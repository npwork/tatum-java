package io.tatum.offchain

import io.tatum.Currency
import io.tatum.deleteFrom
import io.tatum.getArrayFrom
import io.tatum.getObjectFrom
import io.tatum.model.request.BroadcastWithdrawal
import io.tatum.model.request.Withdrawal
import io.tatum.model.response.common.TxHash
import io.tatum.model.response.ledger.Account
import io.tatum.model.response.offchain.Address
import io.tatum.model.response.offchain.WithdrawalResponse
import io.tatum.postObjectTo
import io.tatum.putTo
import java.math.BigInteger

/**
 * For more details
 * @see <a href="https://tatum.io/apidoc.html#operation/generateDepositAddress">Tatum API documentation</a>
 */
fun generateDepositAddress(accountId: String, index: BigInteger? = null): Address =
    postObjectTo(
        endpoint = "/offchain/account/$accountId/address",
        params = index?.let { mapOf("index" to it.toString()) }.orEmpty(),
    )

/**
 * For more details
 * @see <a href="https://tatum.io/apidoc.html#operation/addressExists">Tatum API documentation</a>
 */
fun checkAddressExists(address: org.kethereum.model.Address, currency: Currency, index: BigInteger? = null): Account =
    getObjectFrom(
        endpoint = "/offchain/account/address/${address.hex}/${currency}",
        params = index?.let { mapOf("index" to it.toString()) }.orEmpty(),
    )

/**
 * For more details
 * @see <a href="https://tatum.io/apidoc.html#operation/assignAddress">Tatum API documentation</a>
 */
fun assignDepositAddress(accountId: String, address: org.kethereum.model.Address): Address =
    postObjectTo(
        endpoint = "/offchain/account/${accountId}/address/${address.hex}",
    )

/**
 * For more details
 * @see <a href="https://tatum.io/apidoc.html#operation/assignAddress">Tatum API documentation</a>
 */
fun removeDepositAddress(accountId: String, address: org.kethereum.model.Address) =
    deleteFrom("/offchain/account/$accountId/address/${address.hex}")

/**
 * For more details
 * @see <a href="https://tatum.io/apidoc.html#operation/getAllDepositAddresses">Tatum API documentation</a>
 */
fun getDepositAddressesForAccount(accountId: String): Array<Address> =
    getArrayFrom("/offchain/account/$accountId/address")

/**
 * For more details
 * @see <a href="https://tatum.io/apidoc.html#operation/broadcastBlockchainTransaction">Tatum API documentation</a>
 */
fun offchainBroadcast(data: BroadcastWithdrawal): TxHash =
    postObjectTo(
        endpoint = "/offchain/withdrawal/broadcast",
        payload = data,
    )

/**
 * For more details
 * @see <a href="https://tatum.io/apidoc.html#operation/storeWithdrawal">Tatum API documentation</a>
 */
fun offchainStoreWithdrawal(data: Withdrawal): WithdrawalResponse =
    postObjectTo(
        endpoint = "/offchain/withdrawal",
        payload = data
    )

/**
 * For more details
 * @see <a href="https://tatum.io/apidoc.html#operation/cancelInProgressWithdrawal">Tatum API documentation</a>
 */
fun offchainCancelWithdrawal(id: String, revert: Boolean = true) =
    deleteFrom(
        endpoint = "/offchain/withdrawal/$id",
        params = mapOf("revert" to revert.toString())
    )

/**
 * For more details
 * @see <a href="https://tatum.io/apidoc.html#operation/completeWithdrawal">Tatum API documentation</a>
 */
fun offchainCompleteWithdrawal(id: String, txId: String) =
    putTo("/offchain/withdrawal/$id/$txId")
