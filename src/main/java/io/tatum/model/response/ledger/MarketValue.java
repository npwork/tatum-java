package io.tatum.model.response.ledger;

import java.math.BigDecimal;
import java.util.Optional;

public class MarketValue {
    /**
     * Value of transaction in given base pair.
     *
     * @type {string}
     * @memberof MarketValue
     */
    private String amount;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     * Base pair.
     *
     * @type {string}
     * @memberof MarketValue
     */
    private Optional<Fiat> currency;

    public Optional<Fiat> getCurrency() {
        return currency;
    }

    public void setCurrency(Optional<Fiat> currency) {
        this.currency = currency;
    }

    /**
     * Date of validity of rate in UTC.
     *
     * @type {number}
     * @memberof MarketValue
     */
    private Optional<BigDecimal> sourceDate;

    public Optional<BigDecimal> getSourceDate() {
        return sourceDate;
    }

    public void setSourceDate(Optional<BigDecimal> sourceDate) {
        this.sourceDate = sourceDate;
    }

    /**
     * Source of base pair.
     *
     * @type {string}
     * @memberof MarketValue
     */
    private Optional<String> source;

    public Optional<String> getSource() {
        return source;
    }

    public void setSource(Optional<String> source) {
        this.source = source;
    }
}