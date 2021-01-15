package io.tatum.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * The type Api key.
 */
public class ApiKey {

    private static ApiKey instance;

    private String apiKey;

    private ApiKey() {
        apiKey = System.getenv().get("TATUM_API_KEY");
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static synchronized ApiKey getInstance() {
        if (instance == null) {
            instance = new ApiKey();
        }
        return instance;
    }

    /**
     * Gets api key.
     *
     * @return the api key
     */
    public String getApiKey() {
        return apiKey;
    }
}
