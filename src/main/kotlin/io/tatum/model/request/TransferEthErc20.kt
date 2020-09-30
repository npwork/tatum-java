package io.tatum.model.request

import io.tatum.Currency
import org.kethereum.model.Address
import java.math.BigDecimal
import java.math.BigInteger

/**
 * @property fromPrivateKey of senders
 * @property to is a destination address
 * @property nonce is an atomic number of transaction from given address and must be present in case there are multiple unconfirmed transactions to the address
 * @property amount we want to send to a destination address
 * @property currency is one of crypto currencies supported by Tatum
 * @param data in string and will be converted to ByteArray at init
 * @param fee includes gas limit and gas price. If empty, fee is estimated at init
 */
class TransferEthErc20(
    val fromPrivateKey: String,
    val to: Address,
    val nonce: BigInteger, // nonce must be non-null for signViaEIP155 function
    val amount: BigDecimal,
    val currency: Currency,
    data: String? = null,
    fee: Fee? = null,
) {
    init {
        require(nonce >= BigInteger.ZERO) {"nonce cannot be negative number."}
        if (data != null) require(data.length <= 130000) {"data length cannot exceed size 130000."}
        require(fromPrivateKey.length == 66) {"fromPrivateKey has to have exactly 66 characters."}
        require(to.hex.length == 42) {"Length of address in hex form has to have exactly 42 characters."}
    }

    val input: ByteArray = data?.toByteArray() ?: ByteArray(0)
    val fee: Fee = fee ?: estimateTransactionFee(input)
}
