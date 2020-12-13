package io.tatum.record;

import io.tatum.model.request.Currency;
import io.tatum.model.response.common.Rate;
import io.tatum.utils.Async;
import io.tatum.utils.BaseUrl;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Record {
    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/GetLog" target="_blank">Tatum API documentation</a>
     */
    public Rate getLogRecord(Currency chain, String id) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/record?chain=" + chain.getCurrency() + "&id=" + id;
        return Async.get(uri, Rate.class);
    }
}
