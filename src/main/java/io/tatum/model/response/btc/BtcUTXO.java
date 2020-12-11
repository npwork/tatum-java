package io.tatum.model.response.btc;

import io.tatum.model.response.common.IUTXO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class BtcUTXO implements IUTXO {

    private BigDecimal version;
    private BigDecimal height;
    private BigDecimal value;
    private BigDecimal script;
    private String address;
    private Boolean coinbase;
    private String hash;
    private BigDecimal index;

}
