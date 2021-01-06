package io.tatum.model.response.ledger;

/**
 * The enum Operation type.
 */
public enum OperationType {
    /**
     * Payment operation type.
     */
    PAYMENT("PAYMENT"),
    /**
     * Withdrawal operation type.
     */
    WITHDRAWAL("WITHDRAWAL"),
    /**
     * Blockchain transaction operation type.
     */
    BLOCKCHAIN_TRANSACTION("BLOCKCHAIN_TRANSACTION"),
    /**
     * Exchange operation type.
     */
    EXCHANGE("EXCHANGE"),
    /**
     * Failed operation type.
     */
    FAILED("FAILED"),
    /**
     * Deposit operation type.
     */
    DEPOSIT("DEPOSIT"),
    /**
     * Mint operation type.
     */
    MINT("MINT"),
    /**
     * Revoke operation type.
     */
    REVOKE("REVOKE");

    private final String operationType;

    OperationType(final String operationType) {
        this.operationType = operationType;
    }

    /**
     * Gets operation type.
     *
     * @return the operation type
     */
    public String getOperationType() {
        return operationType;
    }
}
