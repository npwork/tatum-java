package io.tatum.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class FreezeTron {

    @Size(min = 64, max = 64)
    private String fromPrivateKey;

    @Size(min = 34, max = 34)
    private String from;

    @Size(min = 36, max = 36)
    private String signatureId;

    @NotEmpty
    @Size(min = 34, max = 34)
    private String receiver;

    @NotEmpty
    @Pattern(regexp = "^[+]?((\\d+(\\.\\d*)?)|(\\.\\d+))$") // number string
    private String amount;

    @NotNull
    private Resource resource;

    @NotNull
    @Min(3)
    private long duration;
}
