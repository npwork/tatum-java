package io.tatum.model.response.offchain;

import io.tatum.model.response.common.TxHash;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class BroadcastResult {

    private TxHash txHash;
    private String id;
}
