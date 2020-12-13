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
public class VetTxMeta {

    /**
     *
     * @type {string}
     * @memberof VetTxMeta
     */
    private String blockID;

    /**
     *
     * @type {number}
     * @memberof VetTxMeta
     */
    private BigDecimal blockNumber;

    /**
     *
     * @type {number}
     * @memberof VetTxMeta
     */
    private BigDecimal blockTimestamp;

}
