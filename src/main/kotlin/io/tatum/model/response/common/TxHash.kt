package io.tatum.model.response.common

/**
 * @property id ID of withdrawal. If transaction is not valid in blockchain, use this id to cancel withdrawal.
 * @property txId TX hash of successful transaction.
 * @property completed Whethet withdrawal was completed in Tatum's internal ledger. If not, it must be done manually.
 */
class TxHash(
    val id: String,
    val txId: String,
    val completed: Boolean,
)
