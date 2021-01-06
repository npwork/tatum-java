package io.tatum.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * The type Transfer xlm offchain.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class TransferXlmOffchain {

    @NotEmpty
    @Size(min=56, max=56)
    private String secret;

    @NotEmpty
    private CreateWithdrawal withdrawal;
}
