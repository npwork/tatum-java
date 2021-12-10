package io.tatum.model.response.xrp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class XrpTransaction {
    @JsonProperty("Account")
    @NotNull
    private String account;

    @JsonProperty("Destination")
    @NotNull
    private String destination;

    @JsonProperty("DestinationTag")
    @NotNull
    private BigDecimal destinationTag;

    @JsonProperty("Fee")
    @NotNull
    private BigDecimal fee;

    @JsonProperty("Flags")
    @NotNull
    private BigDecimal flags;



    @JsonProperty("InvoiceID")
    @NotNull
    private String invoiceID;

    @JsonProperty("LastLedgerSequence")
    @NotNull
    private BigDecimal lastLedgerSequence;

    @JsonProperty("Sequence")
    @NotNull
    private BigDecimal sequence;

    @JsonProperty("SigningPubKey")
    @NotNull
    private String signingPubKey;

    @JsonProperty("SourceTag")
    @NotNull
    private String sourceTag;

    @JsonProperty("TransactionType")
    @NotNull
    private String transactionType;

    @JsonProperty("TxnSignature")
    @NotNull
    private String txnSignature;

    @NotNull
    private String hash;

    // @TODO - bug in parser?
    /*@JsonProperty("Amount")
    private BigDecimal amount;*/

    private XrpTransactionMetadata metaData;
}
