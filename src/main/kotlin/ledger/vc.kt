package ledger

import getObjectFrom
import model.request.CreateCurrency
import model.request.CurrencyOperation
import model.request.UpdateCurrency
import model.response.ledger.Account
import model.response.ledger.Reference
import model.response.ledger.VC
import postObjectTo
import putObjectTo

/**
 * For more details, see <a href="https://tatum.io/apidoc.html#operation/getCurrency" target="_blank">Tatum API documentation</a>
 */
fun getVirtualCurrencyByName(name: String): VC =
    getObjectFrom("/ledger/virtualCurrency/$name")

/**
 * For more details, see <a href="https://tatum.io/apidoc.html#operation/createCurrency" target="_blank">Tatum API documentation</a>
 */
fun createVirtualCurrency(data: CreateCurrency): Account =
    postObjectTo(
        endpoint = "/ledger/virtualCurrency",
        payload = data,
    )

/**
 * For more details, see <a href="https://tatum.io/apidoc.html#operation/updateCurrency" target="_blank">Tatum API documentation</a>
 */
fun updateVirtualCurrency(data: UpdateCurrency): Unit =
    putObjectTo(
        endpoint = "/ledger/virtualCurrency",
        payload = data,
    )

/**
 * For more details, see <a href="https://tatum.io/apidoc.html#operation/mintCurrency" target="_blank">Tatum API documentation</a>
 */
fun mintVirtualCurrency(data: CurrencyOperation): String {
    val referenceWrapper: Reference = putObjectTo(
        endpoint = "/ledger/virtualCurrency/mint",
        payload = data,
    )
    return referenceWrapper.reference
}

/**
 * For more details, see <a href="https://tatum.io/apidoc.html#operation/revokeCurrency" target="_blank">Tatum API documentation</a>
 */
fun revokeVirtualCurrency(data: CurrencyOperation): String {
    val referenceWrapper: Reference = putObjectTo(
        endpoint = "/ledger/virtualCurrency/revoke",
        payload = data,
    )
    return referenceWrapper.reference
}
