package io.tatum.model.request;

public enum TrcType {
    TRC10("TRC10"),
    TRC20("TRC20");

    private String trcType;

    TrcType(final String trcType) {
        this.trcType = trcType;
    }

    /**
     * Gets country.
     *
     * @return the country
     */
    public String getTrcType() {
        return trcType;
    }
}
