package io.tatum.model.response.ledger;

public class Subscription<T> {
    /**
     * Type of the subscription.
     *
     * @type {string}
     * @memberof Subscription
     */
    private SubscriptionType type;

    public SubscriptionType getType() {
        return type;
    }

    public void setType(SubscriptionType type) {
        this.type = type;
    }

    /**
     * ID of the subscription.
     *
     * @type {string}
     * @memberof Subscription
     */
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * Additional attributes based on the subscription type.
     *
     * @type {object}
     * @memberof Subscription
     */
    private T attr;

    public T getAttr() {
        return attr;
    }

    public void setAttr(T attr) {
        this.attr = attr;
    }
}