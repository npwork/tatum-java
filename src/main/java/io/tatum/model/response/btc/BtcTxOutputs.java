package io.tatum.model.response.btc;

import io.tatum.model.response.common.ITxOutputs;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class BtcTxOutputs implements ITxOutputs {

    private BigDecimal value;
    private String script;
    private String address;

}
