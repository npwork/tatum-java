package io.tatum.model.response.btc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class BtcBlock implements IBtcBlock {

    private String hash;
    private BigDecimal height;
    private BigDecimal depth;
    private BigDecimal version;
    private String prevBlock;
    private String merkleRoot;
    private BigDecimal time;
    private BigDecimal bits;
    private BigDecimal nonce;
    private IBtcTx[] txs;

}
