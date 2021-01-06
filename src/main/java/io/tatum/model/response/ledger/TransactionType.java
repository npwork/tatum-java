package io.tatum.model.response.ledger;

/**
 * The enum Transaction type.
 */
public enum TransactionType {
    /**
     * Failed transaction type.
     */
    FAILED("FAILED"),
    /**
     * Debit payment transaction type.
     */
    DEBIT_PAYMENT("DEBIT_PAYMENT"),
    /**
     * Credit payment transaction type.
     */
    CREDIT_PAYMENT("CREDIT_PAYMENT"),
    /**
     * Credit deposit transaction type.
     */
    CREDIT_DEPOSIT("CREDIT_DEPOSIT"),
    /**
     * Debit withdrawal transaction type.
     */
    DEBIT_WITHDRAWAL("DEBIT_WITHDRAWAL"),
    /**
     * Cancel withdrawal transaction type.
     */
    CANCEL_WITHDRAWAL("CANCEL_WITHDRAWAL"),
    /**
     * Debit outgoing payment transaction type.
     */
    DEBIT_OUTGOING_PAYMENT("DEBIT_OUTGOING_PAYMENT"),
    /**
     * Credit incoming payment transaction type.
     */
    CREDIT_INCOMING_PAYMENT("CREDIT_INCOMING_PAYMENT"),
    /**
     * Exchange buy transaction type.
     */
    EXCHANGE_BUY("EXCHANGE_BUY"),
    /**
     * Exchange sell transaction type.
     */
    EXCHANGE_SELL("EXCHANGE_SELL");

    private final String transactionType;

    TransactionType(final String transactionType) {
        this.transactionType = transactionType;
    }

    /**
     * Gets transaction type.
     *
     * @return the transaction type
     */
    public String getTransactionType() {
        return this.transactionType;
    }
}
