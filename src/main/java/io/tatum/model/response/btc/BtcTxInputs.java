package io.tatum.model.response.btc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class BtcTxInputs {

    private BtcTxPrevout prevout;
    private String script;
    private String witness;
    private BigDecimal sequence;
    private BtcTxCoin coin;

}
