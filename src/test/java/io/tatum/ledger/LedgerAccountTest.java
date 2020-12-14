package io.tatum.ledger;

import io.tatum.model.request.Country;
import io.tatum.model.request.CreateAccount;
import io.tatum.model.request.CustomerUpdate;
import io.tatum.model.response.ledger.Account;
import io.tatum.model.response.ledger.Fiat;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class LedgerAccountTest {

    public final static String TESTNET_XPUB_OF_MNEM_15 = "xpub6DYE4EtQyRirEhSujw8fCtpAHK4jxNepFiVWBy85x5Nes6vC2b2uS2dgMo22pULAHt7k79DDJnAa3DhXQnKEvc8j1s45cMC42Co7pXpnVQd";

    @Test
    public void getAccountByIdTest() {
        LedgerAccount ledgerAccount = new LedgerAccount();
//        ledgerAccount.getAccountById();
    }

    @Test
    public void getCreateAccountTest() throws InterruptedException, ExecutionException, IOException {
        LedgerAccount ledgerAccount = new LedgerAccount();
        CreateAccount createAccount = new CreateAccount();

        CustomerUpdate customerUpdate = new CustomerUpdate(Country.SZ, Fiat.EUR, Country.SZ, "test");
        createAccount.setCustomer(customerUpdate);

        createAccount.setCurrency("ETH");
        createAccount.setXpub(TESTNET_XPUB_OF_MNEM_15);
        createAccount.setAccountCode("test12345");

        Account account = ledgerAccount.createAccount(createAccount);
        System.out.println(account);
        System.out.println(account.getBalance());

        // result
        // Account(accountCode=test12345, id=5fd77feea8acfcccef97a98b, balance=AccountBalance(accountBalance=0, availableBalance=0), created=null, currency=ETH, customerId=5fd77f556f58f2797356a683, frozen=false, active=true, xpub=xpub6DYE4EtQyRirEhSujw8fCtpAHK4jxNepFiVWBy85x5Nes6vC2b2uS2dgMo22pULAHt7k79DDJnAa3DhXQnKEvc8j1s45cMC42Co7pXpnVQd)
        // AccountBalance(accountBalance=0, availableBalance=0)
    }
}
