package ledger

import getObjectFrom
import model.request.CreateTransaction
import model.request.TransactionFilter
import model.response.ledger.Transaction
import postObjectTo

/**
 * For more details, see <a href="https://tatum.io/apidoc.html#operation/getTransactionsByReference" target="_blank">Tatum API documentation</a>
 */
fun getTransactionsByReference(reference: String): Array<Transaction> =
    getObjectFrom("/ledger/transaction/reference/$reference")

/**
 * For more details, see <a href="https://tatum.io/apidoc.html#operation/sendTransaction" target="_blank">Tatum API documentation</a>
 */
fun storeTransaction(transaction: CreateTransaction): String {
    val storedTransaction: Transaction = postObjectTo(
        endpoint = "/ledger/transaction",
        payload = transaction,
    )
    return storedTransaction.reference
}

/**
 * For more details, see <a href="https://tatum.io/apidoc.html#operation/getTransactionsByAccountId" target="_blank">Tatum API documentation</a>
 */
fun getTransactionsByAccount(filter: TransactionFilter, pageSize: Long = 50, offset: Long = 0): Array<Transaction> =
    postObjectTo(
        endpoint = "/ledger/transaction/account",
        params = mapOf("pageSize" to pageSize.toString(), "offset" to offset.toString()),
        payload = filter,
    )

/**
 * For more details, see <a href="https://tatum.io/apidoc.html#operation/getTransactionsByCustomerId" target="_blank">Tatum API documentation</a>
 */
fun getTransactionsByCustomer(filter: TransactionFilter, pageSize: Long = 50, offset: Long = 0): Array<Transaction> =
    postObjectTo(
        endpoint = "/ledger/transaction/customer",
        params = mapOf("pageSize" to pageSize.toString(), "offset" to offset.toString()),
        payload = filter,
    )

/**
 * For more details, see <a href="https://tatum.io/apidoc.html#operation/getTransactions" target="_blank">Tatum API documentation</a>
 */
fun getTransactionsByLedger(filter: TransactionFilter, pageSize: Long = 50, offset: Long = 0): Array<Transaction> =
    postObjectTo(
        endpoint = "/ledger/transaction/ledger",
        params = mapOf("pageSize" to pageSize.toString(), "offset" to offset.toString()),
        payload = filter,
    )

/**
 * For more details, see <a href="https://tatum.io/apidoc.html#operation/getTransactionsByAccountId" target="_blank">Tatum API documentation</a>
 */
fun countTransactionsByAccount(filter: TransactionFilter): Array<Transaction> =
    postObjectTo(
        endpoint = "/ledger/transaction/account",
        params = mapOf("count" to true.toString()),
        payload = filter,
    )

/**
 * For more details, see <a href="https://tatum.io/apidoc.html#operation/getTransactionsByCustomerId" target="_blank">Tatum API documentation</a>
 */
fun countTransactionsByCustomer(filter: TransactionFilter): Array<Transaction> =
    postObjectTo(
        endpoint = "/ledger/transaction/customer",
        params = mapOf("count" to true.toString()),
        payload = filter,
    )

/**
 * For more details, see <a href="https://tatum.io/apidoc.html#operation/getTransactions" target="_blank">Tatum API documentation</a>
 */
fun countTransactionsByLedger(filter: TransactionFilter): Array<Transaction> =
    postObjectTo(
        endpoint = "/ledger/transaction/ledger",
        params = mapOf("count" to true.toString()),
        payload = filter,
    )
