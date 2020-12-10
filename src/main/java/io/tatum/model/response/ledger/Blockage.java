package io.tatum.model.response.ledger;

import java.util.Optional;

public class Blockage {

    /**
     * ID of the blockage.
     * @type {string}
     * @memberof Blockage
     */
    private String id;

    /**
     * ID of the account this blockage is for.
     * @type {string}
     * @memberof Blockage
     */
    private String accountId;

    /**
     * Amount blocked on account.
     * @type {string}
     * @memberof Blockage
     */
    private String amount;

    /**
     * Type of blockage.
     * @type {string}
     * @memberof Blockage
     */
    private String type;

    /**
     * Description of blockage.
     * @type {string}
     * @memberof Blockage
     */
    private Optional<String> description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Optional<String> getDescription() {
        return description;
    }

    public void setDescription(Optional<String> description) {
        this.description = description;
    }
}
