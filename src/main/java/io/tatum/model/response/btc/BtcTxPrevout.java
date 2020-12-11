package io.tatum.model.response.btc;

import io.tatum.model.response.common.ITxPrevout;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class BtcTxPrevout implements ITxPrevout {

    private String hash;
    private BigDecimal index;

}
