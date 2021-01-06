package io.tatum.model.response.btc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * The type Btc tx outputs.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class BtcTxOutputs {

    private BigDecimal value;
    private String script;
    private String address;

}
