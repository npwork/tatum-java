package io.tatum.model.request;

import io.tatum.model.response.ledger.Fiat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class CreateAccount {

    @NotEmpty
    @Size(min = 2, max = 40)
    private String currency;

    @Size(max = 192)
    private Optional<String> xpub;

    private Optional<Boolean> compliant;

    @Size(min = 3, max = 3)
    private Optional<Fiat> accountingCurrency;

    @Size(min = 1, max = 50)
    private Optional<String> accountCode;

    @Size(min = 1, max = 20)
    private Optional<String> accountNumber;

    private Optional<CustomerUpdate> customer;

}
