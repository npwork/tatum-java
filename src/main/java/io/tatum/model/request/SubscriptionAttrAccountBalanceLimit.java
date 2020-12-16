package io.tatum.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class SubscriptionAttrAccountBalanceLimit implements SubscriptionAttr {

    @NotEmpty
    @Size(max = 38)
    @Pattern(regexp = "^[+]?((\\d+(\\.\\d*)?)|(\\.\\d+))$")
    private String limit;

    @NotEmpty
    @Pattern(regexp = "'account'|'available'", flags = Pattern.Flag.CASE_INSENSITIVE)
    private String typeOfBalance;
}
