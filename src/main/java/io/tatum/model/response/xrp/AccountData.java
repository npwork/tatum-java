package io.tatum.model.response.xrp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * The type Account data.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountData {

    @NotNull
    @Min(0)
    private Integer ledgerCurrentIndex;

    @NotNull
    @Min(0)
    private Integer sequence;
}
