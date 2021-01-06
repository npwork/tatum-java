package io.tatum.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * The type Subscription attr complete blockchain tx.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class SubscriptionAttrCompleteBlockchainTx implements SubscriptionAttr {

    @NotEmpty()
    @Size(min=1, max=30)
    @Pattern(regexp = "^BTC|BNB|LTC|BCH|ETH|USDT|LEO|LINK|UNI|FREE|MKR|USDC|BAT|TUSD|PAX|PAXG|PLTC|XCON|MMY|[a-zA-Z0-9_]+$")
    private String currency;
}
