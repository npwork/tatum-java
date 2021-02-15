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
    public String recipientAccountId;

    @NotEmpty
    @Size(max = 38)
    @Pattern(regexp = "^[+]?((\\d+(\\.\\d*)?)|(\\.\\d+))$") // number string
    public String amount;

    @Size(min = 1, max = 100)
    public String paymentId;

    @Size(min = 1, max = 100)
    public String transactionCode;

    @Size(min = 1, max = 500)
    public String senderNote;

    @Size(min = 1, max = 500)
    public String recipientNote;

    @Min(0)
    public long baseRate;

    public boolean anonymous;

    public boolean compliant;
}
