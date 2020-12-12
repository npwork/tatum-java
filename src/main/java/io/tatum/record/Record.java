package io.tatum.record;

import com.google.common.base.Strings;
import io.tatum.model.request.Currency;
import io.tatum.model.response.common.Rate;
import io.tatum.model.response.ledger.Blockage;
import io.tatum.utils.Async;
import io.tatum.utils.Env;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static io.tatum.constants.Constant.TATUM_API_URL;

public class Record {
    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/GetLog" target="_blank">Tatum API documentation</a>
     */
    public Rate getLogRecord(Currency chain, String id) throws IOException, ExecutionException, InterruptedException {
        String tatumApiUrl = Env.getTatumApiUrl();
        String uri = (Strings.isNullOrEmpty(tatumApiUrl) ? TATUM_API_URL : tatumApiUrl) + "/v3/record?chain=" + chain.getCurrency() + "&id=" + id;
        var record = Async.get(uri, Env.getTatumApiKey());
        // TO-DO
        return new Rate();
    }
}
