package io.tatum.model.response.btc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * The type Btc utxo.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class BtcUTXO {

    private int version;
    private long height;
    private long value;
    private BigDecimal script;
    private String address;
    private Boolean coinbase;
    private String hash;
    private int index;

}
