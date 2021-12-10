package io.tatum.model.response.xrp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * The type Account data.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountData {
    @NotNull
    @Min(0)
    @JsonProperty("ledger_current_index")
    private Integer ledgerCurrentIndex;

    @NotNull
    private Boolean validated;

    @NotNull
    @JsonProperty("account_data")
    private AccountDataDetails details;
}
