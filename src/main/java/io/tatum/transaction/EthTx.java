package io.tatum.transaction;

import com.google.common.base.Preconditions;
import io.tatum.blockchain.Ethereum;
import io.tatum.contracts.erc20.TokenBytecode;
import io.tatum.model.request.*;
import io.tatum.model.response.common.TransactionHash;
import io.tatum.model.response.kms.TransactionKMS;
import io.tatum.transaction.eth.EthUtil;
import io.tatum.transaction.eth.Web3jClient;
import io.tatum.utils.ApiKey;
import io.tatum.utils.BaseUrl;
import io.tatum.utils.MapperFactory;
import io.tatum.utils.ObjectValidator;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
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
import static io.tatum.constants.Constant.VET_URL;
import static io.tatum.model.request.Currency.ETH;
import static org.bitcoinj.core.Utils.HEX;
import static org.web3j.utils.Convert.Unit.ETHER;

/**
 * The type Eth tx.
 */
@Log4j2
public class EthTx {

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
    public String signEthKMSTransaction(TransactionKMS tx, String fromPrivateKey, boolean testnet, String provider) throws Exception {
        if (tx.getChain() != Currency.ETH) {
            throw new Exception("Unsupported chain.");
        }

        return CompletableFuture.supplyAsync(() -> {
            try {
                Web3j web3j = Web3jClient.get(provider);
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
                return HEX.encode(signedMessage);

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }).get();

    }

    /**
     * Sign Ethereum Store data transaction with private keys locally. Nothing is broadcast to the blockchain.
     *
     * @param body     content of the transaction to broadcast
     * @param provider url of the Ethereum Server to connect to. If not set, default public server will be used.
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     * @returns transaction data to be broadcast to blockchain.
     */
    public String prepareStoreDataTransaction(CreateRecord body, String provider) throws ExecutionException, InterruptedException {

        Preconditions.checkArgument(ObjectValidator.isValidated(body));

        return CompletableFuture.supplyAsync(() -> {
            try {
                var _to = body.getTo();
                var _ethFee = body.getEthFee();
                var _data = body.getData();
                var _nonce = body.getNonce();

                String _provider = provider;
                if (StringUtils.isEmpty(_provider)) {
                    _provider = BaseUrl.getInstance().getUrl() + "/v3/ethereum/web3/" + ApiKey.getInstance().getApiKey();
                }
                Web3j web3j = Web3jClient.get(_provider);

                Credentials credentials = Credentials.create(body.getFromPrivateKey());
                var address = StringUtils.isNotEmpty(_to) ? _to : credentials.getAddress();

                Ethereum ethereum = new Ethereum();
                var nonce = (_nonce != null && _nonce.compareTo(BigInteger.ONE) > 0) ? _nonce :
                        ethereum.ethGetTransactionsCount(address);

                BigInteger gasPrice = EthUtil.getGasPrice(_ethFee);
                BigInteger gasLimit = _ethFee != null ? new BigInteger(_ethFee.getGasLimit()) :
                        BigInteger.valueOf(_data.length() * 68 + 21000);

                RawTransaction rawTransaction = RawTransaction.createTransaction(
                        nonce,
                        gasPrice,
                        gasLimit,
                        _to,
                        BigInteger.ZERO,
                        EthUtil.toHexString(_data));

                byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
                return HEX.encode(signedMessage);

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }).get();
    }

    /**
     * Sign Ethereum or supported ERC20 transaction with private keys locally. Nothing is broadcast to the blockchain.
     *
     * @param body     content of the transaction to broadcast
     * @param provider url of the Ethereum Server to connect to. If not set, default public server will be used.
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     * @returns transaction data to be broadcast to blockchain.
     */
    public String prepareEthOrErc20SignedTransaction(TransferEthErc20 body, String provider) throws ExecutionException, InterruptedException {
        Preconditions.checkArgument(ObjectValidator.isValidated(body));

        return CompletableFuture.supplyAsync(() -> {
            try {
                var _to = body.getTo();
                var _fee = body.getFee();
                var _data = body.getData();
                var _nonce = body.getNonce();
                var _currency = body.getCurrency();
                var _amount = body.getAmount();

                String _provider = provider;
                if (StringUtils.isEmpty(_provider)) {
                    _provider = BaseUrl.getInstance().getUrl() + "/v3/ethereum/web3/" + ApiKey.getInstance().getApiKey();
                }

                Web3j web3j = Web3jClient.get(_provider);
                Credentials credentials = Credentials.create(body.getFromPrivateKey());
                var from = credentials.getAddress();

                BigInteger gasPrice = EthUtil.getGasPrice(_fee);

                Transaction prepareTx;

                if (_currency == ETH) {
                    prepareTx = new Transaction(
                            from,
                            _nonce,
                            gasPrice,
                            null,
                            _to,
                            Convert.toWei(_amount, ETHER).toBigInteger(),
                            EthUtil.toHexString(_data));
                } else {
                    String contractAddress = CONTRACT_ADDRESSES.get(_currency.getCurrency());
                    var amount = EthUtil.convertAmount(_amount, CONTRACT_DECIMALS.get(_currency.getCurrency()));

                    Function function = new Function(
                            "transfer",  // function we're calling
                            Arrays.asList(new Address(_to), new Uint256(amount)),  // Parameters to pass as Solidity Types
                            Arrays.asList(new org.web3j.abi.TypeReference<Bool>() {
                            }));
                    String txData = FunctionEncoder.encode(function);

                    prepareTx = new Transaction(from,
                            _nonce,
                            gasPrice,
                            null,
                            contractAddress,
                            BigInteger.ZERO,
                            txData);
                }

                BigInteger gasLimit = _fee != null ? new BigInteger(_fee.getGasLimit()) : EthUtil.estimateGas(web3j, prepareTx);

                RawTransaction rawTransaction = RawTransaction.createTransaction(
                        Numeric.decodeQuantity(prepareTx.getNonce()),
                        Numeric.decodeQuantity(prepareTx.getGasPrice()),
                        gasLimit,
                        prepareTx.getTo(),
                        Numeric.decodeQuantity(prepareTx.getValue()), // amount
                        prepareTx.getData());

                byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
                return HEX.encode(signedMessage);

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }).get();
    }

    /**
     * Sign Ethereum custom ERC20 transaction with private keys locally. Nothing is broadcast to the blockchain.
     *
     * @param body     content of the transaction to broadcast
     * @param provider url of the Ethereum Server to connect to. If not set, default public server will be used.
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     * @throws IOException          the io exception
     * @returns transaction data to be broadcast to blockchain.
     */
    public String prepareCustomErc20SignedTransaction(TransferCustomErc20 body, String provider) throws ExecutionException, InterruptedException, IOException {
        Preconditions.checkArgument(ObjectValidator.isValidated(body));

        return CompletableFuture.supplyAsync(() -> {
            try {
                var _to = body.getTo();
                var _fee = body.getFee();
                var _nonce = body.getNonce();
                var _amount = body.getAmount();
                var _contractAddress = body.getContractAddress();
                var _digits = body.getDigits();

                String _provider = provider;
                if (StringUtils.isEmpty(_provider)) {
                    _provider = BaseUrl.getInstance().getUrl() + "/v3/ethereum/web3/" + ApiKey.getInstance().getApiKey();
                }
                Web3j web3j = Web3jClient.get(_provider);

                Credentials credentials = Credentials.create(body.getFromPrivateKey());

                BigInteger gasPrice = EthUtil.getGasPrice(_fee);
                var amount = EthUtil.convertAmount(_amount, _digits);

                Function function = new Function(
                        "transfer",  // function we're calling
                        Arrays.asList(new Address(_to), new Uint256(amount)),  // Parameters to pass as Solidity Types
                        Arrays.asList(new org.web3j.abi.TypeReference<Bool>() {
                        }));
                String txData = FunctionEncoder.encode(function);

                Transaction prepareTx = new Transaction(credentials.getAddress(),
                        _nonce,
                        gasPrice,
                        null,
                        _contractAddress,
                        BigInteger.ZERO,
                        txData);

                BigInteger gasLimit = _fee != null ? new BigInteger(_fee.getGasLimit()) : EthUtil.estimateGas(web3j, prepareTx);

                RawTransaction rawTransaction = RawTransaction.createTransaction(
                        Numeric.decodeQuantity(prepareTx.getNonce()),
                        Numeric.decodeQuantity(prepareTx.getGasPrice()),
                        gasLimit,
                        prepareTx.getTo(),
                        Numeric.decodeQuantity(prepareTx.getValue()),
                        prepareTx.getData());

                byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
                return HEX.encode(signedMessage);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }).get();

    }

    /**
     * Sign Ethereum deploy ERC20 transaction with private keys locally. Nothing is broadcast to the blockchain.
     *
     * @param body     content of the transaction to broadcast
     * @param provider url of the Ethereum Server to connect to. If not set, default public server will be used.
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     * @throws IOException          the io exception
     * @returns transaction data to be broadcast to blockchain.
     */
    public String prepareDeployErc20SignedTransaction(DeployEthErc20 body, String provider) throws ExecutionException, InterruptedException, IOException {
        Preconditions.checkArgument(ObjectValidator.isValidated(body));

        return CompletableFuture.supplyAsync(() -> {
            try {
                var _fee = body.getFee();
                var _nonce = body.getNonce();

                String _provider = provider;
                if (StringUtils.isEmpty(_provider)) {
                    _provider = BaseUrl.getInstance().getUrl() + "/v3/ethereum/web3/" + ApiKey.getInstance().getApiKey();
                }
                Web3j web3j = Web3jClient.get(_provider);

                Credentials credentials = Credentials.create(body.getFromPrivateKey());

                BigInteger gasPrice = EthUtil.getGasPrice(_fee);
                var cap = EthUtil.convertAmount(body.getSupply(), body.getDigits());

                String encodedConstructor = FunctionEncoder.encodeConstructor(
                        Arrays.<Type>asList(
                                new Utf8String(body.getName()),
                                new Utf8String(body.getSymbol()),
                                new Address(body.getAddress()),
                                new Uint8(body.getDigits()),
                                new Uint256(cap),
                                new Uint256(cap)));

                Transaction prepareTx = new Transaction(credentials.getAddress(),
                        _nonce,
                        gasPrice,
                        null,
                        StringUtils.EMPTY,
                        BigInteger.ZERO,
                        TokenBytecode.TOKEN_BYTE_CODE + encodedConstructor);

                BigInteger gasLimit = _fee != null ? new BigInteger(_fee.getGasLimit()) : EthUtil.estimateGas(web3j, prepareTx);

                RawTransaction rawTransaction = RawTransaction.createContractTransaction(
                        Numeric.decodeQuantity(prepareTx.getNonce()),
                        Numeric.decodeQuantity(prepareTx.getGasPrice()),
                        gasLimit,
                        Numeric.decodeQuantity(prepareTx.getValue()),
                        prepareTx.getData());

                byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
                return HEX.encode(signedMessage);

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }).get();

    }

    /**
     * Send Ethereum store data transaction to the blockchain. This method broadcasts signed transaction to the blockchain.
     * This operation is irreversible.
     *
     * @param testnet  mainnet or testnet version
     * @param body     content of the transaction to broadcast
     * @param provider url of the Ethereum Server to connect to. If not set, default public server will be used.
     * @return the transaction hash
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     * @throws IOException          the io exception
     * @returns transaction id of the transaction in the blockchain
     */
    public TransactionHash sendStoreDataTransaction(boolean testnet, CreateRecord body, String provider) throws ExecutionException, InterruptedException, IOException {
        return new Ethereum().ethBroadcast(prepareStoreDataTransaction(body, provider), null);
    }

    /**
     * Send Ethereum or supported ERC20 transaction to the blockchain. This method broadcasts signed transaction to the blockchain.
     * This operation is irreversible.
     *
     * @param testnet  mainnet or testnet version
     * @param body     content of the transaction to broadcast
     * @param provider url of the Ethereum Server to connect to. If not set, default public server will be used.
     * @return the transaction hash
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     * @throws IOException          the io exception
     * @returns transaction id of the transaction in the blockchain
     */
    public TransactionHash sendEthOrErc20Transaction(boolean testnet, TransferEthErc20 body, String provider) throws ExecutionException, InterruptedException, IOException {
        return new Ethereum().ethBroadcast(prepareEthOrErc20SignedTransaction(body, provider), null);
    }

    /**
     * Send Ethereum custom ERC20 transaction to the blockchain. This method broadcasts signed transaction to the blockchain.
     * This operation is irreversible.
     *
     * @param testnet  mainnet or testnet version
     * @param body     content of the transaction to broadcast
     * @param provider url of the Ethereum Server to connect to. If not set, default public server will be used.
     * @return the transaction hash
     * @throws InterruptedException the interrupted exception
     * @throws ExecutionException   the execution exception
     * @throws IOException          the io exception
     * @returns transaction id of the transaction in the blockchain
     */
    public TransactionHash sendCustomErc20Transaction(boolean testnet, TransferCustomErc20 body, String provider) throws InterruptedException, ExecutionException, IOException {
        return new Ethereum().ethBroadcast(prepareCustomErc20SignedTransaction(body, provider), null);
    }

    /**
     * Send Ethereum deploy ERC20 transaction to the blockchain. This method broadcasts signed transaction to the blockchain.
     * This operation is irreversible.
     *
     * @param testnet  mainnet or testnet version
     * @param body     content of the transaction to broadcast
     * @param provider url of the Ethereum Server to connect to. If not set, default public server will be used.
     * @return the transaction hash
     * @throws InterruptedException the interrupted exception
     * @throws ExecutionException   the execution exception
     * @throws IOException          the io exception
     * @returns transaction id of the transaction in the blockchain
     */
    public TransactionHash sendDeployErc20Transaction(boolean testnet, DeployEthErc20 body, String provider) throws InterruptedException, ExecutionException, IOException {
        return new Ethereum().ethBroadcast(prepareDeployErc20SignedTransaction(body, provider), null);
    }
}
