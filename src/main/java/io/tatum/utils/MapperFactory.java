package io.tatum.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The type Mapper factory.
 */
public class MapperFactory {

    private static ObjectMapper objectMapper;

    private MapperFactory() {
    }

    /**
     * Get object mapper.
     *
     * @return the object mapper
     */
    public static synchronized ObjectMapper get() {
        if (objectMapper == null) {
            return new ObjectMapper();
        }
        return objectMapper;
    }
}
