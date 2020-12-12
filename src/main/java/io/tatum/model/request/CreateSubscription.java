package io.tatum.model.request;

import io.tatum.model.response.ledger.SubscriptionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class CreateSubscription {

    @NotEmpty
    private SubscriptionType type;

    @NotEmpty
    public ISubscriptionAttr attr;

}