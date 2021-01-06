package io.tatum.transaction;

import io.tatum.blockchain.Bcash;
import io.tatum.model.request.transaction.FromUTXO;
import io.tatum.model.request.transaction.To;
import io.tatum.model.request.transaction.TransferBchBlockchain;
import io.tatum.model.response.bch.BchTx;
import io.tatum.transaction.bcash.TransactionBuilder;
import io.tatum.utils.ObjectValidator;
import io.tatum.utils.Promise;
import org.apache.commons.lang3.ArrayUtils;
import org.bitcoinj.core.NetworkParameters;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

/**
 * The type Bcash tx.
 */
public class BcashTx {

    /**
     * Sign Bitcoin Cash transaction with private keys locally. Nothing is broadcast to the blockchain.
     *
     * @param network mainnet or testnet version
     * @param body    content of the transaction to broadcast
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     * @returns transaction data to be broadcast to blockchain.
     */
    public String prepareBitcoinCashSignedTransaction(NetworkParameters network, TransferBchBlockchain body) throws ExecutionException, InterruptedException {
        if (!ObjectValidator.isValidated(body)) {
            return null;
        }

        return CompletableFuture.supplyAsync(() -> {
            try {
                FromUTXO[] fromUTXO = body.getFromUTXO();
                To[] to = body.getTo();

                var transactionBuilder = new TransactionBuilder(network);
                for (var item : to) {
                    transactionBuilder.addOutput(item.getAddress(), item.getValue());
                }

                String[] txHashs = Stream.of(fromUTXO).map(u -> u.getTxHash()).toArray(size -> new String[size]);
                List<BchTx> txs = getTransactions(txHashs);


                for (int i = 0; i < fromUTXO.length; i++) {
                    FromUTXO item = fromUTXO[i];
                    BigDecimal value = new BigDecimal(txs.get(i).getVout()[item.getIndex().intValue()].getValue())
                            .setScale(8, RoundingMode.FLOOR);
                    long satoshis = value.multiply(BigDecimal.valueOf(100000000)).longValue();
                    transactionBuilder.addInput(item.getTxHash(), item.getIndex().longValue(), item.getPrivateKey(), satoshis);
                }

                return transactionBuilder.build().toHex();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
                return null;
            }
        }).get();

    }

    /**
     * Gets transactions.
     *
     * @param txHash the tx hash
     * @return the transactions
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public List<BchTx> getTransactions(String[] txHash) throws ExecutionException, InterruptedException {
        List<CompletableFuture<BchTx>> futures = new ArrayList<>();
        if (ArrayUtils.isEmpty(txHash)) {
            Bcash bcash = new Bcash();
            CompletableFuture future;
            for (String tx : txHash) {
                future = CompletableFuture.supplyAsync(() -> {
                    try {
                        return bcash.bcashGetTransaction(tx);
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                    return null;
                });
                futures.add(future);
            }
        }
        return Promise.all(futures).get();
    }
}
