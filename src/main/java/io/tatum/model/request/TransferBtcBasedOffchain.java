package io.tatum.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class TransferBtcBasedOffchain {

    @Size(min = 1, max = 500)
    @NotEmpty
    private String mnemonic;

    @NotEmpty
    @Valid
    private KeyPair[] keyPair;

    @NotEmpty
    @Valid
    private CreateWithdrawal withdrawal;
}
