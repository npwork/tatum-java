package io.tatum.model.response.eth

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.math.BigInteger

/**
 * EthTxLog
 *
 * @param address From which this event originated from.
 * @param topic An array with max 4 32 Byte topics, topic 1-3 contains indexed parameters of the log.
 * @param data The data containing non-indexed log parameter.
 * @param logIndex Integer of the event index position in the block.
 * @param transactionIndex Integer of the transaction’s index position, the event was created in.
 * @param transactionHash Hash of the transaction this event was created in.
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class EthTxLog(
    val id: String? = null,
    val blockHash: String? = null,
    val blockNumber: BigInteger? = null,
    val address: String? = null,
    val topics: List<String>? = null,
    val data: String? = null,
    val logIndex: BigInteger? = null,
    val transactionIndex: BigInteger? = null,
    val transactionHash: String? = null,
)
