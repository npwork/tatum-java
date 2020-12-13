package io.tatum.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApiKey {

    private static ApiKey instance;

    private String apiKey;

    private ApiKey() {

        Properties appProps = new Properties();
        String appConfigPath = "config.properties";
        InputStream input = ApiKey.class.getClassLoader().getResourceAsStream(appConfigPath);
        if (input == null) {
            apiKey = null;
        }
        try {
            appProps.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            apiKey = null;
        }
        apiKey = appProps.getProperty("tatum.api.key");
    }

    public static synchronized ApiKey getInstance() {
        if (instance == null) {
            instance = new ApiKey();
        }
        return instance;
    }

    public String getApiKey() {
        return apiKey;
    }
}
