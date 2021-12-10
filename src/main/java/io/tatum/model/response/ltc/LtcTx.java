package io.tatum.model.response.ltc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.tatum.model.response.btc.BtcTxInputs;
import io.tatum.model.response.btc.BtcTxOutputs;
import lombok.*;

import java.math.BigDecimal;

/**
 * The type Ltc tx.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class LtcTx {

    private String hash;
    private String witnessHash;
    private BigDecimal fee;
    private BigDecimal rate;
    private Long ps;
    private Long blockNumber;
    private BigDecimal ts;
    private Long index;
    private LtcTxOutputs[] outputs;
    private Long flag;
    private LtcTxInputs[] inputs;
    private Long version;
    private Long locktime;

}
