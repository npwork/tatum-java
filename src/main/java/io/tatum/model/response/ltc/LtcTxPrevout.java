package io.tatum.model.response.ltc;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * The type Ltc tx prevout.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class LtcTxPrevout {

    private String hash;
    private BigDecimal index;

}
