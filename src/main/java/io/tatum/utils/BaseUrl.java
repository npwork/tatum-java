package io.tatum.utils;

import com.google.common.base.Strings;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static io.tatum.constants.Constant.TATUM_API_URL;

/**
 * The type Base url.
 */
public class BaseUrl {

    private static BaseUrl instance;

    private String url;

    private BaseUrl() {
        url = System.getenv().get("TATUM_API_URL");
        url = Strings.isNullOrEmpty(url) ? TATUM_API_URL : url;
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static synchronized BaseUrl getInstance() {
        if (instance == null) {
            instance = new BaseUrl();
        }
        return instance;
    }

    /**
     * Gets url.
     *
     * @return the url
     */
    public String getUrl() {
        return url;
    }
}
