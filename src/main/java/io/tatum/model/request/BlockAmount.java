package io.tatum.model.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Optional;

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
    private Optional<String> description;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Optional<String> getDescription() {
        return description;
    }

    public void setDescription(Optional<String> description) {
        this.description = description;
    }
}
