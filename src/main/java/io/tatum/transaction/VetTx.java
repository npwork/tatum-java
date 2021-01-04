package io.tatum.transaction;

import io.tatum.model.request.Currency;
import io.tatum.model.request.TransferVet;
import io.tatum.model.response.kms.TransactionKMS;
import io.tatum.transaction.eth.EthUtil;
import io.tatum.transaction.eth.Web3jClient;
import io.tatum.utils.ApiKey;
import io.tatum.utils.MapperFactory;
import io.tatum.utils.ObjectValidator;
import org.apache.commons.lang3.StringUtils;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static io.tatum.constants.Constant.*;
import static org.web3j.utils.Convert.Unit.ETHER;

public class VetTx {

    /**
     * Sign VeChain pending transaction from Tatum KMS
     *
     * @param tx             pending transaction from KMS
     * @param fromPrivateKey private key to sign transaction with.
     * @param testnet        mainnet or testnet version
     * @param provider       url of the VeChain Server to connect to. If not set, default public server will be used.
     * @returns transaction data to be broadcast to blockchain.
     */
    public String signVetKMSTransaction(TransactionKMS tx, String fromPrivateKey, boolean testnet, String provider) throws Exception {

        if (tx.getChain() != Currency.VET) {
            throw new Exception("Unsupported chain.");
        }

        return CompletableFuture.supplyAsync(() -> {
            try {
                String _provider = StringUtils.isEmpty(provider) ? (testnet == true ? TEST_VET_URL : VET_URL) : provider;
                Web3j web3j = Web3jClient.get(_provider);
                Credentials credentials = Credentials.create(fromPrivateKey);

                Transaction prepareTx = MapperFactory.get().readValue(tx.getSerializedTransaction(), Transaction.class);
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
     * Sign VeChain transaction with private keys locally. Nothing is broadcast to the blockchain.
     *
     * @param testnet  mainnet or testnet version
     * @param body     content of the transaction to broadcast
     * @param provider url of the VeChain Server to connect to. If not set, default public server will be used.
     * @returns transaction data to be broadcast to blockchain.
     */
    public String prepareVetSignedTransaction(boolean testnet, TransferVet body, String provider) throws ExecutionException, InterruptedException, IOException {
        if (!ObjectValidator.isValidated(body)) {
            return null;
        }

        return CompletableFuture.supplyAsync(() -> {
            try {
                var url = StringUtils.isNotEmpty(provider) ? provider : (testnet == true ? TEST_VET_URL : VET_URL);
                Web3j web3j = Web3j.build(new HttpService(url));

                Credentials credentials = Credentials.create(body.getFromPrivateKey());

                Transaction prepareTx = new Transaction(
                        credentials.getAddress(),
                        BigInteger.ZERO,
                        BigInteger.ZERO,
                        null,
                        body.getTo(),
                        Convert.toWei(body.getAmount(), ETHER).toBigInteger(),
                        EthUtil.toHexString(body.getData()));

                BigInteger gasLimit;
                if (body.getFee() != null) {
                    gasLimit = new BigInteger(body.getFee().getGasLimit());
                } else {
                    gasLimit = EthUtil.estimateGas(web3j, prepareTx);
                }

                RawTransaction rawTransaction = RawTransaction.createTransaction(
                        Numeric.decodeQuantity(prepareTx.getNonce()),
                        Numeric.decodeQuantity(prepareTx.getGasPrice()),
                        gasLimit,
                        prepareTx.getTo(),
                        Numeric.decodeQuantity(prepareTx.getValue()), // amount
                        prepareTx.getData());


                byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
                return Numeric.toHexString(signedMessage);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }).get();
    }

}
