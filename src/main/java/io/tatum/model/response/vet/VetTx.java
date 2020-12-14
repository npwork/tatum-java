package io.tatum.model.response.vet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class VetTx {

    /**
     *
     * @type {string}
     * @memberof VetTx
     */
    private String id;

    /**
     *
     * @type {string}
     * @memberof VetTx
     */
    private String chainTag;

    /**
     *
     * @type {string}
     * @memberof VetTx
     */
    private String blockRef;

    /**
     *
     * @type {number}
     * @memberof VetTx
     */
    private BigDecimal expiration;

    /**
     *
     * @type {Array<VetTxClauses>}
     * @memberof VetTx
     */
    private VetTxClauses[] clauses;

    /**
     *
     * @type {number}
     * @memberof VetTx
     */
    private BigDecimal gasPriceCoef;

    /**
     *
     * @type {number}
     * @memberof VetTx
     */
    private BigDecimal gas;

    /**
     *
     * @type {string}
     * @memberof VetTx
     */
    private String origin;

    /**
     *
     * @type {string}
     * @memberof VetTx
     */
    private String nonce;

    /**
     *
     * @type {number}
     * @memberof VetTx
     */
    private BigDecimal size;

    /**
     *
     * @type {VetTxMeta}
     * @memberof VetTx
     */
    private VetTxMeta meta;

    /**
     *
     * @type {number}
     * @memberof VetTx
     */
    private BigDecimal blockNumber;

}
