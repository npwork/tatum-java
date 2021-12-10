package io.tatum.blockchain;

import io.tatum.model.response.common.TransactionHash;
import io.tatum.model.response.eth.Balance;
import io.tatum.model.response.eth.EthBlock;
import io.tatum.model.response.eth.EthTx;
import io.tatum.utils.Async;
import io.tatum.utils.BaseUrl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

/**
 * The type Ethereum.
 */
public final class Ethereum {

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/EthBroadcast" target="_blank">Tatum API documentation</a>
     *
     * @param txData      the tx data
     * @param signatureId the signature id
     * @return the transaction hash
     * @throws IOException          the io exception
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public TransactionHash ethBroadcast(final String txData, final String signatureId) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/ethereum/broadcast";
        return BlockchainUtil.broadcast(uri, txData, signatureId);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/EthGetTransactionCount" target="_blank">Tatum API documentation</a>
     *
     * @param address the address
     * @return the big integer
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public BigInteger ethGetTransactionsCount(String address) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/ethereum/transaction/count/" + address;
        return Async.get(uri, BigInteger.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/EthGetCurrentBlock" target="_blank">Tatum API documentation</a>
     *
     * @return the big decimal
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public BigDecimal ethGetCurrentBlock() throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/ethereum/block/current";
        return Async.get(uri, BigDecimal.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/EthGetBlock" target="_blank">Tatum API documentation</a>
     *
     * @param hash the hash
     * @return the eth block
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public EthBlock ethGetBlock(String hash) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/ethereum/block/" + hash;
        return Async.get(uri, EthBlock.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/EthGetBalance" target="_blank">Tatum API documentation</a>
     *
     * @param address the address
     * @return the big decimal
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public BigDecimal ethGetAccountBalance(String address) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/ethereum/account/balance/" + address;
        Balance res = Async.get(uri, Balance.class);
        if (res != null) {
            return res.getBalance();
        }
        return null;
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/EthErc20GetBalance" target="_blank">Tatum API documentation</a>
     *
     * @param address         the address
     * @param contractAddress the contract address
     * @return the big decimal
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public BigDecimal ethGetAccountErc20Address(String address, String contractAddress) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/ethereum/account/balance/erc20/" + address + "?contractAddress=" + contractAddress;
        Balance res = Async.get(uri, Balance.class);
        if (res != null) {
            return res.getBalance();
        }
        return null;
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/EthGetTransaction" target="_blank">Tatum API documentation</a>
     *
     * @param hash the hash
     * @return the eth tx
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public EthTx ethGetTransaction(String hash) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/ethereum/transaction/" + hash;
        return Async.get(uri, EthTx.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/EthGetTransactionByAddress" target="_blank">Tatum API documentation</a>
     *
     * @param address  the address
     * @param pageSize the page size
     * @param offset   the offset
     * @return the eth tx [ ]
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public EthTx[] ethGetAccountTransactions(String address, Integer pageSize, Integer offset) throws ExecutionException, InterruptedException {
        int _pageSize = (pageSize == null || pageSize < 0 || pageSize > 50) ? 50 : pageSize;
        int _offset = (offset == null || offset < 0) ? 0 : offset;
        String uri = BaseUrl.getInstance().getUrl() + "/v3/ethereum/account/transaction/" + address + "?pageSize=" + _pageSize + "&offset=" + _offset;
        return Async.get(uri, EthTx[].class);
    }

    public EthTx[] ethGetAccountTransactions(String address) throws ExecutionException, InterruptedException {
        return ethGetAccountTransactions(address, null, null);
    }

}
