package io.tatum.ledger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import io.tatum.model.request.OrderBookRequest;
import io.tatum.model.response.ledger.OrderBookResponse;
import io.tatum.utils.Async;
import io.tatum.utils.Env;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutionException;

import static io.tatum.constants.Constant.TATUM_API_URL;

public class OrderBook {

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/getHistoricalTrades" target="_blank">Tatum API documentation</a>
     */
    public OrderBookResponse[] getHistoricalTrades(Integer pageSize, Integer offset) throws IOException, ExecutionException, InterruptedException {
        String tatumApiUrl = Env.getTatumApiUrl();
        String uri = (Strings.isNullOrEmpty(tatumApiUrl) ? TATUM_API_URL : tatumApiUrl) + "/v3/trade/history?pageSize=" + pageSize + "&offset=" + offset;
        var trades = Async.get(uri, Env.getTatumApiKey());
        // TO-DO
        return new OrderBookResponse[]{};
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/getBuyTrades" target="_blank">Tatum API documentation</a>
     */
    public OrderBookResponse[] getActiveBuyTrades(String id, Integer pageSize, Integer offset) throws IOException, ExecutionException, InterruptedException {
        String tatumApiUrl = Env.getTatumApiUrl();
        String uri = (Strings.isNullOrEmpty(tatumApiUrl) ? TATUM_API_URL : tatumApiUrl) + "/v3/trade/buy?id=" + id + "&pageSize=" + pageSize + "&offset=" + offset;
        var trades = Async.get(uri, Env.getTatumApiKey());
        // TO-DO
        return new OrderBookResponse[]{};
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/getSellTrades" target="_blank">Tatum API documentation</a>
     */
    public OrderBookResponse[] getActiveSellTrades(String id, Integer pageSize, Integer offset) throws IOException, ExecutionException, InterruptedException {
        String tatumApiUrl = Env.getTatumApiUrl();
        String uri = (Strings.isNullOrEmpty(tatumApiUrl) ? TATUM_API_URL : tatumApiUrl) + "/v3/trade/sell?id=" + id + "&pageSize=" + pageSize + "&offset=" + offset;
        var trades = Async.get(uri, Env.getTatumApiKey());
        // TO-DO
        return new OrderBookResponse[]{};
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/storeTrade" target="_blank">Tatum API documentation</a>
     */
    public String storeTrade(OrderBookRequest data) throws IOException, ExecutionException, InterruptedException {
        String tatumApiUrl = Env.getTatumApiUrl();
        String uri = (Strings.isNullOrEmpty(tatumApiUrl) ? TATUM_API_URL : tatumApiUrl) + "/v3/trade";
        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(data);

        HttpResponse id = Async.post(uri, Env.getTatumApiKey(), requestBody);
        // TO-DO
        return id.toString();
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/getTradeById" target="_blank">Tatum API documentation</a>
     */
    public OrderBookResponse getTradeById(String id) throws IOException, ExecutionException, InterruptedException {
        String tatumApiUrl = Env.getTatumApiUrl();
        String uri = (Strings.isNullOrEmpty(tatumApiUrl) ? TATUM_API_URL : tatumApiUrl) + "/v3/trade/" + id;
        var trade = Async.get(uri, Env.getTatumApiKey());
        // TO-DO
        return new OrderBookResponse();
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/deleteTrade" target="_blank">Tatum API documentation</a>
     */
    public void deleteTrade(String id) throws IOException, ExecutionException, InterruptedException {
        String tatumApiUrl = Env.getTatumApiUrl();
        String uri = (Strings.isNullOrEmpty(tatumApiUrl) ? TATUM_API_URL : tatumApiUrl) + "/v3/trade/" + id;
        Async.delete(uri);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/deleteAccountTrades" target="_blank">Tatum API documentation</a>
     */
    public void deleteAccountTrades(String id) throws IOException, ExecutionException, InterruptedException {
        String tatumApiUrl = Env.getTatumApiUrl();
        String uri = (Strings.isNullOrEmpty(tatumApiUrl) ? TATUM_API_URL : tatumApiUrl) + "/v3/trade/account/" + id;
        Async.delete(uri);
    }
}
