package io.tatum.security;

import io.tatum.model.response.security.Status;
import io.tatum.utils.Async;
import io.tatum.utils.BaseUrl;

import java.util.concurrent.ExecutionException;

public class Address {

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/CheckMalicousAddress" target="_blank">Tatum API documentation</a>
     */
    public String checkMaliciousAddress(String address) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/security/address/" + address;
        var status = Async.get(uri, Status.class);
        if (status != null) {
            return status.getStatus();
        }
        return null;
    }

}
