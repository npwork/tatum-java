package io.tatum.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * The type Subscription attr incoming blockchain tx.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class SubscriptionAttrIncomingBlockchainTx implements SubscriptionAttr {

    @Size(min=24, max=24)
    @NotEmpty()
    private String id;

    @URL()
    @NotEmpty()
    @Size(max=500)
    private String url;
}
