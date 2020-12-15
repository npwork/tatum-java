package io.tatum.ledger;

import io.tatum.model.request.OrderBookRequest;
import io.tatum.model.response.common.Id;
import io.tatum.model.response.ledger.OrderBookResponse;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class OrderBookTest {

    @Test
    public void getHistoricalTradesTest() throws ExecutionException, InterruptedException {
        OrderBook orderBook = new OrderBook();
        OrderBookResponse[] orderBookResponses = orderBook.getHistoricalTrades(null, null);
        if (orderBookResponses != null && orderBookResponses.length > 0) {
            System.out.println(orderBookResponses[0]);
        }
    }

    @Test
    public void getActiveBuyTradesTest() throws ExecutionException, InterruptedException {
        OrderBook orderBook = new OrderBook();
        String id = "5fd77feea8acfcccef97a98b";
        OrderBookResponse[] orderBookResponses = orderBook.getActiveBuyTrades(id, null, null);
        if (orderBookResponses != null && orderBookResponses.length > 0) {
            System.out.println(orderBookResponses[0]);
        }
    }

    @Test
    public void getActiveSellTradesTest() throws ExecutionException, InterruptedException {
        OrderBook orderBook = new OrderBook();
        String id = "5fd77feea8acfcccef97a98b";
        OrderBookResponse[] orderBookResponses = orderBook.getActiveSellTrades(id, null, null);
        if (orderBookResponses != null && orderBookResponses.length > 0) {
            System.out.println(orderBookResponses[0]);
        }
    }

    @Test
    public void storeTradeTest() throws ExecutionException, InterruptedException, IOException {
        OrderBook orderBook = new OrderBook();
        OrderBookRequest data = new OrderBookRequest();
        Id id = orderBook.storeTrade(data);
        System.out.println(id);
    }

    @Test
    public void getTradeByIdTest() throws ExecutionException, InterruptedException {
        OrderBook orderBook = new OrderBook();
        String id = null;
        OrderBookResponse orderBookResponse = orderBook.getTradeById(id);
        System.out.println(orderBookResponse);
    }

    @Test
    public void deleteTradeTest() throws ExecutionException, InterruptedException {
        OrderBook orderBook = new OrderBook();
        String id = null;
        orderBook.deleteTrade(id);
    }

    @Test
    public void deleteAccountTradesTest() throws ExecutionException, InterruptedException {
        OrderBook orderBook = new OrderBook();
        String id = null;
        orderBook.deleteAccountTrades(id);
    }
}
