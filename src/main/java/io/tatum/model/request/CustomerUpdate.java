package io.tatum.model.request;

import io.tatum.model.response.ledger.Fiat;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * The type Customer update.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode
public class CustomerUpdate {

    private Country customerCountry;

    private Fiat accountingCurrency;

    private Country providerCountry;

    @Size(min = 1, max = 100)
    @NotEmpty
    private String externalId;

}
