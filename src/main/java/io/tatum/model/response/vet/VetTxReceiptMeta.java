package io.tatum.model.response.vet;

import lombok.*;

import java.math.BigDecimal;

/**
 * The type Vet tx receipt meta.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode
public class VetTxReceiptMeta {
    private String blockID;
    private Long blockNumber;
    private Long blockTimestamp;
    private String txID;
    private String txOrigin;
}
