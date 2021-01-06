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
public class TransferEthErc20OffchainKMS {

    @Size(min=36, max=36)
    @NotEmpty
    private String signatureId;

    @NotNull
    private BaseTransferEthErc20Offchain baseTransferEthErc20Offchain;
}
