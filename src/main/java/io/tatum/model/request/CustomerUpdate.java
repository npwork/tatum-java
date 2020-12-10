package io.tatum.model.request;

import io.tatum.model.response.ledger.Fiat;

import javax.validation.constraints.Size;
import java.util.Optional;

public class CustomerUpdate {

    @Size(min = 2, max = 2)
    private Optional<Country> customerCountry;

    private Optional<Fiat> accountingCurrency;

    private Optional<Country>  providerCountry;

    @Size(min = 1, max = 100)
    private String externalId;

    public Optional<Country> getCustomerCountry() {
        return customerCountry;
    }

    public void setCustomerCountry(Optional<Country> customerCountry) {
        this.customerCountry = customerCountry;
    }

    public Optional<Fiat> getAccountingCurrency() {
        return accountingCurrency;
    }

    public void setAccountingCurrency(Optional<Fiat> accountingCurrency) {
        this.accountingCurrency = accountingCurrency;
    }

    public Optional<Country> getProviderCountry() {
        return providerCountry;
    }

    public void setProviderCountry(Optional<Country> providerCountry) {
        this.providerCountry = providerCountry;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }
}
