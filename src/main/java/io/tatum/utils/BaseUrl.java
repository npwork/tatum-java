package io.tatum.utils;

import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static io.tatum.constants.Constant.TATUM_API_URL;

public class BaseUrl {

    private static BaseUrl instance;

    private String url;

    private BaseUrl() {

        Properties appProps = new Properties();
        String appConfigPath = "config.properties";
        InputStream input = BaseUrl.class.getClassLoader().getResourceAsStream(appConfigPath);
        if (input == null) {
            url = null;
        }
        try {
            appProps.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            url = null;
        }
        url = appProps.getProperty("tatum.api.url");
        url = Strings.isNullOrEmpty(url) ? TATUM_API_URL : url;
    }

    public static synchronized BaseUrl getInstance() {
        if (instance == null) {
            instance = new BaseUrl();
        }
        return instance;
    }

    public String getUrl() {
        return url;
    }
}
