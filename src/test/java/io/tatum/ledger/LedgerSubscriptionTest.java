package io.tatum.ledger;

import io.tatum.model.request.CreateSubscription;
import io.tatum.model.response.ledger.Account;
import io.tatum.model.response.ledger.Subscription;
import io.tatum.model.response.ledger.SubscriptionType;
import io.tatum.model.response.ledger.Transaction;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class LedgerSubscriptionTest {

    @Test
    public void createNewSubscriptionTest() {
//        CreateSubscription createSubscription = new CreateSubscription();
//        createSubscription.setType(SubscriptionType.ACCOUNT_BALANCE_LIMIT);
//        createSubscription.setAttr();
//        String id = new LedgerSubscription().createNewSubscription();
    }

    @Test
    public void listActiveSubscriptionsTest() throws InterruptedException, ExecutionException, IOException {

        Subscription[] subscriptions = new LedgerSubscription().listActiveSubscriptions(null, null);
        System.out.println(subscriptions.length);
    }

    @Test
    public void cancelExistingSubscriptionTest() throws InterruptedException, ExecutionException, IOException {
        new LedgerSubscription().cancelExistingSubscription("5e68c66581f2ee32bc354087");
    }

    @Test
    public void obtainReportForSubscriptionTest() throws InterruptedException, ExecutionException, IOException {
        Object[] objects = new LedgerSubscription().obtainReportForSubscription("5e68c66581f2ee32bc354087");
        if (objects instanceof Transaction[]) {
            Transaction[] transactions = (Transaction[]) objects;
            System.out.println(transactions.length);
        }

        if (objects instanceof Account[]) {
            Account[] accounts = (Account[]) objects;
            System.out.println(accounts.length);
        }
    }
}
