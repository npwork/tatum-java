package io.tatum.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.tatum.model.response.ledger.Fiat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateAccount {

    @NotEmpty
    @Size(min = 2, max = 40)
    private String currency;

    @Size(max = 192)
    private String xpub;

    private Boolean compliant;

    @Size(min = 3, max = 3)
    private Fiat accountingCurrency;

    @Size(min = 1, max = 50)
    private String accountCode;

    @Size(min = 1, max = 20)
    private String accountNumber;

    private CustomerUpdate customer;

}
