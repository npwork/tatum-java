package io.tatum.model.request.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * The type From address.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class FromAddress {

    @NotEmpty
    @Size(min = 30, max = 50)
    private String address;

    @NotEmpty
    @Size(min = 52, max = 52)
    private String privateKey;
}
