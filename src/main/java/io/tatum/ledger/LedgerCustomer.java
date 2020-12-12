package io.tatum.ledger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import io.tatum.model.request.CustomerUpdate;
import io.tatum.model.response.ledger.Blockage;
import io.tatum.model.response.ledger.Customer;
import io.tatum.utils.Async;
import io.tatum.utils.Env;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static io.tatum.constants.Constant.TATUM_API_URL;

public class LedgerCustomer {

    public static final String EMPTY_BODY = "{}";

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/getCustomerByExternalId" target="_blank">Tatum API documentation</a>
     */
    public Customer getCustomer(String id) throws IOException, ExecutionException, InterruptedException {
        String tatumApiUrl = Env.getTatumApiUrl();
        String uri = (Strings.isNullOrEmpty(tatumApiUrl) ? TATUM_API_URL : tatumApiUrl) + "/v3/ledger/customer" + id;
        var customer = Async.get(uri, Env.getTatumApiKey());
        // TO-DO
        return new Customer();
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/findAllCustomers" target="_blank">Tatum API documentation</a>
     */
    public Customer[] getAllCustomers(Integer pageSize, Integer offset) throws IOException, ExecutionException, InterruptedException {
        String tatumApiUrl = Env.getTatumApiUrl();
        String uri = (Strings.isNullOrEmpty(tatumApiUrl) ? TATUM_API_URL : tatumApiUrl) + "/v3/ledger/customer?pageSize=" + pageSize + "&offset=" + offset;
        var tx = Async.get(uri, Env.getTatumApiKey());
        // TO-DO
        return new Customer[]{};
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/updateCustomer" target="_blank">Tatum API documentation</a>
     */
    public String updateCustomer(String id, CustomerUpdate data) throws IOException, ExecutionException, InterruptedException {
//        await validateOrReject(data);
//        TO-DO
        String tatumApiUrl = Env.getTatumApiUrl();
        String uri = (Strings.isNullOrEmpty(tatumApiUrl) ? TATUM_API_URL : tatumApiUrl) + "/v3/ledger/customer/" + id;
        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(data);
        String res = Async.put(uri, Env.getTatumApiKey(), requestBody);
        // TO-DO { id: string }
        return res;
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/activateAccount" target="_blank">Tatum API documentation</a>
     */
    public void activateCustomer(String id) throws IOException, ExecutionException, InterruptedException {
        String tatumApiUrl = Env.getTatumApiUrl();
        String uri = (Strings.isNullOrEmpty(tatumApiUrl) ? TATUM_API_URL : tatumApiUrl) + "/v3/ledger/customer/" + id + "/activate";
        String requestBody = EMPTY_BODY;
        Async.put(uri, Env.getTatumApiKey(), requestBody);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/deactivateCustomer" target="_blank">Tatum API documentation</a>
     */
    public void deactivateCustomer(String id) throws IOException, ExecutionException, InterruptedException {
        String tatumApiUrl = Env.getTatumApiUrl();
        String uri = (Strings.isNullOrEmpty(tatumApiUrl) ? TATUM_API_URL : tatumApiUrl) + "/v3/ledger/customer/" + id + "/deactivate";
        String requestBody = EMPTY_BODY;
        Async.put(uri, Env.getTatumApiKey(), requestBody);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/enableCustomer" target="_blank">Tatum API documentation</a>
     */
    public void enableCustomer(String id) throws IOException, ExecutionException, InterruptedException {
        String tatumApiUrl = Env.getTatumApiUrl();
        String uri = (Strings.isNullOrEmpty(tatumApiUrl) ? TATUM_API_URL : tatumApiUrl) + "/v3/ledger/customer/" + id + "/enable";
        String requestBody = EMPTY_BODY;
        Async.put(uri, Env.getTatumApiKey(), requestBody);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/disableCustomer" target="_blank">Tatum API documentation</a>
     */
    public void disableCustomer(String id) throws IOException, ExecutionException, InterruptedException {
        String tatumApiUrl = Env.getTatumApiUrl();
        String uri = (Strings.isNullOrEmpty(tatumApiUrl) ? TATUM_API_URL : tatumApiUrl) + "/v3/ledger/customer/" + id + "/disable";
        String requestBody = EMPTY_BODY;
        Async.put(uri, Env.getTatumApiKey(), requestBody);
    }
}
