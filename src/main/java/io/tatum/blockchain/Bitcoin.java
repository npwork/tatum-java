package io.tatum.blockchain;

import io.tatum.model.response.btc.BtcBlock;
import io.tatum.model.response.btc.BtcInfo;
import io.tatum.model.response.btc.BtcTx;
import io.tatum.model.response.btc.BtcUTXO;
import io.tatum.model.response.common.BlockHash;
import io.tatum.model.response.common.TransactionHash;
import io.tatum.utils.Async;
import io.tatum.utils.BaseUrl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

/**
 * The type Bitcoin.
 */
public final class Bitcoin {

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/BtcBroadcast" target="_blank">Tatum API documentation</a>
     *
     * @param txData      the tx data
     * @param signatureId the signature id
     * @return the transaction hash
     * @throws IOException          the io exception
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public TransactionHash btcBroadcast(final String txData, final String signatureId) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/bitcoin/broadcast";
        return BlockchainUtil.broadcast(uri, txData, signatureId);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/BtcGetBlockChainInfo" target="_blank">Tatum API documentation</a>
     *
     * @return the btc info
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public BtcInfo btcGetCurrentBlock() throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/bitcoin/info";
        return Async.get(uri, BtcInfo.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/BtcGetBlock" target="_blank">Tatum API documentation</a>
     *
     * @param hash the hash
     * @return the btc block
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public BtcBlock btcGetBlock(String hash) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/bitcoin/block/" + hash;
        return Async.get(uri, BtcBlock.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/BtcGetBlockHash" target="_blank">Tatum API documentation</a>
     *
     * @param i the
     * @return the block hash
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public BlockHash btcGetBlockHash(BigDecimal i) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/bitcoin/block/hash/" + i;
        return Async.get(uri, BlockHash.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/BtcGetUTXO" target="_blank">Tatum API documentation</a>
     *
     * @param hash the hash
     * @param i    the
     * @return the btc utxo
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public BtcUTXO btcGetUTXO(String hash, BigDecimal i) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/bitcoin/utxo/" + hash + "/" + i;
        return Async.get(uri, BtcUTXO.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/BtcGetTxByAddress" target="_blank">Tatum API documentation</a>
     *
     * @param address  the address
     * @param pageSize the page size
     * @param offset   the offset
     * @return the btc tx [ ]
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public BtcTx[] btcGetTxForAccount(String address, Integer pageSize, Integer offset) throws ExecutionException, InterruptedException {
        Integer _pageSize = (pageSize == null || pageSize < 0 || pageSize > 50) ? 50 : pageSize;
        Integer _offset = (offset == null || offset < 0) ? 0 : offset;
        String uri = BaseUrl.getInstance().getUrl() + "/v3/bitcoin/transaction/address/" + address + "?pageSize=" + _pageSize + "&offset=" + _offset;
        return Async.get(uri, BtcTx[].class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/BtcGetRawTransaction" target="_blank">Tatum API documentation</a>
     *
     * @param hash the hash
     * @return the btc tx
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public BtcTx btcGetTransaction(String hash) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/bitcoin/transaction/" + hash;
        return Async.get(uri, BtcTx.class);
    }
}
