package io.tatum.model.request;

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
public class BroadcastWithdrawal {
    /**
     * Currency of signed transaction to be broadcast, BTC, LTC, BCH, ETH, XRP, ERC20
     * @type {string}
     * @memberof BroadcastWithdrawal
     */
    private String currency;

     /**
     * Raw signed transaction to be published to network.
     * @type {string}
     * @memberof BroadcastWithdrawal
     */
     private String txData;

    /**
     * Withdrawal ID to be completed by transaction broadcast
     * @type {string}
     * @memberof BroadcastWithdrawal
     */
    private String withdrawalId;

    /**
     * Signature ID to be completed by transaction broadcast
     * @type {string}
     * @memberof BroadcastWithdrawal
     */
    private String signatureId;
}
