package io.tatum.model.response.ltc;

import io.tatum.model.response.common.ITxInputs;
import io.tatum.model.response.common.ITxOutputs;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
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
    private ITxOutputs[] outputs;
    private BigDecimal flag;
    private ITxInputs[] inputs;
    private BigDecimal version;
    private BigDecimal locktime;

}
