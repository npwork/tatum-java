package io.tatum.model.response.xrp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountDataDetails {
    @NotNull
    @JsonProperty("Account")
    private String account;


    @NotNull
    @JsonProperty("Balance")
    private BigDecimal balance;

    @NotNull
    @JsonProperty("Flags")
    private Long flags;

    @NotNull
    @JsonProperty("LedgerEntryType")
    private String ledgerEntryType;

    @NotNull
    @JsonProperty("OwnerCount")
    private Long ownerCount;

    @NotNull
    @JsonProperty("PreviousTxnID")
    private String previousTxnID;
    @NotNull
    @JsonProperty("PreviousTxnLgrSeq")
    private Integer previousTxnLgrSeq;

    @NotNull
    @JsonProperty("Sequence")
    private Integer sequence;

    @NotNull
    private String index;
}
