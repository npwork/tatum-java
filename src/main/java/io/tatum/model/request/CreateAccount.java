package io.tatum.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.tatum.model.response.ledger.Fiat;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * The type Create account.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateAccount {

    @NotEmpty
    @Size(min = 2, max = 40)
    private String currency;

    @Size(max = 192)
    private String xpub;

    private Boolean compliant;

    private Fiat accountingCurrency;

    @Size(min = 1, max = 50)
    private String accountCode;

    @Size(min = 1, max = 20)
    private String accountNumber;

    @Valid
    private CustomerUpdate customer;

}
