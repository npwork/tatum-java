package io.tatum.model.response.vet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class VetBlock {

    /**
     * block number (height)
     * @type {number}
     * @memberof VetBlock
     */
    private BigDecimal number;

    /**
     * block identifier
     * @type {string}
     * @memberof VetBlock
     */
    private String id;

    /**
     * RLP encoded block size in bytes
     * @type {number}
     * @memberof VetBlock
     */
    private BigDecimal size;

    /**
     * parent block ID
     * @type {string}
     * @memberof VetBlock
     */
    private String parentID;

    /**
     * block unix timestamp
     * @type {number}
     * @memberof VetBlock
     */
    private BigDecimal timestamp;

    /**
     * block gas limit (max allowed accumulative gas usage of transactions)
     * @type {number}
     * @memberof VetBlock
     */
    private BigDecimal gasLimit;

    /**
     * Address of account to receive block reward
     * @type {string}
     * @memberof VetBlock
     */
    private String beneficiary;

    /**
     * accumulative gas usage of transactions
     * @type {number}
     * @memberof VetBlock
     */
    private BigDecimal gasUsed;

    /**
     * sum of all ancestral blocks' score
     * @type {number}
     * @memberof VetBlock
     */
    private BigDecimal totalScore;

    /**
     * root hash of transactions in the block
     * @type {string}
     * @memberof VetBlock
     */
    private String txsRoot;

    /**
     * supported txs features bitset
     * @type {number}
     * @memberof VetBlock
     */
    private BigDecimal txsFeatures;

    /**
     * root hash of accounts state
     * @type {string}
     * @memberof VetBlock
     */
    private String stateRoot;

    /**
     * root hash of transaction receipts
     * @type {string}
     * @memberof VetBlock
     */
    private String receiptsRoot;

    /**
     * the one who signed this block
     * @type {string}
     * @memberof VetBlock
     */
    private String signer;

    /**
     * transactions IDs
     * @type {Array<string>}
     * @memberof VetBlock
     */
    private String[] transactions;

}
