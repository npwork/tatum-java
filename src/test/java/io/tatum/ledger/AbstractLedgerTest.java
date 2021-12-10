package io.tatum.ledger;

import com.github.javafaker.Faker;
import io.tatum.model.request.Country;
import io.tatum.model.request.CreateAccount;
import io.tatum.model.request.Currency;
import io.tatum.model.request.CustomerUpdate;
import io.tatum.model.response.ledger.Account;
import io.tatum.model.response.ledger.Fiat;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public abstract class AbstractLedgerTest {
    public final static String XPUB = "xpub6DYE4EtQyRirEhSujw8fCtpAHK4jxNepFiVWBy85x5Nes6vC2b2uS2dgMo22pULAHt7k79DDJnAa3DhXQnKEvc8j1s45cMC42Co7pXpnVQd";
    public final static String XPUB_WRONG = "xpub6DYE4EtQyRirEhSujw8fCtpAHK4jxNepFiVWBy85x5Nes6vC2b2uS2dgMo22pULAHt7k79DDJnAa3DhXQnKEvc8j1s45cMC42Co7pXpnVQdxpub6DYE4EtQyRirEhSujw8fCtpAHK4jxNepFiVWBy85x5Nes6vC2b2uS2dgMo22pULAHt7k79DDJnAa3";

    protected final LedgerAccount LEDGER_ACCOUNT = new LedgerAccount();
    protected final Faker faker = new Faker();

    protected Account createAccount(Currency currency) throws IOException, ExecutionException, InterruptedException {
        return createAccount(currency, new CustomerUpdate(Country.SZ, Fiat.USD, Country.SZ, faker.idNumber().valid()));
    }

    protected Account createAccount(Currency currency, CustomerUpdate customer) throws IOException, ExecutionException, InterruptedException {
        CreateAccount createAccount = CreateAccount.builder()
                .currency(currency.currency)
                .customer(customer)
                .xpub(XPUB)
                .accountCode(faker.idNumber().ssnValid())
                .build();

        return LEDGER_ACCOUNT.createAccount(createAccount);
    }

    protected Account createAccountWithCurrency(Currency currency) throws IOException, ExecutionException, InterruptedException {
        return LEDGER_ACCOUNT.createAccount(CreateAccount.builder().currency(currency.currency).build());
    }

    protected Account createAccountWithCustomer(Currency currency) throws IOException, ExecutionException, InterruptedException {
        return createAccountWithCustomer(currency, faker.idNumber().valid());
    }

    protected Account createAccountWithCustomer(Currency currency, String externalId) throws IOException, ExecutionException, InterruptedException {
        return LEDGER_ACCOUNT.createAccount(CreateAccount.builder().currency(currency.currency).customer(CustomerUpdate.builder().externalId(externalId).customerCountry(Country.SZ).build()).build());
    }

    protected Account createAccountWithXPub(Currency currency, String xpub) throws IOException, ExecutionException, InterruptedException {
        return LEDGER_ACCOUNT.createAccount(CreateAccount.builder().currency(currency.currency).customer(CustomerUpdate.builder().externalId(faker.idNumber().valid()).customerCountry(Country.SZ).build()).xpub(xpub).build());
    }
}
