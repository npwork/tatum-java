package io.tatum.model.response.ltc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * The type Ltc tx inputs.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class LtcTxInputs {

    private LtcTxPrevout prevout;
    private String script;
    private String witness;
    private BigDecimal sequence;
    private LtcTxCoin coin;

}
