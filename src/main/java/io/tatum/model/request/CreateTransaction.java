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
    private String paymentId;

    @Size(min = 1, max = 100)
    private String transactionCode;

    @Size(min = 1, max = 500)
    private String senderNote;

    @Size(min = 1, max = 500)
    private String recipientNote;

    @Min(0)
    private BigDecimal baseRate;

    private Boolean anonymous;

    private Boolean compliant;

}