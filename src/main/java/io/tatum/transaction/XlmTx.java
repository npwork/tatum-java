package io.tatum.transaction;

import io.tatum.blockchain.XLM;
import io.tatum.model.request.Currency;
import io.tatum.model.request.TransferXlm;
import io.tatum.model.response.common.TransactionHash;
import io.tatum.model.response.kms.TransactionKMS;
import io.tatum.utils.ObjectValidator;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.stellar.sdk.*;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * The type Xlm tx.
 */
public class XlmTx {

    /**
     * Send Stellar transaction to the blockchain. This method broadcasts signed transaction to the blockchain.
     * This operation is irreversible.
     *
     * @param testnet mainnet or testnet version
     * @param body    content of the transaction to broadcast
     * @return the transaction hash
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     * @returns transaction id of the transaction in the blockchain
     */
    public TransactionHash sendXlmTransaction(boolean testnet, TransferXlm body) throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return new XLM().xlmBroadcast(prepareXlmSignedTransaction(testnet, body), null);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }).get();
    }

    /**
     * Sign Stellar pending transaction from Tatum KMS
     *
     * @param tx      pending transaction from KMS
     * @param secret  secret key to sign transaction with.
     * @param testnet mainnet or testnet version
     * @return the string
     * @throws Exception the exception
     * @returns transaction data to be broadcast to blockchain.
     */
    public String signXlmKMSTransaction(TransactionKMS tx, String secret, boolean testnet) throws Exception {
        if (tx.getChain() != Currency.XLM) {
            throw new Exception("Unsupported chain.");
        }

        return CompletableFuture.supplyAsync(() -> {
            try {
                Network network = testnet ? Network.TESTNET : Network.PUBLIC;
                Transaction parsedTx = (Transaction) Transaction.fromEnvelopeXdr(tx.getSerializedTransaction(), network);
                parsedTx.sign(KeyPair.fromSecretSeed(secret));
                return parsedTx.toEnvelopeXdrBase64();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }).get();


    }

    /**
     * Sign Stellar transaction with private keys locally. Nothing is broadcast to the blockchain.
     *
     * @param testnet mainnet or testnet version
     * @param body    content of the transaction to broadcast
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     * @returns transaction data to be broadcast to blockchain.
     */
    public String prepareXlmSignedTransaction(boolean testnet, TransferXlm body) throws ExecutionException, InterruptedException {
        if (!ObjectValidator.isValidated(body)) {
            return null;
        }

        return CompletableFuture.supplyAsync(() -> {
            try {
                String message = body.getMessage();
                Memo memo = StringUtils.isNotEmpty(message) ? message.length() > 20 ? Memo.hash(message) : Memo.text(message)
                        : Memo.text(Strings.EMPTY);

                KeyPair keyPair = KeyPair.fromSecretSeed(body.getFromSecret());
                var fromAccount = keyPair.getAccountId();

                var sequence = new XLM().xlmGetAccountInfo(fromAccount);
                Account account = new Account(fromAccount, sequence.getSequence());

                Network network = testnet ? Network.TESTNET : Network.PUBLIC;
                var builder = new Transaction.Builder(account, network)
                        .setBaseFee(100)
                        .addMemo(memo)
                        .setTimeout(30);

                KeyPair destination = KeyPair.fromAccountId(body.getTo());

                var tx = body.isInitialize() ?
                        builder.addOperation(
                                new CreateAccountOperation.Builder(destination.getAccountId(), body.getAmount()).build()).build()
                        : builder.addOperation(
                        new PaymentOperation.Builder(destination.getAccountId(),
                                new AssetTypeNative(), body.getAmount()).build()).build();

                tx.sign(keyPair);

                return tx.toEnvelopeXdrBase64();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }).get();
    }

}
