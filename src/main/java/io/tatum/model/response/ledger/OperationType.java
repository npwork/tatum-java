package io.tatum.model.response.ledger;

public enum OperationType {
    PAYMENT("PAYMENT"),
    WITHDRAWAL("WITHDRAWAL"),
    BLOCKCHAIN_TRANSACTION("BLOCKCHAIN_TRANSACTION"),
    EXCHANGE("EXCHANGE"),
    FAILED("FAILED"),
    DEPOSIT("DEPOSIT"),
    MINT("MINT"),
    REVOKE("REVOKE");

    private final String operationType;

    OperationType(final String operationType) {
        this.operationType = operationType;
    }

    public String getOperationType() {
        return operationType;
    }
}