package io.tatum.model.request.transaction;

import io.tatum.annotation.NotEmptyFields;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * The type Transfer btc based blockchain.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class TransferBtcBasedBlockchain {

    @Valid
    private FromAddress[] fromAddress;

    @Valid
    private FromUTXO[] fromUTXO;

    @NotEmptyFields
    @Valid
    private To[] to;
}
