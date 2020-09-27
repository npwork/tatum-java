package blockchain

import com.fasterxml.jackson.annotation.JsonInclude
import getArrayFrom
import getNumberFrom
import getObjectFrom
import model.response.common.TransactionHash
import model.response.eth.EthBlock
import model.response.eth.EthTx
import org.kethereum.model.Address
import postObjectTo
import transaction.Contract
import java.math.BigDecimal
import java.math.BigInteger

@JsonInclude(JsonInclude.Include.NON_NULL)
class TransactionalPayload(val txData: String, val signatureId: String? = null)

class BalanceResponse(val balance: BigDecimal)

/**
 * For more details
 * @see <a href="https://tatum.io/apidoc.html#operation/EthBroadcast">Tatum API documentation</a>
 *
 * @param txData transactional data
 * @param signatureId
 */
fun ethBroadcast(payload: TransactionalPayload): TransactionHash =
    postObjectTo(
        endpoint = "/ethereum/broadcast",
        payload = payload,
    )

/**
 * For more details
 * @see <a href="https://tatum.io/apidoc.html#operation/EthGetTransactionCount">Tatum API documentation</a>
 */
fun ethGetTransactionsCount(address: Address): BigInteger =
    getNumberFrom("/ethereum/transaction/count/${address.hex}")

/**
 * For more details
 * @see <a href="https://tatum.io/apidoc.html#operation/EthGetCurrentBlock">Tatum API documentation</a>
 */
fun ethGetCurrentBlock(): BigInteger = getNumberFrom("/ethereum/block/current")

/**
 * For more details
 * @see <a href="https://tatum.io/apidoc.html#operation/EthGetBlock">Tatum API documentation</a>
 */
fun ethGetBlock(hash: String): EthBlock = getObjectFrom("/ethereum/block/$hash")

/**
 * For more details
 * @see <a href="https://tatum.io/apidoc.html#operation/EthGetBalance">Tatum API documentation</a>
 */
fun ethGetAccountBalance(address: Address): BalanceResponse =
    getObjectFrom("/ethereum/account/balance/${address.hex}")

/**
 * For more details
 * @see <a href="https://tatum.io/apidoc.html#operation/EthErc20GetBalance">Tatum API documentation</a>
 */
fun ethGetAccountErc20Balance(address: Address, contract: Contract): BalanceResponse =
    getObjectFrom("/ethereum/account/balance/erc20/${address.hex}?contractAddress=${contract.address}")

/**
 * For more details
 * @see <a href="https://tatum.io/apidoc.html#operation/EthGetTransaction">Tatum API documentation</a>
 */
fun ethGetTransaction(hash: TransactionHash): EthTx = getObjectFrom("/ethereum/transaction/${hash.txId}")

/**
 * For more details
 * @see <a href="https://tatum.io/apidoc.html#operation/EthGetTransactionByAddress">Tatum API documentation</a>
 */
fun ethGetAccountTransactions(address: Address, pageSize: Long = 50, offset: Long = 0): Array<EthTx> =
    getArrayFrom(
        endpoint = "/ethereum/account/transaction/${address.hex}",
        params = mapOf("pageSize" to pageSize.toString(), "offset" to offset.toString()),
    )
