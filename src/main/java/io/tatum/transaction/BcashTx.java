package io.tatum.transaction;

public class BcashTx {

    /**
     * Sign Bitcoin Cash transaction with private keys locally. Nothing is broadcast to the blockchain.
     * @param testnet mainnet or testnet version
     * @param body content of the transaction to broadcast
     * @returns transaction data to be broadcast to blockchain.
     */
    /*public CompletableFuture<Object> prepareBitcoinCashSignedTransaction(Boolean testnet, TransferBchBlockchain body) {
        if (!ObjectValidator.isValidated(body)) {
            return null;
        }

        FromUTXO[] fromUTXO = body.getFromUTXO();
        To[] to = body.getTo();


    var networkType = testnet ? "testnet" : "mainnet";

    var transactionBuilder = new TransactionBuilder(networkType);

    const privateKeysToSign: string[] = [];
    const amountToSign: number[] = [];
    const txs = await getTransactions(fromUTXO.map(u => u.txHash));
        for (const [i, item] of fromUTXO.entries()) {
            transactionBuilder.addInput(item.txHash, item.index);
            privateKeysToSign.push(item.privateKey);
            amountToSign.push(Number(new BigNumber(txs[i].vout[item.index].value).multipliedBy(100000000).toFixed(0, BigNumber.ROUND_FLOOR)));
        }
        for (const item of to) {
            transactionBuilder.addOutput(item.address, Number(new BigNumber(item.value).multipliedBy(100000000).toFixed(0, BigNumber.ROUND_FLOOR)));
        }

        for (let i = 0; i < privateKeysToSign.length; i++) {
        const ecPair = new ECPair().fromWIF(privateKeysToSign[i]);
            transactionBuilder.sign(i, ecPair, undefined, transactionBuilder.hashTypes.SIGHASH_ALL, amountToSign[i], transactionBuilder.signatureAlgorithms.SCHNORR);
        }
        return transactionBuilder.build().toHex();
    }

    public CompletableFuture<List<BchTx>> getTransactions(String[] txHash) {
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
        return Promise.all(futures);
    }*/
}
