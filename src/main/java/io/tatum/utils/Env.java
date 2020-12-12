package io.tatum.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Env {

    public static String getTatumApiKey() throws IOException {
        Properties appProps = new Properties();
        String appConfigPath = "config.properties";
        InputStream input = Env.class.getClassLoader().getResourceAsStream(appConfigPath);
        if (input == null) {
            return StringUtils.EMPTY;
        }
        appProps.load(input);
        String appVersion = appProps.getProperty("tatum.api.key");
        return appVersion;
    }

    public static String getTatumApiUrl() throws IOException {
        Properties appProps = new Properties();
        String appConfigPath = "config.properties";
        InputStream input = Env.class.getClassLoader().getResourceAsStream(appConfigPath);
        if (input == null) {
            return StringUtils.EMPTY;
        }
        appProps.load(input);
        String appVersion = appProps.getProperty("tatum.api.url");
        return appVersion;
    }
}
