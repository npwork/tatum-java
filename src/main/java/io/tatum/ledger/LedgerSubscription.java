package io.tatum.ledger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.tatum.model.request.CreateSubscription;
import io.tatum.model.response.common.Id;
import io.tatum.model.response.ledger.Account;
import io.tatum.model.response.ledger.Subscription;
import io.tatum.model.response.ledger.Transaction;
import io.tatum.utils.Async;
import io.tatum.utils.BaseUrl;
import io.tatum.utils.ObjectValidator;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class LedgerSubscription {

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/createSubscription" target="_blank">Tatum API documentation</a>
     */
    public Id createNewSubscription(CreateSubscription data) throws IOException, ExecutionException, InterruptedException {
        if (!ObjectValidator.isValidated(data)) {
            return null;
        }
        String uri = BaseUrl.getInstance().getUrl() + "/v3/subscription";
        return Async.post(uri, data, Id.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/getSubscriptions" target="_blank">Tatum API documentation</a>
     */
    public Subscription[] listActiveSubscriptions(Integer pageSize, Integer offset) throws ExecutionException, InterruptedException {
        Integer _pageSize = (pageSize == null || pageSize < 0 || pageSize > 50) ? 50 : pageSize;
        Integer _offset = (offset == null || offset < 0) ? 0 : offset;
        String uri = BaseUrl.getInstance().getUrl() + "/v3/subscription?pageSize=" + _pageSize + "&offset=" + _offset;
        return Async.get(uri, Subscription[].class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/deleteSubscription" target="_blank">Tatum API documentation</a>
     */
    public void cancelExistingSubscription(String id) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/subscription/" + id;
        Async.delete(uri);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/getSubscriptionReport" target="_blank">Tatum API documentation</a>
     */
    public Object[] obtainReportForSubscription(String id) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/subscription/report/" + id;
        var res = Async.get(uri);
        if (res != null) {
            var objectMapper = new ObjectMapper();
            try {
                return objectMapper.readValue(res, Transaction[].class);
            } catch (JsonProcessingException ignored) {
            }

            try {
                return objectMapper.readValue(res, Account[].class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
