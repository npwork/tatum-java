package io.tatum.model.request;

import javax.validation.constraints.NotEmpty;

public class CreateAccountsBatch {

    @NotEmpty()
    private CreateAccount[] accounts;

    public CreateAccount[] getAccounts() {
        return accounts;
    }

    public void setAccounts(CreateAccount[] accounts) {
        this.accounts = accounts;
    }
}
