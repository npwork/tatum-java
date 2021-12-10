package io.tatum.record;

import io.tatum.model.request.Currency;
import io.tatum.model.response.eth.Log;
import io.tatum.utils.Async;
import io.tatum.utils.BaseUrl;

import java.util.concurrent.ExecutionException;

/**
 * The type Record.
 */
public class Record {
    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/GetLog" target="_blank">Tatum API documentation</a>
     *
     * @param chain the chain
     * @param id    the id
     * @return the log record
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public Log getLogRecord(Currency chain, String txId) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/record?chain=" + chain.currency + "&id=" + txId;
        return Async.get(uri, Log.class);
    }
}
