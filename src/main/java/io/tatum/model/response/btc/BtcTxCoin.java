package io.tatum.model.response.btc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * The type Btc tx coin.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class BtcTxCoin {

    private int version;
    private long height;
    private long value;
    private String script;
    private String address;
    private boolean coinbase;

}
