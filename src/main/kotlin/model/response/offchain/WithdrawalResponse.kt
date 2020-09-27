package model.response.offchain

import java.math.BigDecimal
import java.math.BigInteger

/**
 * @property id ID of withdrawal
 * @property data
 * @property reference Transaction reference of the transaction connected to this withdrawal.
 */
class WithdrawalResponse(
    val reference: String,
    val data: Array<WithdrawalResponseData>,
    val id: String,
)

/**
 *
 * @property address
 * @property amount Amount of unprocessed transaction outputs, that can be used for withdrawal. Bitcoin, Litecoin, Bitcoin Cash only.
 * @property vIn Last used unprocessed transaction output, that can be used.
 * @property vInIndex Index of last used unprocessed transaction output in raw transaction, that can be used. Bitcoin, Litecoin, Bitcoin Cash only.
 * @property scriptPubKey Script of last unprocessed UTXO. Bitcoin SV only.
 */
class WithdrawalResponseData(
    val address: Address,
    val amount: BigDecimal,
    val vIn: String,
    val vInIndex: BigInteger,
    val scriptPubKey: String,
)
