package model.response.ledger

enum class OperationType(val response: String) {
    PAYMENT("PAYMENT"),
    WITHDRAWAL("WITHDRAWAL"),
    BLOCKCHAINTRANSACTION("BLOCKCHAIN_TRANSACTION"),
    EXCHANGE("EXCHANGE"),
    FAILED("FAILED"),
    DEPOSIT("DEPOSIT"),
    MINT("MINT"),
    REVOKE("REVOKE"),
}
