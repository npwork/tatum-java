package io.tatum.model.response.ledger;

public class BookkeepingAccount {

    /**
     * For bookkeeping to distinct account purpose.
     * @type {string}
     * @memberof Account
     */
    private String accountCode;

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    /**
     * Account ID.
     * @type {string}
     * @memberof Account
     */
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @type {AccountBalance}
     * @memberof Account
     */
    private AccountBalance balance;

    public AccountBalance getBalance() {
        return balance;
    }

    public void setBalance(AccountBalance balance) {
        this.balance = balance;
    }

    /**
     * Time of account creation.
     * @type {string}
     * @memberof Account
     */
    private String created;

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    /**
     * Account currency. Supported values are BTC, LTC, BCH, ETH, XRP, Tatum Virtual Currencies started with VC_ prefix or ERC20 customer token created via Tatum Platform.
     * @type {string}
     * @memberof Account
     */
    private String currency;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * ID of newly created customer or existing customer associated with account.
     * @type {string}
     * @memberof Account
     */
    private String customerId;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    /**
     * Indicates whether account is frozen or not.
     * @type {boolean}
     * @memberof Account
     */
    private Boolean frozen;

    public Boolean getFrozen() {
        return frozen;
    }

    public void setFrozen(Boolean frozen) {
        this.frozen = frozen;
    }

    /**
     * Indicates whether account is active or not.
     * @type {boolean}
     * @memberof Account
     */
    private Boolean active;

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     * Extended public key to derive address from.
     * In case of XRP, this is account address, since address is defined as DestinationTag, which is address field.
     * In case of XLM, this is account address, since address is defined as message, which is address field.
     * @type {string}
     * @memberof Account
     */
    private String xpub;

    public String getXpub() {
        return xpub;
    }

    public void setXpub(String xpub) {
        this.xpub = xpub;
    }
}
