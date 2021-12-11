package io.tatum.model.response.bch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * The type Bch block.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class BchBlock {

    private String hash;
    private long size;
    private long height;
    private int version;
    private String merkleroot;
    private BchTx[] tx;
    private String versionHex;
    private String bits;
    private long strippedsize;
    private long time;
    private long nonce;
    private BigDecimal difficulty;
    private int confirmations;
    private String previousblockhash;
    private String nextblockhash;

}
