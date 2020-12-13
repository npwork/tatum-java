package io.tatum.ledger;

import io.tatum.model.request.OrderBookRequest;
import io.tatum.model.response.ledger.OrderBookResponse;
import io.tatum.utils.Async;
import io.tatum.utils.BaseUrl;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class OrderBook {

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/getHistoricalTrades" target="_blank">Tatum API documentation</a>
     */
    public OrderBookResponse[] getHistoricalTrades(Integer pageSize, Integer offset) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/trade/history?pageSize=" + pageSize + "&offset=" + offset;
        return Async.get(uri, OrderBookResponse[].class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/getBuyTrades" target="_blank">Tatum API documentation</a>
     */
    public OrderBookResponse[] getActiveBuyTrades(String id, Integer pageSize, Integer offset) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/trade/buy?id=" + id + "&pageSize=" + pageSize + "&offset=" + offset;
        return Async.get(uri, OrderBookResponse[].class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/getSellTrades" target="_blank">Tatum API documentation</a>
     */
    public OrderBookResponse[] getActiveSellTrades(String id, Integer pageSize, Integer offset) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/trade/sell?id=" + id + "&pageSize=" + pageSize + "&offset=" + offset;
        return Async.get(uri, OrderBookResponse[].class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/storeTrade" target="_blank">Tatum API documentation</a>
     */
    public String storeTrade(OrderBookRequest data) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/trade";
        return Async.post(uri, data, String.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/getTradeById" target="_blank">Tatum API documentation</a>
     */
    public OrderBookResponse getTradeById(String id) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/trade/" + id;
        return Async.get(uri, OrderBookResponse.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/deleteTrade" target="_blank">Tatum API documentation</a>
     */
    public void deleteTrade(String id) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/trade/" + id;
        Async.delete(uri);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/deleteAccountTrades" target="_blank">Tatum API documentation</a>
     */
    public void deleteAccountTrades(String id) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/trade/account/" + id;
        Async.delete(uri);
    }
}
