package io.tatum.blockchain;

import io.tatum.model.response.common.BlockHash;
import io.tatum.model.response.common.TransactionHash;
import io.tatum.model.response.ltc.LtcBlock;
import io.tatum.model.response.ltc.LtcInfo;
import io.tatum.model.response.ltc.LtcTx;
import io.tatum.model.response.ltc.LtcUTXO;
import io.tatum.utils.ApiKey;
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
        String uri = BaseUrl.getInstance().url + "/v3/litecoin/broadcast";
        return BlockchainUtil.broadcast(uri, txData, signatureId);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/LtcGetBlockChainInfo" target="_blank">Tatum API documentation</a>
     */
    public LtcInfo ltcGetCurrentBlock() throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().url + "/v3/litecoin/info";
        return Async.get(uri, ApiKey.getInstance().apiKey, LtcInfo.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/LtcGetBlock" target="_blank">Tatum API documentation</a>
     */
    public LtcBlock ltcGetBlock(String hash) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().url + "/v3/litecoin/block/" + hash;
        return Async.get(uri, ApiKey.getInstance().apiKey, LtcBlock.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/LtcGetBlockHash" target="_blank">Tatum API documentation</a>
     */
    public BlockHash ltcGetBlockHash(BigDecimal i) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().url + "/v3/litecoin/block/hash/" + i;
        return Async.get(uri, ApiKey.getInstance().apiKey, BlockHash.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/LtcGetUTXO" target="_blank">Tatum API documentation</a>
     */
    public LtcUTXO ltcGetUTXO(String hash, BigDecimal i) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().url + "/v3/litecoin/utxo/" + hash + "/" + i;
        return Async.get(uri, ApiKey.getInstance().apiKey, LtcUTXO.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/LtcGetTxByAddress" target="_blank">Tatum API documentation</a>
     */
    public LtcTx[] ltcGetTxForAccount(String address, Integer pageSize, Integer offset) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().url + "/v3/litecoin/transaction/address/" + address + "?pageSize=" + pageSize + "&offset=" + offset;
        return Async.get(uri, ApiKey.getInstance().apiKey, LtcTx[].class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/LtcGetRawTransaction" target="_blank">Tatum API documentation</a>
     */
    public LtcTx ltcGetTransaction(String hash) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().url + "/v3/litecoin/transaction/" + hash;
        return Async.get(uri, ApiKey.getInstance().apiKey, LtcTx.class);
    }

}
