package io.tatum.model.response.ledger;

import io.tatum.model.request.Country;

import java.util.Optional;

public class Customer {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String externalId;

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    private Optional<Country> customerCountry;

    public Optional<Country> getCustomerCountry() {
        return customerCountry;
    }

    public void setCustomerCountry(Optional<Country> customerCountry) {
        this.customerCountry = customerCountry;
    }

    private Optional<Fiat> accountingCurrency;

    public Optional<Fiat> getAccountingCurrency() {
        return accountingCurrency;
    }

    public void setAccountingCurrency(Optional<Fiat> accountingCurrency) {
        this.accountingCurrency = accountingCurrency;
    }

    private Optional<Country> providerCountry;

    public Optional<Country> getProviderCountry() {
        return providerCountry;
    }

    public void setProviderCountry(Optional<Country> providerCountry) {
        this.providerCountry = providerCountry;
    }

    private Boolean active;

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    private Boolean enabled;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
