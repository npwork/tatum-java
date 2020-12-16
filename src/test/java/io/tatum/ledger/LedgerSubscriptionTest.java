package io.tatum.ledger;

import io.tatum.model.request.CreateSubscription;
import io.tatum.model.request.SubscriptionAttrOffchainWithdrawal;
import io.tatum.model.response.common.Id;
import io.tatum.model.response.ledger.Account;
import io.tatum.model.response.ledger.Subscription;
import io.tatum.model.response.ledger.SubscriptionType;
import io.tatum.model.response.ledger.Transaction;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

public class LedgerSubscriptionTest {

    @Test
    public void createNewSubscriptionTypeFailedTest() throws InterruptedException, ExecutionException, IOException {
        CreateSubscription createSubscription = new CreateSubscription();
        createSubscription.setType(SubscriptionType.OFFCHAIN_WITHDRAWAL);
        createSubscription.setAttr(BigDecimal.ZERO);
        Id id = new LedgerSubscription().createNewSubscription(createSubscription);
    }

    @Test
    public void createNewSubscriptionNestedEmptyFailedTest() throws InterruptedException, ExecutionException, IOException {
        CreateSubscription createSubscription = new CreateSubscription();
        createSubscription.setType(SubscriptionType.OFFCHAIN_WITHDRAWAL);

        SubscriptionAttrOffchainWithdrawal withdrawal = new SubscriptionAttrOffchainWithdrawal();
        createSubscription.setAttr(withdrawal);
        Id id = new LedgerSubscription().createNewSubscription(createSubscription);
    }

    @Test
    public void createNewSubscriptionNestedFailedTest() throws InterruptedException, ExecutionException, IOException {
        CreateSubscription createSubscription = new CreateSubscription();
        createSubscription.setType(SubscriptionType.OFFCHAIN_WITHDRAWAL);
        SubscriptionAttrOffchainWithdrawal withdrawal = new SubscriptionAttrOffchainWithdrawal();
        withdrawal.setCurrency("$QA");
        createSubscription.setAttr(withdrawal);
        Id id = new LedgerSubscription().createNewSubscription(createSubscription);
    }

    @Test
    public void createNewSubscriptionTest() throws InterruptedException, ExecutionException, IOException {
        CreateSubscription createSubscription = new CreateSubscription();
        createSubscription.setType(SubscriptionType.OFFCHAIN_WITHDRAWAL);
        SubscriptionAttrOffchainWithdrawal withdrawal = new SubscriptionAttrOffchainWithdrawal();
        withdrawal.setCurrency("BTC");
        createSubscription.setAttr(withdrawal);
        Id id = new LedgerSubscription().createNewSubscription(createSubscription);
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
