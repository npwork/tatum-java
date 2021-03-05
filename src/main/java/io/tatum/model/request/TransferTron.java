package io.tatum.model.request;

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
public class TransferTron {

    @Size(min=64, max=64)
    private String fromPrivateKey;

    @Size(min=34, max=34)
    private String from;

    @Size(min=36, max=36)
    private String signatureId;

    @NotEmpty
    @Size(min=34, max=34)
    private String to;

    @NotEmpty
    @Pattern(regexp = "^[+]?((\\d+(\\.\\d*)?)|(\\.\\d+))$")
    private String amount;
}
