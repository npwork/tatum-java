package io.tatum.transaction;

import io.tatum.blockchain.Litecoin;
import io.tatum.model.request.transaction.TransferBtcBasedBlockchain;
import io.tatum.model.response.ltc.LtcTxOutputs;
import io.tatum.model.response.ltc.LtcUTXO;
import io.tatum.transaction.bitcoin.TransactionBuilder;
import io.tatum.utils.ObjectValidator;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ArrayUtils;
import org.bitcoinj.core.NetworkParameters;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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

            TransactionBuilder transactionBuilder = TransactionBuilder.getInstance();
            transactionBuilder.Init(network);

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
}
