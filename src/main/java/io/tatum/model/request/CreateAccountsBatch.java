package io.tatum.model.request;

import io.tatum.annotation.NotEmptyFields;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class CreateAccountsBatch {

    @NotEmpty
    @NotEmptyFields
    @Valid
    private CreateAccount[] accounts;
}
