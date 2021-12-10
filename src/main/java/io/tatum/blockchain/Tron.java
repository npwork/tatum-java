package io.tatum.blockchain;

import io.tatum.model.response.common.TransactionHash;
import io.tatum.model.response.tron.*;
import io.tatum.utils.Async;
import io.tatum.utils.BaseUrl;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Tron {

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/TronBroadcast" target="_blank">Tatum API documentation</a>
     */
    public TransactionHash tronBroadcast(final String txData, final String signatureId) throws InterruptedException, ExecutionException, IOException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/tron/broadcast";
        return BlockchainUtil.broadcast(uri, txData, signatureId);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/TronGetCurrentBlock" target="_blank">Tatum API documentation</a>
     */
    public TronInfo tronGetCurrentBlock() throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/tron/info";
        return Async.get(uri, TronInfo.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/TronGetBlock" target="_blank">Tatum API documentation</a>
     */
    public TronBlock tronGetBlock(String hash) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/tron/block/" + hash;
        return Async.get(uri, TronBlock.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/TronTrc10Detail" target="_blank">Tatum API documentation</a>
     */
    public TronTrc10 tronGetTrc10Detail(String id) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/tron/trc10/detail/" + id;
        return Async.get(uri, TronTrc10.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/TronGetTransaction" target="_blank">Tatum API documentation</a>
     */
    public TronTransaction tronGetTransaction(String hash) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/tron/transaction/" + hash;
        TronTransaction tronTransaction = Async.get(uri, TronTransaction.class);
        return tronTransaction == null || tronTransaction.getTxID() == null ? null : tronTransaction;
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/TronGetAccount" target="_blank">Tatum API documentation</a>
     */
    public TronAccount tronGetAccount(String address) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/tron/account/" + address;
        return Async.get(uri, TronAccount.class);
    }
}
