package io.tatum.model.response.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * @export
 * @interface SignatureId
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class SignatureId {
    /**
     * Signature ID of pending transaction to sign.
     * @type {string}
     * @memberof SignatureId
     */
    private String signatureId;
}
