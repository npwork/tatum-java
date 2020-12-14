package io.tatum.ledger;

import io.tatum.model.request.Country;
import io.tatum.model.request.CreateAccount;
import io.tatum.model.request.CustomerUpdate;
import io.tatum.model.response.ledger.Fiat;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class LedgerAccountTest {

    @Test
    public void getAccountByIdTest() {
        LedgerAccount ledgerAccount = new LedgerAccount();
//        ledgerAccount.getAccountById();
    }

    @Test
    public void getCreateAccountTest() throws InterruptedException, ExecutionException, IOException {
        LedgerAccount ledgerAccount = new LedgerAccount();
        CreateAccount createAccount = new CreateAccount();

        CustomerUpdate customerUpdate = new CustomerUpdate(Country.AD, Fiat.AED, Country.AE, "test");
        createAccount.setCustomer(customerUpdate);
        createAccount.setCurrency("ETH");
//        createAccount.setXpub();

        ledgerAccount.createAccount(createAccount);
    }
}
