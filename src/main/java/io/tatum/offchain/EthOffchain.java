package io.tatum.offchain;

import io.tatum.model.request.Currency;
import io.tatum.model.response.offchain.PrepareEthTx;
import io.tatum.transaction.eth.EthUtil;
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

import static io.tatum.constants.Constant.CONTRACT_ADDRESSES;
import static io.tatum.constants.Constant.CONTRACT_DECIMALS;
import static org.web3j.utils.Convert.Unit.ETHER;

public class EthOffchain {

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
     * @returns transaction data to be broadcast to blockchain.
     */
    public PrepareEthTx prepareEthSignedOffchainTransaction(String amount, String privateKey, String address,
                                                            Currency currency, Web3j web3, String gasPrice,
                                                            BigInteger nonce) throws Exception {

        Credentials credentials = Credentials.create(privateKey);
        var from = Credentials.create(privateKey).getAddress();

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
    }


    /**
     * Sign Ethereum custom ERC20 transaction with private keys locally. Nothing is broadcast to the blockchain.
     *
     * @param amount       amount to send
     * @param privateKey   private key to sign transaction and send funds from
     * @param address      recipient address
     * @param tokenAddress blockchain address of the custom ERC20 token
     * @param web3         instance of the web3 client
     * @param gasPrice     gas price of the blockchain fee
     * @param nonce        nonce of the transaction. this is counter of the transactions from given address. should be + 1 from previous one.
     * @returns transaction data to be broadcast to blockchain.
     */
    public PrepareEthTx prepareEthErc20SignedOffchainTransaction(String amount, String privateKey, String address,
                                                                 Web3j web3, String tokenAddress, String gasPrice,
                                                                 BigInteger nonce) throws IOException {

        Credentials credentials = Credentials.create(privateKey);
        var from = credentials.getAddress();
        var _amount = Convert.toWei(amount, Convert.Unit.ETHER).toBigInteger();

        Transaction prepareTx;

        String txData = encodeContractTransfer(address, _amount);

        prepareTx = new Transaction(from,
                nonce,
                new BigInteger(gasPrice),
                null,
                tokenAddress,
                BigInteger.ZERO,
                txData);

        return createPrepareEthTx(web3, credentials, prepareTx);
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

        return new PrepareEthTx(Numeric.toHexString(signedMessage), gasLimit);
    }
}
