package io.tatum.model.request;

/**
 * The enum Trade type.
 */
public enum TradeType {

    /**
     * Buy trade type.
     */
    BUY("BUY"),
    /**
     * Sell trade type.
     */
    SELL("SELL");

    private final String tradeType;

    TradeType(final String tradeType) {
        this.tradeType = tradeType;
    }

    /**
     * Gets trade type.
     *
     * @return the trade type
     */
    public String getTradeType() {
        return tradeType;
    }
}
