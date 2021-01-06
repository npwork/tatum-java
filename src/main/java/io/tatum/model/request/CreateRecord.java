package io.tatum.model.request;

import io.tatum.model.request.transaction.Fee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigInteger;

/**
 * The type Create record.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class CreateRecord {

    @NotEmpty
    @Size(min=1, max=130000)
    private String data;

    @NotEmpty
    @Size(min=32, max=64)
    private String fromPrivateKey;

    @Size(min=42, max=42)
    private String to;

    @Min(0)
    private BigInteger nonce;

    @Valid
    private Fee ethFee;
}
