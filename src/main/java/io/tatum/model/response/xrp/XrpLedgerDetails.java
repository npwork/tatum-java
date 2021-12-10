package io.tatum.model.response.xrp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class XrpLedgerDetails {
    @NotNull
    private Boolean accepted;

    @JsonProperty("account_hash")
    @NotNull
    private String accountHash;

    @JsonProperty("close_flags")
    @NotNull
    private Long closeFlags;

    @JsonProperty("close_time")
    @NotNull
    private Long closeTime;

    @JsonProperty("close_time_human")
    @NotNull
    private String closeTimeHuman;

    @JsonProperty("close_time_resolution")
    @NotNull
    private Long closeTimeResolution;

    @NotNull
    private Boolean closed;

    @NotNull
    private String hash;

    @JsonProperty("ledger_hash")
    @NotNull
    private String ledgerHash;

    @JsonProperty("ledger_index")
    @NotNull
    private Long ledgerIndex;

    @JsonProperty("parent_close_time")
    @NotNull
    private Long parentCloseTime;

    @JsonProperty("parent_hash")
    @NotNull
    private String parentHash;

    @NotNull
    private Long seqNum;

    @NotNull
    private BigDecimal totalCoins;

    @JsonProperty("transaction_hash")
    @NotNull
    private String transactionHash;

    @NotNull
    private List<XrpTransaction> transactions;
}
