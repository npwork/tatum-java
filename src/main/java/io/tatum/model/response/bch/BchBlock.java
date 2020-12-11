package io.tatum.model.response.bch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class BchBlock implements IBchBlock {

    private String hash;
    private BigDecimal size;
    private BigDecimal height;
    private BigDecimal version;
    private String merkleroot;
    private IBchTx[] tx;
    private BigDecimal time;
    private BigDecimal nonce;
    private BigDecimal difficulty;
    private BigDecimal confirmations;
    private String previousblockhash;
    private String nextblockhash;

}
