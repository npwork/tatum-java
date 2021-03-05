package io.tatum.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigInteger;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class CreateTronTrc20 {

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
    @Size(min=34, max=34)
    private String recipient;

    @NotEmpty
    @Size(min=1, max=100)
    private String symbol;

    @NotEmpty
    private long totalSupply;

    @NotEmpty()
    @Min(0)
    @Max(30)
    private int decimals;
}
