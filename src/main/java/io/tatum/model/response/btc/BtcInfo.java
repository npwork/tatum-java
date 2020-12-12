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
public class BtcInfo {

    private String chain;
    private BigDecimal blocks;
    private BigDecimal headers;
    private String bestblockhash;
    private BigDecimal difficulty;

}
