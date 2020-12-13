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
public class VetEstimateGas {

    /**
     * Sender
     * @type {string}
     * @memberof VetEstimateGas
     */
    private String from;

    /**
     * Recipient
     * @type {string}
     * @memberof VetEstimateGas
     */
    private String to;

    /**
     * Amount to send
     * @type {string}
     * @memberof VetEstimateGas
     */
    private String value;

    /**
     * Data to send to Smart Contract
     * @type {string}
     * @memberof VetEstimateGas
     */
    private String data;

    /**
     * Nonce
     * @type {number}
     * @memberof VetEstimateGas
     */
    private BigDecimal nonce;

}
