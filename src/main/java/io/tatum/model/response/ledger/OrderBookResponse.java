package io.tatum.model.response.ledger;

import io.tatum.model.request.TradeType;

import java.math.BigDecimal;

/**
 * The type Order book response.
 */
public class OrderBookResponse {

    /**
     * ID of the trade.
     *
     * @type {string}
     * @memberof OrderBook
     */
    private String id;

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
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

    /**
     * Gets type.
     *
     * @return the type
     */
    public TradeType getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
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

    /**
     * Gets price.
     *
     * @return the price
     */
    public String getPrice() {
        return price;
    }

    /**
     * Sets price.
     *
     * @param price the price
     */
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

    /**
     * Gets amount.
     *
     * @return the amount
     */
    public String getAmount() {
        return amount;
    }

    /**
     * Sets amount.
     *
     * @param amount the amount
     */
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

    /**
     * Gets pair.
     *
     * @return the pair
     */
    public String getPair() {
        return pair;
    }

    /**
     * Sets pair.
     *
     * @param pair the pair
     */
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

    /**
     * Gets fill.
     *
     * @return the fill
     */
    public String getFill() {
        return fill;
    }

    /**
     * Sets fill.
     *
     * @param fill the fill
     */
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

    /**
     * Gets currency 1 account id.
     *
     * @return the currency 1 account id
     */
    public String getCurrency1AccountId() {
        return currency1AccountId;
    }

    /**
     * Sets currency 1 account id.
     *
     * @param currency1AccountId the currency 1 account id
     */
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

    /**
     * Gets currency 2 account id.
     *
     * @return the currency 2 account id
     */
    public String getCurrency2AccountId() {
        return currency2AccountId;
    }

    /**
     * Sets currency 2 account id.
     *
     * @param currency2AccountId the currency 2 account id
     */
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

    /**
     * Gets created.
     *
     * @return the created
     */
    public BigDecimal getCreated() {
        return created;
    }

    /**
     * Sets created.
     *
     * @param created the created
     */
    public void setCreated(BigDecimal created) {
        this.created = created;
    }
}
