package io.tatum.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MapperFactory {

    private static ObjectMapper objectMapper;

    private MapperFactory() {
    }

    public static synchronized ObjectMapper get() {
        if (objectMapper == null) {
            return new ObjectMapper();
        }
        return objectMapper;
    }
}
