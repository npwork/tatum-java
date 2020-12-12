package io.tatum.ledger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import io.tatum.model.request.CreateSubscription;
import io.tatum.model.response.ledger.Subscription;
import io.tatum.utils.Async;
import io.tatum.utils.Env;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutionException;

import static io.tatum.constants.Constant.TATUM_API_URL;

public class LedgerSubscription {

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/createSubscription" target="_blank">Tatum API documentation</a>
     */
    public String createNewSubscription(CreateSubscription data) throws IOException, ExecutionException, InterruptedException {
        // TO-DO id: string
        String tatumApiUrl = Env.getTatumApiUrl();
        String uri = (Strings.isNullOrEmpty(tatumApiUrl) ? TATUM_API_URL : tatumApiUrl) + "/v3/subscription";

        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(data);

        HttpResponse res = Async.post(uri, Env.getTatumApiKey(), requestBody);
        // TO-DO
        return "subscription";
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/getSubscriptions" target="_blank">Tatum API documentation</a>
     */
    public Subscription[] listActiveSubscriptions(Integer pageSize, Integer offset) throws IOException, ExecutionException, InterruptedException {
        String tatumApiUrl = Env.getTatumApiUrl();
        String uri = (Strings.isNullOrEmpty(tatumApiUrl) ? TATUM_API_URL : tatumApiUrl) + "/v3/subscription?pageSize=" + pageSize + "&offset=" + offset;
        var tx = Async.get(uri, Env.getTatumApiKey());
        // TO-DO
        return new Subscription[]{};
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/deleteSubscription" target="_blank">Tatum API documentation</a>
     */
    public void cancelExistingSubscription(String id) throws IOException, ExecutionException, InterruptedException {
        String tatumApiUrl = Env.getTatumApiUrl();
        String uri = (Strings.isNullOrEmpty(tatumApiUrl) ? TATUM_API_URL : tatumApiUrl) + "/v3/subscription/" + id;
        Async.delete(uri, Env.getTatumApiKey());
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/getSubscriptionReport" target="_blank">Tatum API documentation</a>
     */
    public <T> T obtainReportForSubscription(String id) throws IOException, ExecutionException, InterruptedException {
        String tatumApiUrl = Env.getTatumApiUrl();
        String uri = (Strings.isNullOrEmpty(tatumApiUrl) ? TATUM_API_URL : tatumApiUrl) + "/v3/subscription/report" + id;
        var tx = Async.get(uri, Env.getTatumApiKey());
        // TO-DO
        return (T) new Object();
    }
}
