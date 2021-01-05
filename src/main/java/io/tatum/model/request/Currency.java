package io.tatum.model.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.tatum.model.response.common.Money;

public enum Currency implements Money {
    BTC("BTC"),
    BCH("BCH"),
    LTC("LTC"),
    ETH("ETH"),
    XRP("XRP"),
    XLM("XLM"),
    VET("VET"),
    NEO("NEO"),
    BNB("BNB"),
    USDT("USDT"),
    LEO("LEO"),
    LINK("LINK"),
    UNI("UNI"),
    FREE("FREE"),
    MKR("MKR"),
    USDC("USDC"),
    BAT("BAT"),
    TUSD("TUSD"),
    PAX("PAX"),
    PLTC("PLTC"),
    ADA("ADA"),
    XCON("XCON"),
    MMY("MMY"),
    PAXG("PAXG");

    private final String currency;

    Currency(final String currency) {
        this.currency = currency;
    }

    public String getCurrency() {
        return this.currency;
    }
}
