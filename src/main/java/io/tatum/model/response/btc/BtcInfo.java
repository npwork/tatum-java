package io.tatum.model.response.btc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

/**
 * The type Btc info.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class BtcInfo {

    private String chain;
    private long blocks;
    private long headers;
    private String bestblockhash;
    private BigInteger difficulty;

}
