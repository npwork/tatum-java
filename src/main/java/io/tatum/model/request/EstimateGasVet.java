package io.tatum.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class EstimateGasVet {

    @NotEmpty
    @Size(min = 66, max = 66)
    private String from;

    @NotEmpty
    @Size(min = 42, max = 42)
    private String to;

    @NotEmpty
    @Pattern(regexp = "\\d+") // number string
    @Pattern(regexp = "^[+]?((\\d+(\\.\\d*)?)|(\\.\\d+))$")
    private String value;

    @Size(max = 10000)
    private String data;

    @Min(0)
    @PositiveOrZero
    private Integer nonce;

}
