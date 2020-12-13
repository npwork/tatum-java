package io.tatum.model.response.vet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class VetTxReceiptOutputs {

    /**
     *
     * @type {Array<any>}
     * @memberof VetTxReceiptOutputs
     */
    private List[] events;

    /**
     *
     * @type {Array<VetTxReceiptTransfers>}
     * @memberof VetTxReceiptOutputs
     */
    private VetTxReceiptTransfers[] transfers;

}
