package io.tatum.ledger;

import io.tatum.model.request.CustomerUpdate;
import io.tatum.model.response.common.Id;
import io.tatum.model.response.ledger.Customer;
import io.tatum.utils.Async;
import io.tatum.utils.BaseUrl;
import io.tatum.utils.ObjectValidator;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static io.tatum.constants.Constant.EMPTY_BODY;

/**
 * The type Ledger customer.
 */
public class LedgerCustomer {

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/getCustomerByExternalId" target="_blank">Tatum API documentation</a>
     *
     * @param id the id
     * @return the customer
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public Customer getCustomer(String id) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/ledger/customer/" + id;
        return Async.get(uri, Customer.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/findAllCustomers" target="_blank">Tatum API documentation</a>
     *
     * @param pageSize the page size
     * @param offset   the offset
     * @return the customer [ ]
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public Customer[] getAllCustomers(Integer pageSize, Integer offset) throws ExecutionException, InterruptedException {
        Integer _pageSize = (pageSize == null || pageSize < 0 || pageSize > 50) ? 50 : pageSize;
        Integer _offset = (offset == null || offset < 0) ? 0 : offset;
        String uri = BaseUrl.getInstance().getUrl() + "/v3/ledger/customer?pageSize=" + _pageSize + "&offset=" + _offset;
        return Async.get(uri, Customer[].class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/updateCustomer" target="_blank">Tatum API documentation</a>
     *
     * @param id   the id
     * @param data the data
     * @return the id
     * @throws IOException          the io exception
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public Id updateCustomer(String id, CustomerUpdate data) throws IOException, ExecutionException, InterruptedException {
        if (!ObjectValidator.isValidated(data)) {
            return null;
        }
        String uri = BaseUrl.getInstance().getUrl() + "/v3/ledger/customer/" + id;
        return Async.put(uri, data, Id.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/activateAccount" target="_blank">Tatum API documentation</a>
     *
     * @param id the id
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public void activateCustomer(String id) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/ledger/customer/" + id + "/activate";
        Async.put(uri, EMPTY_BODY);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/deactivateCustomer" target="_blank">Tatum API documentation</a>
     *
     * @param id the id
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public void deactivateCustomer(String id) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/ledger/customer/" + id + "/deactivate";
        Async.put(uri, EMPTY_BODY);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/enableCustomer" target="_blank">Tatum API documentation</a>
     *
     * @param id the id
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public void enableCustomer(String id) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/ledger/customer/" + id + "/enable";
        Async.put(uri, EMPTY_BODY);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/disableCustomer" target="_blank">Tatum API documentation</a>
     *
     * @param id the id
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public void disableCustomer(String id) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/ledger/customer/" + id + "/disable";
        Async.put(uri, EMPTY_BODY);
    }
}
