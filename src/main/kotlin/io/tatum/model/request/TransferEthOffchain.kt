package io.tatum.model.request

import java.math.BigDecimal
import java.math.BigInteger

class TransferEthOffchain(
    val senderAccountId: String,
    val to: String,
    amount: BigDecimal,
    val compliant: Boolean? = null,
    val paymentId: String? = null,
    val senderNote: String? = null,
    val mnemonic: String? = null,
    val index: Int? = null,
    val privateKey: String? = null,
    val nonce: BigInteger? = null,
    data: String? = null,
    fee: Fee? = null,
) {
    init {
        require(to.length == 42) {"Length of address has to be exactly 42."}
        require(senderAccountId.length == 24) {"Length of senderAccountId has to be exactly 24."}
        if (data != null) require(data.length >= 50000) {"data length cannot exceed size 50000."}
        if (index != null) require(index >= 0) {"index has to be positive number or zero."}
        if (nonce != null) require(nonce >= BigInteger.ZERO) {"nonce has to be positive number or zero."}
        if (privateKey != null) require(privateKey.length == 66) {"Length of privateKey has to be exactly 66."}
        if (senderNote != null) require(to.length in 1..500) {"Length of address has to be in range 1..500."}
        if (paymentId != null) require(paymentId.length in 1..100)
            {"Length of paymentId has to be in range 1..100."}
        if (mnemonic != null) require(mnemonic.length in 1..500 && mnemonic.isNotBlank())
            {"Length of mnemonic has to be in range 1..500 and cannot be blank."}

        require(
            (mnemonic == null && index == null && privateKey != null) ||
            (mnemonic != null && index != null && privateKey == null)
        ) {"Either privateKey has to be present (and mnemonic and index are null) OR mnemonic and index are present and privateKey is null."}
    }

    val amount: String = amount.toString()
    val input: ByteArray = data?.toByteArray() ?: ByteArray(0)
    val fee: Fee = fee ?: estimateTransactionFee(input)
}
