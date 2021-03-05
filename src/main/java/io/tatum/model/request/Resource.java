package io.tatum.model.request;

public enum Resource {
    BANDWIDTH(0),
    ENERGY(1);

    private final int resource;

    Resource(int resource) {
        this.resource = resource;
    }

    public int getResource() {
        return this.resource;
    }
}
