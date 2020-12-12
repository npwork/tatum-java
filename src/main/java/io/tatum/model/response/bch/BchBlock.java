package io.tatum.model.response.bch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class BchBlock {

    private String hash;
    private BigDecimal size;
    private BigDecimal height;
    private BigDecimal version;
    private String merkleroot;
    private BchTx[] tx;
    private String versionHex;
    private String bits;
    private BigDecimal strippedsize;
    private BigDecimal time;
    private BigDecimal nonce;
    private BigDecimal difficulty;
    private BigDecimal confirmations;
    private String previousblockhash;
    private String nextblockhash;

}
