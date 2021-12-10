package io.tatum.model.response.xrp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class XrpLedger {
    @NotNull
    @JsonProperty("ledger_hash")
    private String ledgerHash;

    @NotNull
    @Positive
    @JsonProperty("ledger_index")
    private Long ledgerIndex;

    @NotNull
    private Boolean validated;

    @JsonProperty("ledger")
    @NotNull
    private XrpLedgerDetails details;
}
