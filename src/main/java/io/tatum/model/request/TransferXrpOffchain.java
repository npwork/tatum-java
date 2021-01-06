package io.tatum.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class TransferXrpOffchain {
    @NotEmpty
    @Size(min = 33, max = 34)
    private String account;

    @NotEmpty
    @Size(min = 29, max = 29)
    private String secret;

    @Min(0)
    private int sourceTag;

    @NotNull
    private CreateWithdrawal withdrawal;
}
