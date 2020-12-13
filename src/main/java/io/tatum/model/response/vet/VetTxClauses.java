package io.tatum.model.response.vet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class VetTxClauses {

    /**
     *
     * @type {string}
     * @memberof VetTxClauses
     */
    private String to;

    /**
     *
     * @type {string}
     * @memberof VetTxClauses
     */
    private String value;

    /**
     *
     * @type {string}
     * @memberof VetTxClauses
     */
    private String data;

}
