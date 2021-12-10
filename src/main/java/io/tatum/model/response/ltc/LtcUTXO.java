package io.tatum.model.response.ltc;

import lombok.*;

import java.math.BigDecimal;

/**
 * The type Ltc utxo.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode
public class LtcUTXO {

    private Long version;
    private Long height;
    private BigDecimal value;
    private String script;
    private String address;
    private Boolean coinbase;
    private String hash;
    private Long index;

}
