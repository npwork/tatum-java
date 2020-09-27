package ledger

import deleteFrom
import getArrayFrom
import getObjectFrom
import model.request.BlockAmount
import model.request.CreateAccount
import model.response.ledger.Account
import model.response.ledger.AccountBalance
import model.response.ledger.Blockage
import model.response.ledger.IdString
import postObjectTo
import putTo

/**
 * For more details see link
 * @see <a href="https://tatum.io/apidoc.html#operation/getAccountByAccountId">Tatum API documentation</a>
 */
fun getAccountById(accountId: String): Account = getObjectFrom("/ledger/account/$accountId")

/**
 * For more details see link
 * @see <a href="https://tatum.io/apidoc.html#operation/createAccount">Tatum API documentation</a>
 */
fun createAccount(account: CreateAccount): Account = postObjectTo(
    endpoint = "/ledger/account",
    payload = account,
)

/**
 * For more details see link
 * @see <a href="https://tatum.io/apidoc.html#operation/getBlockAmount">Tatum API documentation</a>
 */
fun getBlockedAmountsByAccountId(accountId: String, pageSize: Long = 50, offset: Long = 0): Array<Blockage> =
    getArrayFrom(
        endpoint = "/ledger/account/block/$accountId",
        params = mapOf("pageSize" to pageSize.toString(), "offset" to offset.toString()),
    )

/**
 * For more details see link
 * @see <a href="https://tatum.io/apidoc.html#operation/blockAmount">Tatum API documentation</a>
 */
fun blockAmount(accountId: String, block: BlockAmount): IdString =
    postObjectTo(
        endpoint = "/ledger/account/block/$accountId",
        payload = block
    )

/**
 * For more details see link
 * @see <a href="https://tatum.io/apidoc.html#operation/deleteBlockAmount">Tatum API documentation</a>
 */
fun deleteBlockedAmount(blockageId: String) = deleteFrom("/ledger/account/block/$blockageId")

/**
 * For more details see link
 * @see <a href="https://tatum.io/apidoc.html#operation/deleteAllBlockAmount">Tatum API documentation</a>
 */
fun deleteBlockedAmountForAccount(accountId: String) = deleteFrom("/ledger/account/block/account/$accountId")

/**
 * For more details see link
 * @see <a href="https://tatum.io/apidoc.html#operation/activateAccount">Tatum API documentation</a>
 */
fun activateAccount(accountId: String) = putTo("/ledger/account/$accountId/activate")

/**
 * For more details see link
 * @see <a href="https://tatum.io/apidoc.html#operation/deactivateAccount">Tatum API documentation</a>
 */
fun deactivateAccount(accountId: String) = putTo("/ledger/account/$accountId/deactivate")

/**
 * For more details see link
 * @see <a href="https://tatum.io/apidoc.html#operation/freezeAccount">Tatum API documentation</a>
 */
fun freezeAccount(accountId: String) = putTo("/ledger/account/$accountId/freeze")

/**
 * For more details see link
 * @see <a href="https://tatum.io/apidoc.html#operation/unfreezeAccount">Tatum API documentation</a>
 */
fun unfreezeAccount(accountId: String) = putTo("/ledger/account/$accountId/unfreeze")

/**
 * For more details see link
 * @see <a href="https://tatum.io/apidoc.html#operation/getAccountsByCustomerId">Tatum API documentation</a>
 */
fun getAccountsByCustomerId(customerId: String, pageSize: Long = 50, offset: Long = 0): Array<Account> =
    getArrayFrom(
        endpoint = "/ledger/account/customer/$customerId",
        params = mapOf("pageSize" to pageSize.toString(), "offset" to offset.toString()),
    )

/**
 * For more details see link
 * @see <a href="https://tatum.io/apidoc.html#operation/getAllAccounts">Tatum API documentation</a>
 */
fun getAllAccounts(pageSize: Long = 50, offset: Long = 0): Array<Account> =
    getArrayFrom(
        endpoint = "/ledger/account",
        params = mapOf("pageSize" to pageSize.toString(), "offset" to offset.toString()),
    )

/**
 * For more details see link
 * @see <a href="https://tatum.io/apidoc.html#operation/getAccountBalance">Tatum API documentation</a>
 */
fun getAccountBalance(accountId: String): AccountBalance = getObjectFrom("/ledger/account/$accountId/balance")
