package io.tatum.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class TransferBtcBasedOffchainKMS {

    @Size(min=1, max=150)
    @NotEmpty
    private String xpub;

    @NotEmpty
    private String signatureId;

    @NotNull
    private CreateWithdrawal withdrawal;
}
