package io.tatum.model.response.xrp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class XrpTransactionMetadata {
    @JsonProperty("TransactionIndex")
    private Long transactionIndex;

    @JsonProperty("TransactionResult")
    private String transactionResult;

    // @TODO - bug in parser?
    /*@JsonProperty("delivered_amount")
    private String deliveredAmount;*/

    // @TODO add AffectedNodes
}
