package io.tatum.model.request;

import io.tatum.annotation.TypeOf;
import io.tatum.model.response.ledger.SubscriptionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class CreateSubscription {

    @NotNull
    private SubscriptionType type;

    @NotNull
    @Valid
    @TypeOf(SubscriptionAttr.class)
    private Object attr;
}
