package io.tatum.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * The type Block amount.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class BlockAmount {

    @NotEmpty
    @Size(max = 38)
    @Pattern(regexp = "\\d+") // number string
    @Pattern(regexp="^[+]?((\\d+(\\.\\d*)?)|(\\.\\d+))$")
    private String amount;

    @NotEmpty
    @Size(min = 1, max = 100)
    private String type;

    @Size(min = 1, max = 300)
    private String description;

}
