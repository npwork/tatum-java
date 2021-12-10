package io.tatum.model.response.btc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.math.BigDecimal;

/**
 * The type Btc tx.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class BtcTx {

    private String hash;
    private String witnessHash;
    private BigDecimal fee;
    private BigDecimal rate;
    private long mtime;
    private long height;
    private long blockNumber;
    private long time;
    private int index;
    private int version;
    private BtcTxInputs[] inputs;
    private BtcTxOutputs[] outputs;
    private long locktime;

}
