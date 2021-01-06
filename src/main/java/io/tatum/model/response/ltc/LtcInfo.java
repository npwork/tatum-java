package io.tatum.model.response.ltc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * The type Ltc info.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class LtcInfo {

    private String chain;
    private BigDecimal blocks;
    private BigDecimal headers;
    private String bestblockhash;
    private BigDecimal difficulty;

}
