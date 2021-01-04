package io.tatum.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Convert {

    private Convert() {
    }

    public static BigDecimal toSatoshis(String amount) {
        return new BigDecimal(amount).multiply(BigDecimal.valueOf(100000000)).setScale(8, RoundingMode.FLOOR);
    }

    public static BigDecimal toSatoshis(BigDecimal amount) {
        return amount.multiply(BigDecimal.valueOf(100000000)).setScale(8, RoundingMode.FLOOR);
    }
}
