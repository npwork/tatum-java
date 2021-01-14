package io.tatum.model.response.btc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

/**
 * The type Btc block.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class BtcBlock {

    private String hash;
    private long height;
    private long depth;
    private int version;
    private String prevBlock;
    private String merkleRoot;
    private long time;
    private long bits;
    private BigInteger nonce;
    private BtcTx[] txs;

}
