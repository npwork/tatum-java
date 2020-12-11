package io.tatum.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class CreateTransaction {

    @NotEmpty
    @Size(min = 24, max = 24)
    private String senderAccountId;

    @NotEmpty
    @Size(min = 24, max = 24)
    private String recipientAccountId;

    @NotEmpty
    @Size(max = 38)
    @Pattern(regexp = "\\d+") // number string
    @Pattern(regexp = "^[+]?((\\d+(\\.\\d*)?)|(\\.\\d+))$")
    private String amount;

    @Size(min = 1, max = 100)
    private Optional<String> paymentId;

    @Size(min = 1, max = 100)
    private Optional<String> transactionCode;

    @Size(min = 1, max = 500)
    private Optional<String> senderNote;

    @Size(min = 1, max = 500)
    private Optional<String> recipientNote;

    @Min(0)
    private Optional<BigDecimal> baseRate;

    private Optional<Boolean> anonymous;

    private Optional<Boolean> compliant;

}