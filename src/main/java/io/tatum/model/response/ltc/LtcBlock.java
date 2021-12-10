package io.tatum.model.response.ltc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.math.BigDecimal;

/**
 * The type Ltc block.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class LtcBlock {

    private String hash;
    private BigDecimal height;
    private long version;
    private String prevBlock;
    private String merkleRoot;
    private long ts;
    private long bits;
    private long nonce;
    private LtcTx[] txs;
}
