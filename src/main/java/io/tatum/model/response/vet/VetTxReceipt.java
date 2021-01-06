package io.tatum.model.response.vet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * The type Vet tx receipt.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class VetTxReceipt {

    /**
     *
     * @type {number}
     * @memberof VetTxReceipt
     */
    private BigDecimal gasUsed;

    /**
     *
     * @type {string}
     * @memberof VetTxReceipt
     */
    private String gasPayer;

    /**
     *
     * @type {string}
     * @memberof VetTxReceipt
     */
    private String paid;

    /**
     *
     * @type {string}
     * @memberof VetTxReceipt
     */
    private String reward;

    /**
     *
     * @type {boolean}
     * @memberof VetTxReceipt
     */
    private Boolean reverted;

    /**
     *
     * @type {VetTxReceiptMeta}
     * @memberof VetTxReceipt
     */
    private VetTxReceiptMeta meta;

    /**
     *
     * @type {Array<VetTxReceiptOutputs>}
     * @memberof VetTxReceipt
     */
    private VetTxReceiptOutputs[] outputs;

    /**
     *
     * @type {number}
     * @memberof VetTxReceipt
     */
    private BigDecimal blockNumber;

    /**
     *
     * @type {string}
     * @memberof VetTxReceipt
     */
    private String blockHash;

    /**
     *
     * @type {string}
     * @memberof VetTxReceipt
     */
    private String transactionHash;

    /**
     *
     * @type {string}
     * @memberof VetTxReceipt
     */
    private String status;

}
