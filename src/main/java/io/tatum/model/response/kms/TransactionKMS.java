package io.tatum.model.response.kms;

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
public class TransactionKMS {

    private String id;

    private Currency chain;

    private String serializedTransaction;

    private String[] hashes;

    private Optional<String> txId;

    private Optional<String> withdrawalId;

    private Optional<BigDecimal> index;

    private Optional<WithdrawalResponseData[]> withdrawalResponses;
}