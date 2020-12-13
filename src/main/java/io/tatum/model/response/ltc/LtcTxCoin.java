package io.tatum.model.response.ltc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class LtcTxCoin {

    private BigDecimal version;
    private BigDecimal height;
    private BigDecimal value;
    private String script;
    private String address;
    private Boolean coinbase;
}
