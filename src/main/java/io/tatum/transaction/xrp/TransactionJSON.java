package io.tatum.transaction.xrp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * The type Transaction json.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionJSON {

    @NotEmpty
    private String transactionType;

    @NotEmpty
    private String account;

    @NotEmpty
    private String destination;

    @NotNull
    @Min(0)
    private long amount;

    @NotNull
    @Min(0)
    private long flags;

    @NotNull
    @Min(0)
    private int lastLedgerSequence;

    @NotNull
    @Min(0)
    private long fee;

    @NotNull
    @Min(0)
    private int sequence;

}
