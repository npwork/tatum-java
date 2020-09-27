package ledger

import getArrayFrom
import getObjectFrom
import model.request.CustomerUpdate
import model.response.ledger.Customer
import putObjectTo
import putTo

/**
 * For more details, see <a href="https://tatum.io/apidoc.html#operation/getCustomerByExternalId" target="_blank">Tatum API documentation</a>
 */
fun getCustomer(id: String): Customer =
    getObjectFrom("/ledger/customer/$id")

/**
 * For more details, see <a href="https://tatum.io/apidoc.html#operation/findAllCustomers" target="_blank">Tatum API documentation</a>
 */
fun getAllCustomers(pageSize: Long = 50, offset: Long = 0): Array<Customer> =
    getArrayFrom(
        endpoint = "/ledger/customer",
        params = mapOf("pageSize" to pageSize.toString(), "offset" to offset.toString()),
    )

/**
 * For more details, see <a href="https://tatum.io/apidoc.html#operation/updateCustomer" target="_blank">Tatum API documentation</a>
 */
fun updateCustomer(id: String, data: CustomerUpdate): String {
    val updatedCustomer: Customer = putObjectTo(endpoint = "/ledger/customer/$id", payload = data)
    return updatedCustomer.id
}

/**
 * For more details, see <a href="https://tatum.io/apidoc.html#operation/activateAccount" target="_blank">Tatum API documentation</a>
 */
fun activateCustomer(id: String): Unit =
    putTo("/ledger/customer/$id/activate")

/**
 * For more details, see <a href="https://tatum.io/apidoc.html#operation/deactivateCustomer" target="_blank">Tatum API documentation</a>
 */
fun deactivateCustomer(id: String): Unit =
    putTo("/ledger/customer/$id/deactivate")

/**
 * For more details, see <a href="https://tatum.io/apidoc.html#operation/enableCustomer" target="_blank">Tatum API documentation</a>
 */
fun enableCustomer(id: String): Unit =
    putTo("/ledger/customer/$id/enable")

/**
 * For more details, see <a href="https://tatum.io/apidoc.html#operation/disableCustomer" target="_blank">Tatum API documentation</a>
 */
fun disableCustomer(id: String): Unit =
    putTo("/ledger/customer/$id/disable")
