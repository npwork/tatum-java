package io.tatum.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class BlockageTransaction {
    @NotEmpty
    @Size(min = 24, max = 24)
    private String recipientAccountId;

    @NotEmpty
    @Size(max = 38)
    @Pattern(regexp = "^[+]?((\\d+(\\.\\d*)?)|(\\.\\d+))$") // number string
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
    private long baseRate;

    private boolean anonymous;

    private boolean compliant;
}
