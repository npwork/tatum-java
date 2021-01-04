package io.tatum.model.response.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class TxHash {
    /**
     * TX hash of successful transaction.
     * @type {string}
     * @memberof TxHash
     */
    private String txId;

    /**
     * Whethet withdrawal was completed in Tatum's internal ledger. If not, it must be done manually.
     * @type {boolean}
     * @memberof TxHash
     */
    private boolean completed;
}
