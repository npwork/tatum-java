package io.tatum.offchain;

import com.google.common.base.Preconditions;
import io.tatum.ledger.LedgerAccount;
import io.tatum.ledger.LedgerVC;
import io.tatum.model.request.CreateWithdrawal;
import io.tatum.model.request.Currency;
import io.tatum.model.request.TransferEthErc20Offchain;
import io.tatum.model.request.TransferEthOffchain;
import io.tatum.model.response.kms.TransactionKMS;
import io.tatum.model.response.ledger.VC;
import io.tatum.model.response.offchain.BroadcastResult;
import io.tatum.model.response.offchain.PrepareEthTx;
import io.tatum.model.response.offchain.WithdrawalResponse;
import io.tatum.transaction.eth.EthUtil;
import io.tatum.transaction.eth.Web3jClient;
import io.tatum.utils.MapperFactory;
import io.tatum.utils.ObjectValidator;
import org.apache.commons.lang3.StringUtils;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static io.tatum.constants.Constant.*;
import static org.bitcoinj.core.Utils.HEX;
import static org.web3j.utils.Convert.Unit.ETHER;

/**
 * The type Eth offchain.
 */
public class EthOffchain {

    /**
     * Send Ethereum transaction from Tatum Ledger account to the blockchain. This method broadcasts signed transaction to the blockchain.
     * This operation is irreversible.
     *
     * @param testnet  mainnet or testnet version
     * @param body     content of the transaction to broadcast
     * @param provider url of the Ethereum Server to connect to. If not set, default public server will be used.
     * @return the broadcast result
     * @throws Exception the exception
     * @returns transaction id of the transaction in the blockchain
     */
    public BroadcastResult sendEthOffchainTransaction(boolean testnet, TransferEthOffchain body, String provider) throws Exception {

        Preconditions.checkArgument(ObjectValidator.isValidated(body));

        return CompletableFuture.supplyAsync(() -> {
            try {
                CreateWithdrawal withdrawal = new CreateWithdrawal();
                withdrawal.setAmount(body.getBaseTransferEthErc20Offchain().getAmount());
                withdrawal.setAddress(body.getBaseTransferEthErc20Offchain().getAddress());

                var fromPriv = getPrivKey(testnet, body.getMnemonic(), body.getIndex(), body.getPrivateKey());
                var gasPrice = EthUtil.ethGetGasPriceInWei();
                var currency = new LedgerAccount().getAccountById(withdrawal.getSenderAccountId()).getCurrency();
                var web3 = Web3jClient.get(provider);

                PrepareEthTx prepareEthTx = prepareEthSignedOffchainTransaction(
                        withdrawal.getAmount(),
                        fromPriv,
                        withdrawal.getAddress(),
                        Currency.valueOf(currency),
                        web3,
                        gasPrice.toString(),
                        body.getBaseTransferEthErc20Offchain().getNonce());

                withdrawal.setFee(Convert.fromWei(prepareEthTx.getGasLimit().multiply(gasPrice.toBigInteger()).toString(), ETHER).toString());
                WithdrawalResponse withdrawalResponse = Common.offchainStoreWithdrawal(withdrawal);
                return OffchainUtil.broadcast(prepareEthTx.getTx(), withdrawalResponse.getId(), Currency.ETH.getCurrency());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }).get();

    }

    private String getPrivKey(boolean testnet, String mnemonic, int index, String privateKey) throws Exception {
        String fromPriv;
        if (StringUtils.isNotEmpty(mnemonic)) {
            fromPriv = index > 0 ? io.tatum.wallet.Address.generatePrivateKeyFromMnemonic(Currency.ETH, testnet, mnemonic, index) : privateKey;
        } else if (StringUtils.isNotEmpty(privateKey)) {
            fromPriv = privateKey;
        } else {
            throw new Exception("No mnemonic or private key is present.");
        }
        return fromPriv;
    }

    /**
     * Send Ethereum ERC20 transaction from Tatum Ledger account to the blockchain. This method broadcasts signed transaction to the blockchain.
     * This operation is irreversible.
     *
     * @param testnet  mainnet or testnet version
     * @param body     content of the transaction to broadcast
     * @param provider url of the Ethereum Server to connect to. If not set, default public server will be used.
     * @return the broadcast result
     * @throws Exception the exception
     * @returns transaction id of the transaction in the blockchain
     */
    public BroadcastResult sendEthErc20OffchainTransaction(boolean testnet, TransferEthErc20Offchain body, String provider) throws Exception {
        Preconditions.checkArgument(ObjectValidator.isValidated(body));

        return CompletableFuture.supplyAsync(() -> {
            try {
                CreateWithdrawal withdrawal = new CreateWithdrawal();
                withdrawal.setAmount(body.getBaseTransferEthErc20Offchain().getAmount());
                withdrawal.setAmount(body.getBaseTransferEthErc20Offchain().getAddress());

                var fromPriv = getPrivKey(testnet, body.getMnemonic(), body.getIndex(), body.getPrivateKey());
                var gasPrice = EthUtil.ethGetGasPriceInWei();
                var currency = new LedgerAccount().getAccountById(withdrawal.getSenderAccountId()).getCurrency();

                if (ETH_BASED_CURRENCIES.contains(currency)) {
                    TransferEthOffchain transferEthOffchain = new TransferEthOffchain();
                    transferEthOffchain.setBaseTransferEthErc20Offchain(body.getBaseTransferEthErc20Offchain());
                    transferEthOffchain.setIndex(body.getIndex());
                    transferEthOffchain.setMnemonic(body.getMnemonic());
                    transferEthOffchain.setPrivateKey(body.getPrivateKey());
                    return sendEthOffchainTransaction(testnet, transferEthOffchain, provider);
                }

                VC vc = new LedgerVC().getVirtualCurrencyByName(currency);
                var web3 = Web3jClient.get(provider);

                PrepareEthTx prepareEthTx = prepareEthErc20SignedOffchainTransaction(
                        withdrawal.getAmount(),
                        fromPriv,
                        withdrawal.getAddress(),
                        web3,
                        vc.getErc20Address(),
                        gasPrice.toString(),
                        body.getBaseTransferEthErc20Offchain().getNonce());

                withdrawal.setFee(Convert.fromWei(prepareEthTx.getGasLimit().multiply(gasPrice.toBigInteger()).toString(), ETHER).toString());
                var withdrawalResponse = Common.offchainStoreWithdrawal(withdrawal);
                return OffchainUtil.broadcast(prepareEthTx.getTx(), withdrawalResponse.getId(), Currency.ETH.getCurrency());

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }).get();

    }

    /**
     * Sign Ethereum pending transaction from Tatum KMS
     *
     * @param tx             pending transaction from KMS
     * @param fromPrivateKey private key to sign transaction with.
     * @param testnet        mainnet or testnet version
     * @param provider       url of the Ethereum Server to connect to. If not set, default public server will be used.
     * @return the string
     * @throws Exception the exception
     * @returns transaction data to be broadcast to blockchain.
     */
    public String signEthOffchainKMSTransaction(TransactionKMS tx, String fromPrivateKey, boolean testnet, String provider) throws Exception {
        if (tx.getChain() != Currency.ETH) {
            throw new Exception("Unsupported chain.");
        }

        return CompletableFuture.supplyAsync(() -> {
            try {
                var web3j = Web3jClient.get(provider);
                Credentials credentials = Credentials.create(fromPrivateKey);

                Transaction prepareTx = MapperFactory.get().readValue(HEX.decode(tx.getSerializedTransaction()), Transaction.class);
                BigInteger gasLimit = EthUtil.estimateGas(web3j, prepareTx);

                RawTransaction rawTransaction = RawTransaction.createTransaction(
                        new BigInteger(prepareTx.getNonce()),
                        new BigInteger(prepareTx.getGasPrice()), gasLimit,
                        prepareTx.getTo(),
                        new BigInteger(prepareTx.getValue()),
                        prepareTx.getData());

                byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
                return Numeric.toHexString(signedMessage);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }).get();

    }

    /**
     * Sign Ethereum transaction with private keys locally. Nothing is broadcast to the blockchain.
     *
     * @param amount     amount to send
     * @param privateKey private key to sign transaction and send funds from
     * @param address    recipient address
     * @param currency   Ethereum or supported ERC20
     * @param web3       instance of the web3 client
     * @param gasPrice   gas price of the blockchain fee
     * @param nonce      nonce of the transaction. this is counter of the transactions from given address. should be + 1 from previous one.
     * @return the prepare eth tx
     * @throws Exception the exception
     * @returns transaction data to be broadcast to blockchain.
     */
    public PrepareEthTx prepareEthSignedOffchainTransaction(String amount, String privateKey, String address,
                                                            Currency currency, Web3j web3, String gasPrice,
                                                            BigInteger nonce) throws Exception {

        return CompletableFuture.supplyAsync(() -> {
            try {
                Credentials credentials = Credentials.create(privateKey);
                var from = credentials.getAddress();

                Transaction prepareTx;
                if (currency == Currency.ETH) {
                    prepareTx = new Transaction(
                            from,
                            nonce,
                            new BigInteger(gasPrice),
                            null,
                            address,
                            Convert.toWei(amount, ETHER).toBigInteger(),
                            null);
                } else {
                    String contractAddress = CONTRACT_ADDRESSES.get(currency.getCurrency());
                    if (StringUtils.isNotEmpty(contractAddress)) {
                        throw new Exception("Unsupported ETH ERC20 blockchain.");
                    }

                    var _amount = EthUtil.convertAmount(amount, CONTRACT_DECIMALS.get(currency.getCurrency()));
                    String txData = encodeContractTransfer(address, _amount);
                    prepareTx = new Transaction(
                            from,
                            nonce,
                            new BigInteger(gasPrice),
                            null,
                            contractAddress,
                            BigInteger.ZERO,
                            txData);
                }
                return createPrepareEthTx(web3, credentials, prepareTx);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }).get();

    }


    /**
     * Sign Ethereum custom ERC20 transaction with private keys locally. Nothing is broadcast to the blockchain.
     *
     * @param amount       amount to send
     * @param privateKey   private key to sign transaction and send funds from
     * @param address      recipient address
     * @param web3         instance of the web3 client
     * @param tokenAddress blockchain address of the custom ERC20 token
     * @param gasPrice     gas price of the blockchain fee
     * @param nonce        nonce of the transaction. this is counter of the transactions from given address. should be + 1 from previous one.
     * @return the prepare eth tx
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     * @returns transaction data to be broadcast to blockchain.
     */
    public PrepareEthTx prepareEthErc20SignedOffchainTransaction(String amount, String privateKey, String address,
                                                                 Web3j web3, String tokenAddress, String gasPrice,
                                                                 BigInteger nonce) throws ExecutionException, InterruptedException {

        return CompletableFuture.supplyAsync(() -> {
            try {
                Credentials credentials = Credentials.create(privateKey);
                var from = credentials.getAddress();
                var _amount = Convert.toWei(amount, Convert.Unit.ETHER).toBigInteger();
                var txData = encodeContractTransfer(address, _amount);

                Transaction prepareTx = new Transaction(from,
                        nonce,
                        new BigInteger(gasPrice),
                        null,
                        tokenAddress,
                        BigInteger.ZERO,
                        txData);

                return createPrepareEthTx(web3, credentials, prepareTx);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }).get();

    }

    private String encodeContractTransfer(String address, BigInteger _amount) {
        Function function = new Function(
                "transfer",  // function we're calling
                Arrays.asList(new Address(address), new Uint256(_amount)),  // Parameters to pass as Solidity Types
                Arrays.asList(new org.web3j.abi.TypeReference<Bool>() {
                }));
        return FunctionEncoder.encode(function);
    }

    private PrepareEthTx createPrepareEthTx(Web3j web3, Credentials credentials, Transaction prepareTx) throws IOException {
        BigInteger gasLimit = EthUtil.estimateGas(web3, prepareTx);

        RawTransaction rawTransaction = RawTransaction.createTransaction(
                Numeric.decodeQuantity(prepareTx.getNonce()),
                Numeric.decodeQuantity(prepareTx.getGasPrice()),
                gasLimit,
                prepareTx.getTo(),
                Numeric.decodeQuantity(prepareTx.getValue()), // amount
                prepareTx.getData());

        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);

        return new PrepareEthTx(HEX.encode(signedMessage), gasLimit);
    }
}
