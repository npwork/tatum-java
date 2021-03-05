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
public class CreateTronTrc10 {

    @Size(min=64, max=64)
    private String fromPrivateKey;

    @Size(min=34, max=34)
    private String from;

    @Size(min=36, max=36)
    private String signatureId;

    @NotEmpty
    @Size(min=1, max=100)
    private String name;

    @NotEmpty
    @Size(min=1, max=100)
    private String abbreviation;

    @NotEmpty
    @Size(min=1, max=100)
    private String description;

    @NotEmpty
    @Size(min=1, max=100)
    private String url;

    @NotNull
    @Min(0)
    private long totalSupply;

    @NotNull
    @Min(0)
    @Max(5)
    private int decimals;
}
