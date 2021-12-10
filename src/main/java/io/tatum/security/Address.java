package io.tatum.security;

import io.tatum.model.response.security.Status;
import io.tatum.utils.Async;
import io.tatum.utils.BaseUrl;

import java.util.concurrent.ExecutionException;

/**
 * The type Address.
 */
public class Address {

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/CheckMalicousAddress" target="_blank">Tatum API documentation</a>
     *
     * @param address the address
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public Boolean checkMaliciousAddress(String address) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/security/address/" + address;
        var status = Async.get(uri, Status.class);
        if (status != null) {
            return status.getStatus().equals("valid");
        }
        return null;
    }

}
