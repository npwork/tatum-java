package io.tatum.api;

import io.tatum.utils.ApiKey;
import io.tatum.utils.BaseUrl;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ApiKeyTest {
    @Test
    public void apiKeyAndUrlAreInEnv() {
        assertNotNull(ApiKey.getInstance().getApiKey());
        assertNotNull(BaseUrl.getInstance().getUrl());
    }
}
