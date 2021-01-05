package io.tatum.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class TransferEthOffchain {

    @Size(min=1, max=500)
    @NotEmpty
    private String mnemonic;

    @Min(0)
    @NotEmpty
    private int index;

    @Size(min=66, max=66)
    @NotEmpty
    private String privateKey;

    @Size(max=50000)
    private String data;

    @NotNull
    @Valid
    private BaseTransferEthErc20Offchain baseTransferEthErc20Offchain;
}
