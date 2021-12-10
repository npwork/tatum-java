package io.tatum.model.response.ledger;

import lombok.AllArgsConstructor;

/**
 * The enum Subscription type.
 */
@AllArgsConstructor
public enum SubscriptionType {
    /**
     * Account balance limit subscription type.
     */
    ACCOUNT_BALANCE_LIMIT("ACCOUNT_BALANCE_LIMIT"),
    /**
     * Offchain withdrawal subscription type.
     */
    OFFCHAIN_WITHDRAWAL("OFFCHAIN_WITHDRAWAL"),
    /**
     * Transaction history report subscription type.
     */
    TRANSACTION_HISTORY_REPORT("TRANSACTION_HISTORY_REPORT"),
    /**
     * Account incoming blockchain transaction subscription type.
     */
    ACCOUNT_INCOMING_BLOCKCHAIN_TRANSACTION("ACCOUNT_INCOMING_BLOCKCHAIN_TRANSACTION"),
    /**
     * Complete blockchain transaction subscription type.
     */
    COMPLETE_BLOCKCHAIN_TRANSACTION("COMPLETE_BLOCKCHAIN_TRANSACTION");

    public final String subscriptionType;
}
