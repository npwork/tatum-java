package io.tatum.model.response.ledger;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.tatum.model.request.Country;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * The type Customer.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer {

    private String id;

    private String externalId;

    private Country customerCountry;

    private Fiat accountingCurrency;

    private Country providerCountry;

    private Boolean active;

    private Boolean enabled;
}
