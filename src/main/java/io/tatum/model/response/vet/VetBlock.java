package io.tatum.model.response.vet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.math.BigDecimal;

/**
 * The type Vet block.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class VetBlock {
    private Long number;

    private String id;

    private Long size;

    private String parentID;

    private Long timestamp;

    private Long gasLimit;

    private String beneficiary;

    private Long gasUsed;

    private Long totalScore;

    private String txsRoot;

    private Long txsFeatures;

    private String stateRoot;

    private String receiptsRoot;

    private String signer;

    private String[] transactions;

}
