package io.tatum.blockchain;

import io.tatum.model.response.bch.BchBlock;
import io.tatum.model.response.bch.BchInfo;
import io.tatum.model.response.bch.BchTx;
import io.tatum.model.response.common.BlockHash;
import io.tatum.model.response.common.TransactionHash;
import io.tatum.utils.ApiKey;
import io.tatum.utils.Async;
import io.tatum.utils.BaseUrl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

public final class Bcash {

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/BchBroadcast" target="_blank">Tatum API documentation</a>
     */
    public TransactionHash bcashBroadcast(String txData, String signatureId) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/bcash/broadcast";
        return BlockchainUtil.broadcast(uri, txData, signatureId);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/BchGetBlockChainInfo" target="_blank">Tatum API documentation</a>
     */
    public BchInfo bcashGetCurrentBlock() throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/bcash/info";
        return Async.get(uri, BchInfo.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/BchGetBlock" target="_blank">Tatum API documentation</a>
     */
    public BchBlock bcashGetBlock(String hash) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/bcash/block/" + hash;
        return Async.get(uri, BchBlock.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/BchGetBlockHash" target="_blank">Tatum API documentation</a>
     */
    public BlockHash bcashGetBlockHash(BigDecimal i) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/bcash/block/hash/" + i;
        return Async.get(uri, BlockHash.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/BchGetTxByAddress" target="_blank">Tatum API documentation</a>
     */
    public BchTx[] bcashGetTxForAccount(String address, BigDecimal skip) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/bcash/transaction/address/" + address + "?skip=" + skip;
        return Async.get(uri, BchTx[].class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/BchGetRawTransaction" target="_blank">Tatum API documentation</a>
     */
    public BchTx bcashGetTransaction(String hash) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/bcash/transaction/" + hash;
        return Async.get(uri, BchTx.class);
    }

}
