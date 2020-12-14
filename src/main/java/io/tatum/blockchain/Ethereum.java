package io.tatum.blockchain;

import io.tatum.model.response.common.TransactionHash;
import io.tatum.model.response.eth.Balance;
import io.tatum.model.response.eth.EthBlock;
import io.tatum.model.response.eth.EthTx;
import io.tatum.utils.Async;
import io.tatum.utils.BaseUrl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

public final class Ethereum {

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/EthBroadcast" target="_blank">Tatum API documentation</a>
     */
    public TransactionHash ethBroadcast(final String txData, final String signatureId) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/ethereum/broadcast";
        return BlockchainUtil.broadcast(uri, txData, signatureId);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/EthGetTransactionCount" target="_blank">Tatum API documentation</a>
     */
    public BigDecimal ethGetTransactionsCount(String address) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/ethereum/transaction/count/" + address;
        return Async.get(uri, BigDecimal.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/EthGetCurrentBlock" target="_blank">Tatum API documentation</a>
     */
    public BigDecimal ethGetCurrentBlock() throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/ethereum/block/current";
        return Async.get(uri, BigDecimal.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/EthGetBlock" target="_blank">Tatum API documentation</a>
     */
    public EthBlock ethGetBlock(String hash) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/ethereum/block/" + hash;
        return Async.get(uri, EthBlock.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/EthGetBalance" target="_blank">Tatum API documentation</a>
     */
    public BigDecimal ethGetAccountBalance(String address) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/ethereum/account/balance/" + address;
        Balance res = Async.get(uri, Balance.class);
        if (res != null) {
            return res.getBalance();
        }
        return null;
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/EthErc20GetBalance" target="_blank">Tatum API documentation</a>
     */
    public BigDecimal ethGetAccountErc20Address(String address, String contractAddress) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/ethereum/account/balance/erc20/" + address + "?contractAddress=" + contractAddress;
        Balance res = Async.get(uri, Balance.class);
        if (res != null) {
            return res.getBalance();
        }
        return null;
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/EthGetTransaction" target="_blank">Tatum API documentation</a>
     */
    public EthTx ethGetTransaction(String hash) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/ethereum/transaction/" + hash;
        return Async.get(uri, EthTx.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/EthGetTransactionByAddress" target="_blank">Tatum API documentation</a>
     */
    public EthTx[] ethGetAccountTransactions(String address, Integer pageSize, Integer offset) throws ExecutionException, InterruptedException {
        Integer _pageSize = (pageSize == null || pageSize < 0 || pageSize > 50) ? 50 : pageSize;
        Integer _offset = (offset == null || offset < 0) ? 0 : offset;
        String uri = BaseUrl.getInstance().getUrl() + "/v3/ethereum/account/transaction/" + address + "?pageSize=" + _pageSize + "&offset=" + _offset;
        return Async.get(uri, EthTx[].class);
    }
}
