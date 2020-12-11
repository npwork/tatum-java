package io.tatum.model.response.btc;

import io.tatum.model.response.common.ITxCoin;
import io.tatum.model.response.common.ITxInputs;
import io.tatum.model.response.common.ITxPrevout;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class BtcTxInputs implements ITxInputs {

    private ITxPrevout prevout;
    private String script;
    private String witness;
    private BigDecimal sequence;
    private ITxCoin coin;

}
