package io.tatum.model.response.common;

import io.tatum.model.response.ledger.Fiat;

import java.math.BigDecimal;

public class Rate {

    private Money id;

    public Money getId() {
        return id;
    }

    public void setId(Money id) {
        this.id = id;
    }

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private Fiat basePair;

    public Fiat getBasePair() {
        return basePair;
    }

    public void setBasePair(Fiat basePair) {
        this.basePair = basePair;
    }

    private BigDecimal timestamp;

    public BigDecimal getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(BigDecimal timestamp) {
        this.timestamp = timestamp;
    }

    private String source;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
