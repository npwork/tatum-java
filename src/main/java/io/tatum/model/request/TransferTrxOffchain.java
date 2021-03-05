package io.tatum.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class TransferTrxOffchain extends CreateWithdrawal {

    @Size(min = 1, max = 500)
    @NotEmpty
    private String mnemonic;

    @Min(0)
    @NotNull
    @Max(2147483647)
    private int index;

    @Size(min = 66, max = 66)
    @NotEmpty
    private String fromPrivateKey;

    @Size(min = 34, max = 34)
    @NotEmpty
    private String from;

    @Size(min = 36, max = 36)
    @NotEmpty
    private String signatureId;

    @NotNull
    @Valid
    private CreateWithdrawal withdrawal;
}
