package io.tatum.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.math.BigInteger;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransferXrp {

    @NotEmpty
    @Size(min=33, max=34)
    private String fromAccount;

    @NotEmpty
    @Size(min=29, max=29)
    private String fromSecret;

    @NotEmpty
    @Size(min=33, max=34)
    private String to;

    @NotNull
    private BigDecimal amount;

    private BigDecimal fee;

    @Min(0)
    private Integer sourceTag;

    @Min(0)
    private Integer destinationTag;
}
