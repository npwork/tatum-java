package io.tatum.ledger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import io.tatum.model.request.CreateSubscription;
import io.tatum.model.response.ledger.Account;
import io.tatum.model.response.ledger.Subscription;
import io.tatum.model.response.ledger.Transaction;
import io.tatum.utils.Async;
import io.tatum.utils.BaseUrl;
import io.tatum.utils.Env;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.concurrent.ExecutionException;

import static io.tatum.constants.Constant.TATUM_API_URL;

public class LedgerSubscription {

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/createSubscription" target="_blank">Tatum API documentation</a>
     */
    public String createNewSubscription(CreateSubscription data) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/subscription";
        return Async.post(uri, data, String.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/getSubscriptions" target="_blank">Tatum API documentation</a>
     */
    public Subscription[] listActiveSubscriptions(Integer pageSize, Integer offset) throws IOException, ExecutionException, InterruptedException {
        Integer _pageSize = (pageSize == null || pageSize < 0 || pageSize > 50) ? 50 : pageSize;
        Integer _offset = (offset == null || offset < 0) ? 0 : offset;
        String uri = BaseUrl.getInstance().getUrl() + "/v3/subscription?pageSize=" + _pageSize + "&offset=" + _offset;
        return Async.get(uri, Subscription[].class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/deleteSubscription" target="_blank">Tatum API documentation</a>
     */
    public void cancelExistingSubscription(String id) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/subscription/" + id;
        Async.delete(uri);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/getSubscriptionReport" target="_blank">Tatum API documentation</a>
     */
    public Object[] obtainReportForSubscription(String id) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/subscription/report/" + id;
        var res = Async.get(uri);
        if (res.statusCode() == 200) {
            var objectMapper = new ObjectMapper();
            try {
                Transaction[] transactions = objectMapper.readValue((String) res.body(), Transaction[].class);
                return transactions;
            } catch (JsonProcessingException e) {
            }

            try {
                Account[] accounts = objectMapper.readValue((String) res.body(), Account[].class);
                return accounts;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
