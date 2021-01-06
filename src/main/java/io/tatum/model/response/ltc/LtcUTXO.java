package io.tatum.model.response.ltc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * The type Ltc utxo.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class LtcUTXO {

    private BigDecimal version;
    private BigDecimal height;
    private BigDecimal value;
    private String script;
    private String address;
    private Boolean coinbase;
    private String hash;
    private BigDecimal index;

}
