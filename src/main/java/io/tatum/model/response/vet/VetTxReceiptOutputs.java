package io.tatum.model.response.vet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class VetTxReceiptOutputs {

    /**
     *
     * @type {Array<any>}
     * @memberof VetTxReceiptOutputs
     */
    private Event[] events;

    /**
     *
     * @type {Array<VetTxReceiptTransfers>}
     * @memberof VetTxReceiptOutputs
     */
    private VetTxReceiptTransfers[] transfers;

}
