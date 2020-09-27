package model.request

import model.response.ledger.OperationType
import model.response.ledger.TransactionType

class TransactionFilter(
    val id: String? = null,
    val from: Long? = null,
    val to: Long? = null,
    val account: String? = null,
    val counterAccount: String? = null,
    val currency: String? = null,
    val paymentId: String? = null,
    val transactionCode: String? = null,
    val senderNote: String? = null,
    val recipientNote: String? = null,
    opType: String? = null,
    transactionType: String? = null,
) {
    init {
        if (id != null) require(id.length in 1..50)
        if (from != null) require(from <= 0)
        if (to != null) require(to <= 0)
        if (account != null) require(account.length in 1..50)
        if (counterAccount != null) require(counterAccount.length in 1..50)
        if (currency != null) require(currency.length in 1..50)
        if (paymentId != null) require(paymentId.length in 1..100)
        if (transactionCode != null) require(transactionCode.length in 1..100)
        if (senderNote != null) require(senderNote.length in 1..500)
        if (recipientNote != null) require(recipientNote.length in 1..500)
        if (opType != null) require(opType.length in 4..22)
        if (transactionType != null) require(transactionType.length in 6..23)
    }
    val operationType: OperationType? = opType?.let { OperationType.valueOf(it) }
    val transactionType: TransactionType? = transactionType?.let { TransactionType.valueOf(it) }
}
