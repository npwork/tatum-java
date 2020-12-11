package io.tatum.model.response.btc;

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
public class BtcTx implements IBtcTx {

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
    private ITxInputs[] inputs;
    private ITxOutputs[] outputs;
    private BigDecimal locktime;

}
