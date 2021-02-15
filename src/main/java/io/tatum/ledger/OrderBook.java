package io.tatum.ledger;

import io.tatum.model.request.OrderBookRequest;
import io.tatum.model.response.common.Id;
import io.tatum.model.response.ledger.OrderBookResponse;
import io.tatum.utils.Async;
import io.tatum.utils.BaseUrl;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * The type Order book.
 */
public class OrderBook {

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/getHistoricalTrades" target="_blank">Tatum API documentation</a>
     *
     * @param pageSize the page size
     * @param offset   the offset
     * @return the order book response [ ]
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public OrderBookResponse[] getHistoricalTrades(Integer pageSize, Integer offset, String id, String pair) throws ExecutionException, InterruptedException {
        Integer _pageSize = (pageSize == null || pageSize < 0 || pageSize > 50) ? 50 : pageSize;
        Integer _offset = (offset == null || offset < 0) ? 0 : offset;
        String uri = BaseUrl.getInstance().getUrl() + "/v3/trade/history?pageSize=" + _pageSize + "&offset=" + _offset;
        if (StringUtils.isNotEmpty(id)) {
            uri += "&id=" + id;
        }
        if (StringUtils.isNotEmpty(pair)) {
            uri += "&pair=" + pair;
        }
        return Async.get(uri, OrderBookResponse[].class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/getBuyTrades" target="_blank">Tatum API documentation</a>
     *
     * @param id       the id
     * @param pageSize the page size
     * @param offset   the offset
     * @return the order book response [ ]
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public OrderBookResponse[] getActiveBuyTrades(String id, Integer pageSize, Integer offset) throws ExecutionException, InterruptedException {
        Integer _pageSize = (pageSize == null || pageSize < 0 || pageSize > 50) ? 50 : pageSize;
        Integer _offset = (offset == null || offset < 0) ? 0 : offset;
        String uri = BaseUrl.getInstance().getUrl() + "/v3/trade/buy?id=" + id + "&pageSize=" + _pageSize + "&offset=" + _offset;
        return Async.get(uri, OrderBookResponse[].class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/getSellTrades" target="_blank">Tatum API documentation</a>
     *
     * @param id       the id
     * @param pageSize the page size
     * @param offset   the offset
     * @return the order book response [ ]
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public OrderBookResponse[] getActiveSellTrades(String id, Integer pageSize, Integer offset) throws ExecutionException, InterruptedException {
        Integer _pageSize = (pageSize == null || pageSize < 0 || pageSize > 50) ? 50 : pageSize;
        Integer _offset = (offset == null || offset < 0) ? 0 : offset;
        String uri = BaseUrl.getInstance().getUrl() + "/v3/trade/sell?id=" + id + "&pageSize=" + _pageSize + "&offset=" + _offset;
        return Async.get(uri, OrderBookResponse[].class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/storeTrade" target="_blank">Tatum API documentation</a>
     *
     * @param data the data
     * @return the id
     * @throws IOException          the io exception
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public Id storeTrade(OrderBookRequest data) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/trade";
        return Async.post(uri, data, Id.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/getTradeById" target="_blank">Tatum API documentation</a>
     *
     * @param id the id
     * @return the trade by id
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public OrderBookResponse getTradeById(String id) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/trade/" + id;
        return Async.get(uri, OrderBookResponse.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/deleteTrade" target="_blank">Tatum API documentation</a>
     *
     * @param id the id
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public void deleteTrade(String id) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/trade/" + id;
        Async.delete(uri);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/deleteAccountTrades" target="_blank">Tatum API documentation</a>
     *
     * @param id the id
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public void deleteAccountTrades(String id) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/trade/account/" + id;
        Async.delete(uri);
    }
}
