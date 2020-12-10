package io.tatum.model.request;

import io.tatum.model.response.ledger.Fiat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Optional;

public class CreateAccount {

    @NotEmpty
    @Size(min = 2, max = 40)
    private String currency;

    @Size(max = 192)
    private Optional<String> xpub;

    private Optional<Boolean> compliant;

    @Size(min = 3, max = 3)
    private Optional<Fiat> accountingCurrency;

    @Size(min = 1, max = 50)
    private Optional<String> accountCode;

    @Size(min = 1, max = 20)
    private Optional<String> accountNumber;

    private Optional<CustomerUpdate> customer;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Optional<String> getXpub() {
        return xpub;
    }

    public void setXpub(Optional<String> xpub) {
        this.xpub = xpub;
    }

    public Optional<Boolean> getCompliant() {
        return compliant;
    }

    public void setCompliant(Optional<Boolean> compliant) {
        this.compliant = compliant;
    }

    public Optional<Fiat> getAccountingCurrency() {
        return accountingCurrency;
    }

    public void setAccountingCurrency(Optional<Fiat> accountingCurrency) {
        this.accountingCurrency = accountingCurrency;
    }

    public Optional<String> getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(Optional<String> accountCode) {
        this.accountCode = accountCode;
    }

    public Optional<String> getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Optional<String> accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Optional<CustomerUpdate> getCustomer() {
        return customer;
    }

    public void setCustomer(Optional<CustomerUpdate> customer) {
        this.customer = customer;
    }
}
