package io.tatum.model.response.btc;

import lombok.*;

import java.math.BigDecimal;

/**
 * The type Btc utxo.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode
public class BtcUTXO {
    private int version;
    private long height;
    private BigDecimal value;
    private String script;
    private String address;
    private Boolean coinbase;
    private String hash;
    private int index;
}
