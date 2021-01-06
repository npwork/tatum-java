package io.tatum.model.response.ltc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.tatum.model.response.btc.BtcTxInputs;
import io.tatum.model.response.btc.BtcTxOutputs;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * The type Ltc tx.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class LtcTx {

    private String hash;
    private String witnessHash;
    private String fee;
    private String rate;
    private BigDecimal ps;
    private BigDecimal height;
    private String block;
    private BigDecimal ts;
    private BigDecimal index;
    private LtcTxOutputs[] outputs;
    private BigDecimal flag;
    private LtcTxInputs[] inputs;
    private BigDecimal version;
    private BigDecimal locktime;

}
