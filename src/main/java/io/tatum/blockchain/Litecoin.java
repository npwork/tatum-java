package io.tatum.blockchain;

import io.tatum.model.response.common.BlockHash;
import io.tatum.model.response.common.TransactionHash;
import io.tatum.model.response.ltc.LtcBlock;
import io.tatum.model.response.ltc.LtcInfo;
import io.tatum.model.response.ltc.LtcTx;
import io.tatum.model.response.ltc.LtcUTXO;
import io.tatum.utils.Async;
import io.tatum.utils.BaseUrl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

public final class Litecoin {

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/LtcBroadcast" target="_blank">Tatum API documentation</a>
     */
    public TransactionHash ltcBroadcast(String txData, String signatureId) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/litecoin/broadcast";
        return BlockchainUtil.broadcast(uri, txData, signatureId);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/LtcGetBlockChainInfo" target="_blank">Tatum API documentation</a>
     */
    public LtcInfo ltcGetCurrentBlock() throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/litecoin/info";
        return Async.get(uri, LtcInfo.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/LtcGetBlock" target="_blank">Tatum API documentation</a>
     */
    public LtcBlock ltcGetBlock(String hash) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/litecoin/block/" + hash;
        return Async.get(uri, LtcBlock.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/LtcGetBlockHash" target="_blank">Tatum API documentation</a>
     */
    public BlockHash ltcGetBlockHash(BigDecimal i) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/litecoin/block/hash/" + i;
        return Async.get(uri, BlockHash.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/LtcGetUTXO" target="_blank">Tatum API documentation</a>
     */
    public LtcUTXO ltcGetUTXO(String txHash, BigDecimal i) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/litecoin/utxo/" + txHash + "/" + i;
        return Async.get(uri, LtcUTXO.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/LtcGetTxByAddress" target="_blank">Tatum API documentation</a>
     */
    public LtcTx[] ltcGetTxForAccount(String address, Integer pageSize, Integer offset) throws ExecutionException, InterruptedException {
        Integer _pageSize = (pageSize == null || pageSize < 0 || pageSize > 50) ? 50 : pageSize;
        Integer _offset = (offset == null || offset < 0) ? 0 : offset;
        String uri = BaseUrl.getInstance().getUrl() + "/v3/litecoin/transaction/address/" + address + "?pageSize=" + _pageSize + "&offset=" + _offset;
        return Async.get(uri, LtcTx[].class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/LtcGetRawTransaction" target="_blank">Tatum API documentation</a>
     */
    public LtcTx ltcGetTransaction(String hash) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/litecoin/transaction/" + hash;
        return Async.get(uri, LtcTx.class);
    }

}
