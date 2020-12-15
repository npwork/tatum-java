package io.tatum.model.response.kms;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.tatum.model.request.Currency;
import io.tatum.model.response.offchain.WithdrawalResponseData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionKMS {

    private String id;

    private Currency chain;

    private String serializedTransaction;

    private String[] hashes;

    private String txId;

    private String withdrawalId;

    private BigDecimal index;

    private WithdrawalResponseData[] withdrawalResponses;
}