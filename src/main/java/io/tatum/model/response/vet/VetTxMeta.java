package io.tatum.model.response.vet;

import lombok.*;

import java.math.BigDecimal;

/**
 * The type Vet tx meta.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode
public class VetTxMeta {
    private String blockID;
    private Long blockNumber;
    private Long blockTimestamp;
}
