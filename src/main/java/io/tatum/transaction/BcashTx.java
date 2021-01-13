package io.tatum.transaction;

import io.tatum.blockchain.Bcash;
import io.tatum.model.request.Currency;
import io.tatum.model.request.transaction.FromUTXO;
import io.tatum.model.request.transaction.To;
import io.tatum.model.request.transaction.TransferBchBlockchain;
import io.tatum.model.response.bch.BchTx;
import io.tatum.model.response.common.TransactionHash;
import io.tatum.model.response.kms.TransactionKMS;
import io.tatum.transaction.bcash.TransactionBuilder;
import io.tatum.utils.ObjectValidator;
import io.tatum.utils.Promise;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.bitcoincashj.core.Coin;
import org.bitcoincashj.core.Transaction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

import static io.tatum.constants.Constant.BCH_MAINNET;
import static io.tatum.constants.Constant.BCH_TESTNET;
import static org.bitcoinj.core.Utils.HEX;

/**
 * The type Bcash tx.
 */
public class BcashTx {

    /**
     * Send Bitcoin Cash transaction to the blockchain. This method broadcasts signed transaction to the blockchain.
     * This operation is irreversible.
     *
     * @param testnet mainnet or testnet version
     * @param body    content of the transaction to broadcast
     * @returns transaction id of the transaction in the blockchain
     */
    public TransactionHash sendBitcoinCashTransaction(boolean testnet, TransferBchBlockchain body) throws ExecutionException, InterruptedException, IOException {
        return new Bcash().bcashBroadcast(prepareBitcoinCashSignedTransaction(testnet, body), null);
    }

    /**
     * Sign Bitcoin Cash pending transaction from Tatum KMS
     *
     * @param tx          pending transaction from KMS
     * @param privateKeys private keys to sign transaction with.
     * @param testnet     mainnet or testnet version
     * @returns transaction data to be broadcast to blockchain.
     */
    public String signBitcoinCashKMSTransaction(TransactionKMS tx, String[] privateKeys, boolean testnet) throws Exception {
        if (tx.getChain() != Currency.BCH) {
            throw new Exception("Unsupported chain.");
        }
//    ignore signKMS
//        var data = String.
        var network = testnet ? BCH_TESTNET : BCH_MAINNET;
        Long[] amountsToSign = new Long[0];
        TransactionBuilder transactionBuilder = new TransactionBuilder(network);
        Transaction transaction = new Transaction(network, HEX.decode(tx.getSerializedTransaction()));
        transactionBuilder.fromTransaction(transaction, privateKeys, amountsToSign);
        return transactionBuilder.build().toHex();
    }

    /**
     * Sign Bitcoin Cash transaction with private keys locally. Nothing is broadcast to the blockchain.
     *
     * @param testnet mainnet or testnet version
     * @param body    content of the transaction to broadcast
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     * @returns transaction data to be broadcast to blockchain.
     */
    public String prepareBitcoinCashSignedTransaction(boolean testnet, TransferBchBlockchain body) throws ExecutionException, InterruptedException {
        if (!ObjectValidator.isValidated(body)) {
            return null;
        }

        return CompletableFuture.supplyAsync(() -> {
            try {
                FromUTXO[] fromUTXO = body.getFromUTXO();
                To[] to = body.getTo();

                var network = testnet ? BCH_TESTNET : BCH_MAINNET;
                var transactionBuilder = new TransactionBuilder(network);
                for (var item : to) {
                    System.out.println(Coin.btcToSatoshi(item.getValue()));
                    transactionBuilder.addOutput(item.getAddress(), Coin.btcToSatoshi(item.getValue()));
                }

                String[] txHashs = Stream.of(fromUTXO).map(u -> u.getTxHash()).toArray(size -> new String[size]);
                List<BchTx> txs = getTransactions(txHashs);

                if (CollectionUtils.isNotEmpty(txs)) {
                    long satoshis;
                    for (int i = 0; i < fromUTXO.length; i++) {
                        FromUTXO item = fromUTXO[i];
                        if (txs.get(i) == null) {
                            return null;
                        }

                        satoshis = Coin.btcToSatoshi(txs.get(i).getVout()[Math.toIntExact(item.getIndex())].getValue());
                        transactionBuilder.addInput(item.getTxHash(), item.getIndex(), item.getPrivateKey(), satoshis);
                    }
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
    private List<BchTx> getTransactions(String[] txHash) throws ExecutionException, InterruptedException {
        List<CompletableFuture<BchTx>> futures = new ArrayList<>();
        if (ArrayUtils.isNotEmpty(txHash)) {
            Bcash bcash = new Bcash();
            CompletableFuture future;
            for (String tx : txHash) {
                future = CompletableFuture.supplyAsync(() -> {
                    try {
                        return bcash.bcashGetTransaction(tx);
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                        return null;
                    }
                });
                futures.add(future);
            }
        }

        if (!futures.isEmpty()) {
            return Promise.all(futures).get();
        }

        return new ArrayList<>();
    }
}
