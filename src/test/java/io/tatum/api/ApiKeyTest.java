package io.tatum.api;

import io.tatum.utils.ApiKey;
import io.tatum.utils.BaseUrl;
import org.junit.Test;

public class ApiKeyTest {

    @Test
    public void getApiKeyTest() {
        System.out.println(ApiKey.getInstance().getApiKey());
        System.out.println(BaseUrl.getInstance().getUrl());
    }
}
