package io.tatum.model.request;

import com.google.common.base.Optional;
import io.tatum.model.response.ledger.OperationType;
import io.tatum.model.response.ledger.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class TransactionFilter {

    @Size(min = 1, max = 50)
    private String id;

    @Min(0)
    private BigDecimal from;

    @Min(0)
    private BigDecimal to;

    @Size(min = 1, max = 50)
    private String account;

    @Size(min = 1, max = 50)
    private String counterAccount;

    @Size(min = 1, max = 50)
    private String currency;

    @Size(min = 1, max = 100)
    private String paymentId;

    @Size(min = 1, max = 100)
    private String transactionCode;

    @Size(min = 1, max = 500)
    private String senderNote;

    @Size(min = 1, max = 500)
    private String recipientNote;

    private OperationType opType;

    private TransactionType transactionType;

}
