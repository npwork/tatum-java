package io.tatum.model.response.ledger;

import io.tatum.model.request.TradeType;

import java.math.BigDecimal;

public class OrderBookResponse {

    /**
     * ID of the trade.
     *
     * @type {string}
     * @memberof OrderBook
     */
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * Type of the trade, BUY or SELL.
     *
     * @type {string}
     * @memberof OrderBook
     */
    private TradeType type;

    public TradeType getType() {
        return type;
    }

    public void setType(TradeType type) {
        this.type = type;
    }

    /**
     * Price to buy / sell.
     *
     * @type {string}
     * @memberof OrderBook
     */
    private String price;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * Amount of the trade to be bought / sold.
     *
     * @type {string}
     * @memberof OrderBook
     */
    private String amount;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     * Trading pair.
     *
     * @type {string}
     * @memberof OrderBook
     */
    private String pair;

    public String getPair() {
        return pair;
    }

    public void setPair(String pair) {
        this.pair = pair;
    }

    /**
     * How much of the trade was already filled.
     *
     * @type {string}
     * @memberof OrderBook
     */
    private String fill;

    public String getFill() {
        return fill;
    }

    public void setFill(String fill) {
        this.fill = fill;
    }

    /**
     * ID of the account of the currency 1 trade currency.
     *
     * @type {string}
     * @memberof OrderBook
     */
    private String currency1AccountId;

    public String getCurrency1AccountId() {
        return currency1AccountId;
    }

    public void setCurrency1AccountId(String currency1AccountId) {
        this.currency1AccountId = currency1AccountId;
    }

    /**
     * ID of the account of the currency 2 trade currency.
     *
     * @type {string}
     * @memberof OrderBook
     */
    private String currency2AccountId;

    public String getCurrency2AccountId() {
        return currency2AccountId;
    }

    public void setCurrency2AccountId(String currency2AccountId) {
        this.currency2AccountId = currency2AccountId;
    }

    /**
     * Creation date, UTC millis.
     *
     * @type {string}
     * @memberof OrderBook
     */
    private BigDecimal created;

    public BigDecimal getCreated() {
        return created;
    }

    public void setCreated(BigDecimal created) {
        this.created = created;
    }
}
