package io.tatum.model.response.ledger;

public class AccountBalance {

    /**
     * Account balance represents all assets on the account, available and blocked.
     * @type {string}
     * @memberof AccountBalance
     */
    private String accountBalance;

    public String getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(String accountBalance) {
        this.accountBalance = accountBalance;
    }

    /**
     * Available balance on the account represents account balance minus blocked amount on the account.
     * If the account is frozen or customer is disabled, the available balance will be 0.
     * Available balance should be user do determine how much can customer send or withdraw from the account.
     * @type {string}
     * @memberof AccountBalance
     */
    private String availableBalance;

    public String getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(String availableBalance) {
        this.availableBalance = availableBalance;
    }
}
