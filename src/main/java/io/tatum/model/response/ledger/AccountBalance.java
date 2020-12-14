package io.tatum.model.response.ledger;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountBalance {

    /**
     * Account balance represents all assets on the account, available and blocked.
     * @type {string}
     * @memberof AccountBalance
     */
    private String accountBalance;

    /**
     * Available balance on the account represents account balance minus blocked amount on the account.
     * If the account is frozen or customer is disabled, the available balance will be 0.
     * Available balance should be user do determine how much can customer send or withdraw from the account.
     * @type {string}
     * @memberof AccountBalance
     */
    private String availableBalance;

}
