package io.tatum.blockchain;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.tatum.model.response.common.TransactionHash;
import io.tatum.model.response.eth.EthBlock;
import io.tatum.model.response.eth.EthTx;
import io.tatum.utils.ApiKey;
import io.tatum.utils.Async;
import io.tatum.utils.BaseUrl;
import io.tatum.utils.Env;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutionException;

public final class Ethereum {

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/EthBroadcast" target="_blank">Tatum API documentation</a>
     */
    public TransactionHash ethBroadcast(final String txData, final String signatureId) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().url + "/v3/ethereum/broadcast";
        return BlockchainUtil.broadcast(uri, txData, signatureId);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/EthGetTransactionCount" target="_blank">Tatum API documentation</a>
     */
    public BigDecimal ethGetTransactionsCount(String address) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().url + "/v3/ethereum/transaction/count/" + address;
        HttpResponse res = Async.get(uri, ApiKey.getInstance().apiKey);

        BigDecimal count = BigDecimal.ZERO;
        if (res.statusCode() == 200) {
            var objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString (res.body());
            count = new BigDecimal(json);
        }

        return count;
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/EthGetCurrentBlock" target="_blank">Tatum API documentation</a>
     */
    public BigDecimal ethGetCurrentBlock() throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().url + "/v3/ethereum/block/current";
        HttpResponse res = Async.get(uri, ApiKey.getInstance().apiKey);

        BigDecimal block = BigDecimal.ZERO;
        if (res.statusCode() == 200) {
            var objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString (res.body());
            block = new BigDecimal(json);
        }

        return block;
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/EthGetBlock" target="_blank">Tatum API documentation</a>
     */
    public EthBlock ethGetBlock(String hash) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().url + "/v3/ethereum/block/" + hash;
        return Async.get(uri, ApiKey.getInstance().apiKey, EthBlock.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/EthGetBalance" target="_blank">Tatum API documentation</a>
     */
    public BigDecimal ethGetAccountBalance(String address) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().url + "/v3/ethereum/account/balance" + address;
        HttpResponse res = Async.get(uri, ApiKey.getInstance().apiKey);

        BigDecimal balance = BigDecimal.ZERO;
        if (res.statusCode() == 200) {
            var jsonObject = new JSONObject(res.body());
            String bal = jsonObject.getString("balance");
            balance = new BigDecimal(bal);
        }
        return balance;
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/EthErc20GetBalance" target="_blank">Tatum API documentation</a>
     */
    public BigDecimal ethGetAccountErc20Address(String address, String contractAddress) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().url + "/v3/ethereum/account/balance/erc20/" + address + "?contractAddress=" + contractAddress;
        HttpResponse res = Async.get(uri, ApiKey.getInstance().apiKey);

        BigDecimal balance = BigDecimal.ZERO;
        if (res.statusCode() == 200) {
            var jsonObject = new JSONObject(res.body());
            String bal = jsonObject.getString("balance");
            balance = new BigDecimal(bal);
        }
        return balance;
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/EthGetTransaction" target="_blank">Tatum API documentation</a>
     */
    public EthTx ethGetTransaction(String hash) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().url + "/v3/ethereum/transaction/" + hash;
        return Async.get(uri, ApiKey.getInstance().apiKey, EthTx.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/EthGetTransactionByAddress" target="_blank">Tatum API documentation</a>
     */
    public EthTx[] ethGetAccountTransactions(String address, Integer pageSize, Integer offset) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().url + "/v3/ethereum/transaction/" + address + "?pageSize=" + pageSize + "&offset=" + offset;
        return Async.get(uri, ApiKey.getInstance().apiKey, EthTx[].class);
    }
}
