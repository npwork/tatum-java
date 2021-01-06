package io.tatum.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * The type Key pair.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class KeyPair {

    @NotEmpty
    @Size(min=30, max=50)
    private String address;

    @NotEmpty
    @Size(min=52, max=52)
    private String privateKey;
}
