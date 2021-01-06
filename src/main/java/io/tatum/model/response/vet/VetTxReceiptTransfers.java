package io.tatum.model.response.vet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * The type Vet tx receipt transfers.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class VetTxReceiptTransfers {

    /**
     *
     * @type {string}
     * @memberof VetTxReceiptTransfers
     */
    private String sender;

    /**
     *
     * @type {string}
     * @memberof VetTxReceiptTransfers
     */
    private String recipient;

    /**
     *
     * @type {string}
     * @memberof VetTxReceiptTransfers
     */
    private String amount;

}
