package io.tatum.security;

import com.google.common.base.Strings;
import io.tatum.model.response.bch.BchInfo;
import io.tatum.utils.Async;
import io.tatum.utils.Env;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static io.tatum.constants.Constant.TATUM_API_URL;

public class Address {

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/CheckMalicousAddress" target="_blank">Tatum API documentation</a>
     */
    public String checkMaliciousAddress(String address) throws IOException, ExecutionException, InterruptedException {
        String tatumApiUrl = Env.getTatumApiUrl();
        String uri = (Strings.isNullOrEmpty(tatumApiUrl) ? TATUM_API_URL : tatumApiUrl) + "/v3/security/address" + address;
        String addr = Async.get(uri, Env.getTatumApiKey());
        // TO-DO
        // { status: string }
        return "status";
    }

}
