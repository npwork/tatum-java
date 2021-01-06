package io.tatum.model.response.btc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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
    private BigDecimal height;
    private BigDecimal depth;
    private BigDecimal version;
    private String prevBlock;
    private String merkleRoot;
    private BigDecimal time;
    private BigDecimal bits;
    private BigDecimal nonce;
    private BtcTx[] txs;

}
