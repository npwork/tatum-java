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
public class VetTxReceiptMeta {
    /**
     *
     * @type {string}
     * @memberof VetTxReceiptMeta
     */
    private String blockID;

    /**
     *
     * @type {number}
     * @memberof VetTxReceiptMeta
     */
    private BigDecimal blockNumber;

    /**
     *
     * @type {number}
     * @memberof VetTxReceiptMeta
     */
    private BigDecimal blockTimestamp;

    /**
     *
     * @type {string}
     * @memberof VetTxReceiptMeta
     */
    private String txID;

    /**
     *
     * @type {string}
     * @memberof VetTxReceiptMeta
     */
    private String txOrigin;

}
