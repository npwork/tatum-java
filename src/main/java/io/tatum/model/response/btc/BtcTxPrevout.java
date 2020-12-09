package io.tatum.model.response.btc;

import io.tatum.model.response.common.ITxPrevout;

import java.math.BigDecimal;

public class BtcTxPrevout implements ITxPrevout {

    private String hash;
    private BigDecimal index;

    @Override
    public String getHash() {
        return hash;
    }

    @Override
    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public BigDecimal getIndex() {
        return index;
    }

    @Override
    public void setIndex(BigDecimal index) {
        this.index = index;
    }

}
