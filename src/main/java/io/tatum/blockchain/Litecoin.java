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

/**
 * The type Litecoin.
 */
public final class Litecoin {

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/LtcBroadcast" target="_blank">Tatum API documentation</a>
     *
     * @param txData      the tx data
     * @param signatureId the signature id
     * @return the transaction hash
     * @throws IOException          the io exception
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public TransactionHash ltcBroadcast(String txData, String signatureId) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/litecoin/broadcast";
        return BlockchainUtil.broadcast(uri, txData, signatureId);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/LtcGetBlockChainInfo" target="_blank">Tatum API documentation</a>
     *
     * @return the ltc info
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public LtcInfo ltcGetCurrentBlock() throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/litecoin/info";
        return Async.get(uri, LtcInfo.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/LtcGetBlock" target="_blank">Tatum API documentation</a>
     *
     * @param hash the hash
     * @return the ltc block
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public LtcBlock ltcGetBlock(String hash) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/litecoin/block/" + hash;
        return Async.get(uri, LtcBlock.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/LtcGetBlockHash" target="_blank">Tatum API documentation</a>
     *
     * @param i the
     * @return the block hash
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public BlockHash ltcGetBlockHash(BigDecimal i) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/litecoin/block/hash/" + i;
        return Async.get(uri, BlockHash.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/LtcGetUTXO" target="_blank">Tatum API documentation</a>
     *
     * @param txHash the tx hash
     * @param i      the
     * @return the ltc utxo
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public LtcUTXO ltcGetUTXO(String txHash, BigDecimal i) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/litecoin/utxo/" + txHash + "/" + i;
        return Async.get(uri, LtcUTXO.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/LtcGetTxByAddress" target="_blank">Tatum API documentation</a>
     *
     * @param address  the address
     * @param pageSize the page size
     * @param offset   the offset
     * @return the ltc tx [ ]
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public LtcTx[] ltcGetTxForAccount(String address, Integer pageSize, Integer offset) throws ExecutionException, InterruptedException {
        Integer _pageSize = (pageSize == null || pageSize < 0 || pageSize > 50) ? 50 : pageSize;
        Integer _offset = (offset == null || offset < 0) ? 0 : offset;
        String uri = BaseUrl.getInstance().getUrl() + "/v3/litecoin/transaction/address/" + address + "?pageSize=" + _pageSize + "&offset=" + _offset;
        return Async.get(uri, LtcTx[].class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/LtcGetRawTransaction" target="_blank">Tatum API documentation</a>
     *
     * @param hash the hash
     * @return the ltc tx
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public LtcTx ltcGetTransaction(String hash) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/litecoin/transaction/" + hash;
        return Async.get(uri, LtcTx.class);
    }

}
