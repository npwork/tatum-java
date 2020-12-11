package io.tatum.model.response.ledger;

public enum SubscriptionType {
    ACCOUNT_BALANCE_LIMIT("ACCOUNT_BALANCE_LIMIT"),
    OFFCHAIN_WITHDRAWAL("OFFCHAIN_WITHDRAWAL"),
    TRANSACTION_HISTORY_REPORT("TRANSACTION_HISTORY_REPORT"),
    ACCOUNT_INCOMING_BLOCKCHAIN_TRANSACTION("ACCOUNT_INCOMING_BLOCKCHAIN_TRANSACTION"),
    COMPLETE_BLOCKCHAIN_TRANSACTION("COMPLETE_BLOCKCHAIN_TRANSACTION");

    private final String subscriptionType;

    SubscriptionType(final String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }
}