package io.tatum.transaction;

import io.tatum.blockchain.Bitcoin;
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

@Log4j2
public class LitecoinTx {

    public String prepareSignedTransaction(NetworkParameters network, TransferBtcBasedBlockchain body) throws ExecutionException, InterruptedException {

        if (!ObjectValidator.isValidated(body)) {
            return null;
        }

        if ((ArrayUtils.isEmpty(body.getFromAddress()) && ArrayUtils.isEmpty(body.getFromUTXO())) |
                (ArrayUtils.isNotEmpty(body.getFromAddress()) && ArrayUtils.isNotEmpty(body.getFromUTXO()))) {
            log.error("Only accept from either addresses or utxo");
            return null;
        }

        return CompletableFuture.supplyAsync(() -> {
            var fromUTXO = body.getFromUTXO();
            var to = body.getTo();
            var fromAddress = body.getFromAddress();

            Litecoin litecoin = new Litecoin();
            TransactionBuilder transactionBuilder = new TransactionBuilder(network);

            // adding outputs before adding inputs
            for (var item : to) {
                transactionBuilder.addOutput(item.getAddress(), item.getValue());
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
                        transactionBuilder.addInput(item.getTxHash(), item.getIndex().longValue(), item.getPrivateKey());
                    }
                }
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
                return null;
            }
            return transactionBuilder.build().toHex();
        }).get();
    }

    /**
     * Sign Litecoin pending transaction from Tatum KMS
     *
     * @param tx          pending transaction from KMS
     * @param privateKeys private keys to sign transaction with.
     * @param network     mainnet or testnet version
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
     * @returns transaction id of the transaction in the blockchain
     */
    public TransactionHash sendBitcoinTransaction(NetworkParameters network, TransferBtcBasedBlockchain body) throws ExecutionException, InterruptedException, IOException {
        Bitcoin bitcoin = new Bitcoin();
        String txData = new BitcoinTx().prepareSignedTransaction(network, body);
        return bitcoin.btcBroadcast(txData, null);
    }
}