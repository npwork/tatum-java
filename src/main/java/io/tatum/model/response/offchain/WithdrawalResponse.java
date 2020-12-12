package io.tatum.model.response.offchain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class WithdrawalResponse {

    /**
     * Transaction reference of the transaction connected to this withdrawal.
     *
     * @type {string}
     * @memberof WithdrawalResponse
     */
    private String reference;

    /**
     * @type {Array<WithdrawalResponseData>}
     * @memberof WithdrawalResponse
     */
    private WithdrawalResponseData[] data;
    /**
     * ID of withdrawal
     *
     * @type {string}
     * @memberof WithdrawalResponse
     */
    private String id;

}
