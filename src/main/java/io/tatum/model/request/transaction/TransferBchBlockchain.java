package io.tatum.model.request.transaction;

import io.tatum.annotation.NotEmptyFields;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * The type Transfer bch blockchain.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class TransferBchBlockchain {

    @NotEmpty
    @Valid
    @NotEmptyFields
    private FromUTXOBcash[] fromUTXO;

    @NotEmpty
    @NotEmptyFields
    @Valid
    private To[] to;
}
