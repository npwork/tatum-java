package io.tatum.model.response.ledger;

public enum TransactionType {
    FAILED("FAILED"),
    DEBIT_PAYMENT("DEBIT_PAYMENT"),
    CREDIT_PAYMENT("CREDIT_PAYMENT"),
    CREDIT_DEPOSIT("CREDIT_DEPOSIT"),
    DEBIT_WITHDRAWAL("DEBIT_WITHDRAWAL"),
    CANCEL_WITHDRAWAL("CANCEL_WITHDRAWAL"),
    DEBIT_OUTGOING_PAYMENT("DEBIT_OUTGOING_PAYMENT"),
    CREDIT_INCOMING_PAYMENT("CREDIT_INCOMING_PAYMENT"),
    EXCHANGE_BUY("EXCHANGE_BUY"),
    EXCHANGE_SELL("EXCHANGE_SELL");

    private final String transactionType;

    TransactionType(final String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionType() {
        return this.transactionType;
    }
}