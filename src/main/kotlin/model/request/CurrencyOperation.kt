package model.request

import java.math.BigDecimal

class CurrencyOperation(
    val accountId: String,
    amount: BigDecimal,
    val paymentId: String?,
    val transactionCode: String?,
    val senderNote: String?,
    val recipientNote: String?,
    val counterAccount: String?,
    val reference: String?,
) {
    init {
        require(accountId.length == 24)
        require(amount.toString().length <= 38)
        if (paymentId != null) require(paymentId.length in 1..100)
        if (transactionCode != null) require(transactionCode.length in 1..100)
        if (senderNote != null) require(senderNote.length in 1..500)
        if (recipientNote != null) require(recipientNote.length in 1..500)
        if (counterAccount != null) require(counterAccount.length == 24)
        if (reference != null) require(reference.length in 1..50)
    }
    val amount: String = amount.toString()
}
