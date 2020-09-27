package model.response.eth

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.math.BigInteger

@JsonIgnoreProperties(ignoreUnknown = true)
class EthBlock(
    /**
     * Difficulty for this block.
     */
    val difficulty: BigInteger? = null,
    /**
     * The 'extra data' field of this block.
     */
    val extraData: String? = null,
    /**
     * The maximum gas allowed in this block.
     */
    val gasLimit: BigInteger? = null,
    /**
     * The total used gas by all transactions in this block.
     */
    val gasUsed: BigInteger? = null,
    /**
     * Hash of the block. 'null' when its pending block.
     */
    val hash: String? = null,
    /**
     * The bloom filter for the logs of the block. 'null' when its pending block.
     */
    val logsBloom: String? = null,
    /**
     * The address of the beneficiary to whom the mining rewards were given.
     */
    val miner: String? = null,
    val mixHash: String? = null,
    /**
     * Hash of the generated proof-of-work. 'null' when its pending block.
     */
    val nonce: String? = null,
    /**
     * The block number. 'null' when its pending block.
     */
    val number: BigInteger? = null,
    /**
     * Hash of the parent block.
     */
    val parentHash: String? = null,
    val receiptsRoot: String? = null,
    /**
     * SHA3 of the uncles data in the block.
     */
    val sha3Uncles: String? = null,
    /**
     * The size of this block in bytes.
     */
    val size: BigInteger? = null,
    /**
     * The root of the final state trie of the block.
     */
    val stateRoot: String? = null,
    /**
     * The unix timestamp for when the block was collated.
     */
    val timestamp: BigInteger? = null,
    /**
     * Total difficulty of the chain until this block.
     */
    val totalDifficulty: String? = null,
    /**
     * Array of transactions.
     */
    val transactions: List<EthTx>? = null,
    /**
     * The root of the transaction trie of the block.
     */
    val transactionsRoot: String? = null,
)