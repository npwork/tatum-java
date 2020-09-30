package io.tatum.model.request

import java.math.BigDecimal
import java.math.BigInteger

/**
 * @property senderAccountId Sender account ID
 * @property address Blockchain address to send assets to.
 * @param amount Amount to be withdrawn to blockchain.
 * @property attr Used to parametrize withdrawal. Used for XRP withdrawal to define destination tag of recipient, or XLM memo of the recipient, if needed. For Bitcoin, Litecoin, Bitcoin Cash and account with manually assigned addresses and keyPair, used as a change address for left coins from transaction.
 * @property compliant Compliance check, if withdrawal is not compliant, it will not be processed.
 * @property fee Fee in eth to be submitted as a transaction fee to blockchain.
 * @property paymentId Identifier of the payment, shown for created Transaction within Tatum sender account.
 * @property senderBlockchainAddress Blockchain address to send assets from. Used only for ETH-based accounts. Used for information purposes only.
 * @property senderNote Note visible to owner of withdrawing account.
 */
class Withdrawal(
    val senderAccountId: String,
    val address: String,
    amount: BigDecimal,
    val attr: String? = null,
    val compliant: Boolean? = null,
    fee: BigInteger? = null,
    val paymentId: String? = null,
    val senderNote: String? = null,
) {
    init {
        require(senderAccountId.length == 24) {"Length of senderAccountId has to be exactly 24 characters."}
        require(address.length in 1..100) {"Length of address has to be in range of 1..100."}
        require(amount.toString().length <=38) {"amount has to have max 38 digits."}
        if (attr != null) require(attr.length in 1..64) {"Length of attr has to be in range of 1..64."}
        if (paymentId != null) require(paymentId.length in 1..100)
            {"Length of paymentId has to be in range of 1..100."}
        if (senderNote != null) require(senderNote.length in 1..500)
            {"Length of senderNote has to be in range of 1..500."}
    }

    val amount: String = amount.toString()
    val fee: String? = fee.toString()
}
