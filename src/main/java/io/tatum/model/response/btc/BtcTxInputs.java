package io.tatum.model.response.btc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * The type Btc tx inputs.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class BtcTxInputs {

    private BtcTxPrevout prevout;
    private String script;
    private String witness;
    private long sequence;
    private BtcTxCoin coin;

}
