package io.tatum.transaction;

import com.google.common.base.Preconditions;
import io.tatum.blockchain.Litecoin;
import io.tatum.model.request.Currency;
import io.tatum.model.request.transaction.TransferBtcBasedBlockchain;
import io.tatum.model.response.common.TransactionHash;
import io.tatum.model.response.kms.TransactionKMS;
import io.tatum.model.response.ltc.LtcTxOutputs;
import io.tatum.model.response.ltc.LtcUTXO;
import io.tatum.transaction.bitcoin.TransactionBuilder;
import io.tatum.utils.ObjectValidator;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ArrayUtils;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Transaction;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.bitcoinj.core.Utils.HEX;

/**
 * The type Litecoin tx.
 */
@Log4j2
public class LitecoinTx {

    /**
     * Prepare signed transaction string.
     *
     * @param network the network
     * @param body    the body
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public String prepareSignedTransaction(NetworkParameters network, TransferBtcBasedBlockchain body, boolean testnet) throws Exception {

        Preconditions.checkArgument(ObjectValidator.isValidated(body));

        Preconditions.checkArgument(
                (ArrayUtils.isEmpty(body.getFromAddress()) && ArrayUtils.isEmpty(body.getFromUTXO())) ||
                        (ArrayUtils.isNotEmpty(body.getFromAddress()) && ArrayUtils.isNotEmpty(body.getFromUTXO())),
                "Only accept from either addresses or utxo");

        var fromUTXO = body.getFromUTXO();
        var to = body.getTo();
        var fromAddress = body.getFromAddress();

        Litecoin litecoin = new Litecoin();
        TransactionBuilder transactionBuilder = new TransactionBuilder(network);

        // adding outputs before adding inputs
        try {
            for (var item : to) {

                transactionBuilder.addOutput(item.getAddress(), item.getValue());
            }
        } catch (Exception e) {
            if (!testnet) {
                throw new Exception("Wrong output address. Supported LTC address should start with M or L.");
            }
            throw e;
        }

        // adding inputs
        try {
            if (ArrayUtils.isNotEmpty(fromAddress)) {
                for (var item : fromAddress) {
                    var txs = litecoin.ltcGetTxForAccount(item.getAddress(), null, null);
                    for (var tx : txs) {
                        LtcTxOutputs[] outputs = tx.getOutputs();
                        for (int i = 0; i < outputs.length; i++) {
                            if (outputs[i].getAddress().equals(item.getAddress())) {
                                LtcUTXO utxo = litecoin.ltcGetUTXO(tx.getHash(), outputs[i].getValue());
                                transactionBuilder.addInput(item.getAddress(), utxo.getIndex().longValue(), item.getPrivateKey());
                            }
                        }
                    }
                }
            } else if (ArrayUtils.isNotEmpty(fromUTXO)) {
                for (var item : fromUTXO) {
                    transactionBuilder.addInput(item.getTxHash(), item.getIndex(), item.getPrivateKey());
                }
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
        return transactionBuilder.build().toHex();
    }

    /**
     * Sign Litecoin pending transaction from Tatum KMS
     *
     * @param network     mainnet or testnet version
     * @param tx          pending transaction from KMS
     * @param privateKeys private keys to sign transaction with.
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     * @returns transaction data to be broadcast to blockchain.
     */
    public String signLitecoinKMSTransaction(NetworkParameters network, TransactionKMS tx, String[] privateKeys) throws ExecutionException, InterruptedException {
        if (ObjectValidator.isValidated(tx)) {
            return null;
        }

        if (tx.getChain() != Currency.LTC) {
            log.error("Unsupported chain.");
            return null;
        }
        return CompletableFuture.supplyAsync(() -> {
            TransactionBuilder transactionBuilder = new TransactionBuilder(network);
            Transaction transaction = new Transaction(network, HEX.decode(tx.getSerializedTransaction()));
            transactionBuilder.fromTransaction(transaction, privateKeys);
            return transactionBuilder.build().toHex();
        }).get();
    }

    /**
     * Send Litecoin transaction to the blockchain. This method broadcasts signed transaction to the blockchain.
     * This operation is irreversible.
     *
     * @param network mainnet or testnet version
     * @param body    content of the transaction to broadcast
     * @return the transaction hash
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     * @throws IOException          the io exception
     * @returns transaction id of the transaction in the blockchain
     */
    public TransactionHash sendLitecoinTransaction(NetworkParameters network, TransferBtcBasedBlockchain body, boolean testnet) throws Exception {
        Litecoin litecoin = new Litecoin();
        String txData = new LitecoinTx().prepareSignedTransaction(network, body, testnet);
        return litecoin.ltcBroadcast(txData, null);
    }
}
