package io.tatum.model.response.btc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * The type Btc tx.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class BtcTx {

    private String hash;
    private String witnessHash;
    private BigDecimal fee;
    private BigDecimal rate;
    private long mtime;
    private long height;
    private String block;
    private long time;
    private int index;
    private int version;
    private BtcTxInputs[] inputs;
    private BtcTxOutputs[] outputs;
    private long locktime;

}
