package io.tatum.model.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.tatum.utils.MapperFactory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EnumTest {

    @Test
    public void deserializeEnumTest() throws JsonProcessingException {

        Currency _currency = Currency.valueOf("ETH");
        assertEquals(_currency, Currency.ETH);

    }
}
