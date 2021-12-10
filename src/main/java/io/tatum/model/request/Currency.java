package io.tatum.model.request;

import io.tatum.model.response.common.Money;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The enum Currency.
 */
@AllArgsConstructor
public enum Currency implements Money {
    /**
     * Btc currency.
     */
    BTC("BTC"),
    /**
     * Bch currency.
     */
    BCH("BCH"),
    /**
     * Ltc currency.
     */
    LTC("LTC"),
    /**
     * Eth currency.
     */
    ETH("ETH"),
    /**
     * Xrp currency.
     */
    XRP("XRP"),
    /**
     * Xlm currency.
     */
    XLM("XLM"),
    /**
     * Vet currency.
     */
    VET("VET"),
    /**
     * Neo currency.
     */
    NEO("NEO"),
    /**
     * Bnb currency.
     */
    BNB("BNB"),
    /**
     * Usdt currency.
     */
    USDT("USDT"),
    /**
     * Leo currency.
     */
    LEO("LEO"),
    /**
     * Link currency.
     */
    LINK("LINK"),

    WBTC("WBTC"),

    /**
     * Uni currency.
     */
    UNI("UNI"),
    /**
     * Free currency.
     */
    FREE("FREE"),
    /**
     * Mkr currency.
     */
    MKR("MKR"),
    /**
     * Usdc currency.
     */
    USDC("USDC"),
    /**
     * Bat currency.
     */
    BAT("BAT"),
    /**
     * Tusd currency.
     */
    TUSD("TUSD"),
    /**
     * Pax currency.
     */
    PAX("PAX"),
    /**
     * Pltc currency.
     */
    PLTC("PLTC"),
    /**
     * Xcon currency.
     */
    XCON("XCON"),
    /**
     * Mmy currency.
     */
    MMY("MMY"),
    /**
     * Paxg currency.
     */
    PAXG("PAXG"),

    /**
     * TRON currency.
     */
    TRON("TRON"),

    /**
     * USDT_TRON
     */
    USDT_TRON("USDT_TRON");

    public final String currency;

    public static List<Currency> supportedCurrencies() {
        return Arrays.asList(
                Currency.BTC, Currency.BCH, Currency.LTC,
                Currency.ETH, Currency.XRP, Currency.BNB,
                Currency.TRON, Currency.XLM, Currency.VET
        );
    }

    public static List<Currency> supportsXpub() {
        return Arrays.asList(Currency.ETH, Currency.XRP, Currency.BNB, Currency.XLM, Currency.VET);
    }

    public static List<Currency> doesNotSupportXpub() {
        List<Currency> supportsXpub = supportsXpub();
        return Arrays.stream(Currency.values()).filter(c -> !supportsXpub.contains(c)).collect(Collectors.toList());
    }

    public static List<Currency> virtualCurrencies() {
        List<Currency> supported = supportedCurrencies();
        return Arrays.stream(Currency.values()).filter(c -> !supported.contains(c)).collect(Collectors.toList());
    }
}
