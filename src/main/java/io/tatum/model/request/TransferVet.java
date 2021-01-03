package io.tatum.model.request;

import io.tatum.model.request.transaction.VetFee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class TransferVet {

    @NotEmpty
    @Size(min=66, max=66)
    private String fromPrivateKey;

    @NotEmpty
    @Size(min=42, max=42)
    private String to;

    @NotEmpty
    @Pattern(regexp = "^[+]?((\\d+(\\.\\d*)?)|(\\.\\d+))$")
    private String amount;

    @Size(max=10000)
    private String data;

    public VetFee fee;
}
