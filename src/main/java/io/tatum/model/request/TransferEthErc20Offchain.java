package io.tatum.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * The type Transfer eth erc 20 offchain.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class TransferEthErc20Offchain {

    @Size(min=1, max=500)
    @NotEmpty
    private String mnemonic;

    @Min(0)
    @NotEmpty
    private int index;

    @Size(min=66, max=66)
    @NotEmpty
    private String privateKey;

    @NotEmpty
    @Valid
    private BaseTransferEthErc20Offchain baseTransferEthErc20Offchain;
}
