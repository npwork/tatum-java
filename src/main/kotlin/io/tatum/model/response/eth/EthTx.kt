package io.tatum.model.response.eth

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.math.BigInteger

/**
 *
 * EthTx
 *
 * @param blockHash Hash of the block where this transaction was in.
 * @param status TRUE if the transaction was successful, FALSE, if the EVM reverted the transaction.
 * @param blockNumber Block number where this transaction was in.
 * @param from Address of the sender.
 * @param gas Gas provided by the sender.
 * @param gasPrice Gas price provided by the sender in wei.
 * @param transactionHash Hash of the transaction.
 * @param input The data sent along with the transaction.
 * @param nonce The number of transactions made by the sender prior to this one.
 * @param to Address of the receiver. 'null' when its a contract creation transaction.
 * @param transactionIndex Integer of the transactions index position in the block.
 * @param value Value transferred in wei.
 * @param gasUsed The amount of gas used by this specific transaction alone.
 * @param cumulativeGasUsed The total amount of gas used when this transaction was executed in the block.
 * @param contractAddress The contract address created, if the transaction was a contract creation, otherwise null.
 * @param logs Log events, that happened in this transaction.
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class EthTx(
    val blockHash: String? = null,
    val status: Boolean? = null,
    val blockNumber: BigInteger? = null,
    val from: String? = null,
    val gas: BigInteger? = null,
    val gasPrice: String? = null,
    val transactionHash: String? = null,
    val input: String? = null,
    val nonce: BigInteger? = null,
    val to: String? = null,
    val transactionIndex: BigInteger? = null,
    val value: String? = null,
    val gasUsed: BigInteger? = null,
    val cumulativeGasUsed: BigInteger? = null,
    val contractAddress: String? = null,
    val logs: List<EthTxLog>? = null,
)
