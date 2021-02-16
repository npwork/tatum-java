package io.tatum.model.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.tatum.model.response.common.Money;

/**
 * The enum Currency.
 */
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
    PAXG("PAXG");

    private final String currency;

    Currency(final String currency) {
        this.currency = currency;
    }

    /**
     * Gets currency.
     *
     * @return the currency
     */
    public String getCurrency() {
        return this.currency;
    }
}
