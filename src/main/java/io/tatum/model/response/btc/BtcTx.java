package io.tatum.model.response.btc;

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
public class BtcTx {

    private String hash;
    private String witnessHash;
    private BigDecimal fee;
    private BigDecimal rate;
    private BigDecimal mtime;
    private BigDecimal height;
    private String block;
    private BigDecimal time;
    private BigDecimal index;
    private BigDecimal version;
    private BtcTxInputs[] inputs;
    private BtcTxOutputs[] outputs;
    private BigDecimal locktime;

}
