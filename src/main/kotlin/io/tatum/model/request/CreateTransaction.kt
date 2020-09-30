package io.tatum.model.request

import java.math.BigDecimal

class CreateTransaction(
    val senderAccountId: String,
    val recipientAccountId: String,
    amount: BigDecimal,
    val paymentId: String? = null,
    val transactionCode: String? = null,
    val senderNote: String? = null,
    val recipientNote: String? = null,
    val baseRate: BigDecimal? = null,
    val anonymous: Boolean?,
    val compliant: Boolean?,
) {
    init {
        require(senderAccountId.length == 24)
        require(recipientAccountId.length == 24)
        require(amount.toString().length <= 38)
        if (paymentId != null) require(paymentId.length in 1..100)
        if (transactionCode != null) require(transactionCode.length in 1..100)
        if (senderNote != null) require(senderNote.length in 1..100)
        if (recipientNote != null) require(recipientNote.length in 1..100)
        if (baseRate != null) require(baseRate >= BigDecimal.ZERO)
    }
    val amount: String = amount.toString()
}
