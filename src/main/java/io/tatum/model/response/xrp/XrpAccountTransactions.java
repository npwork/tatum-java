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
public class XrpAccountTransactions {
    @NotNull
    private String account;

    @JsonProperty("ledger_index_max")
    @NotNull
    private Long ledgerIndexMax;

    @JsonProperty("ledger_index_min")
    @NotNull
    private Long ledgerIndexMin;

    @NotNull
    private Long limit;

    @NotNull
    private List<XrpAccountTransactionsObject> transactions;

    private Boolean validated;
}

