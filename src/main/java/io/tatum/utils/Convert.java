package io.tatum.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * The type Convert.
 */
public class Convert {

    private Convert() {
    }

    /**
     * To satoshis big decimal.
     *
     * @param amount the amount
     * @return the big decimal
     */
    public static BigDecimal toSatoshis(String amount) {
        return new BigDecimal(amount).multiply(BigDecimal.valueOf(100000000)).setScale(8, RoundingMode.FLOOR);
    }

    /**
     * To satoshis big decimal.
     *
     * @param amount the amount
     * @return the big decimal
     */
    public static BigDecimal toSatoshis(BigDecimal amount) {
        return amount.multiply(BigDecimal.valueOf(100000000)).setScale(8, RoundingMode.FLOOR);
    }
}
