package io.tatum.model.request;

import io.tatum.model.request.transaction.Fee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigInteger;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class TransferEthErc20 {

    @NotEmpty
    @Size(min = 66, max = 66)
    private String fromPrivateKey;

    @NotEmpty
    @Size(min = 42, max = 42)
    private String to;

    @NotEmpty
    @Pattern(regexp = "\\d+") // number string
    @Pattern(regexp = "^[+]?((\\d+(\\.\\d*)?)|(\\.\\d+))$")
    private String amount;

    @Size(max = 130000)
    private String data;

    @NotEmpty
//    @IsIn(ETH_BASED_CURRENCIES)
    private Currency currency;

    @Valid
    private Fee fee;

    @Min(0)
    private BigInteger nonce;
}
