package io.tatum.model.response.ledger;

/**
 * The type Subscription.
 *
 * @param <T> the type parameter
 */
public class Subscription<T> {
    /**
     * Type of the subscription.
     *
     * @type {string}
     * @memberof Subscription
     */
    private SubscriptionType type;

    /**
     * Gets type.
     *
     * @return the type
     */
    public SubscriptionType getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
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
     * Additional attributes based on the subscription type.
     *
     * @type {object}
     * @memberof Subscription
     */
    private T attr;

    /**
     * Gets attr.
     *
     * @return the attr
     */
    public T getAttr() {
        return attr;
    }

    /**
     * Sets attr.
     *
     * @param attr the attr
     */
    public void setAttr(T attr) {
        this.attr = attr;
    }
}
