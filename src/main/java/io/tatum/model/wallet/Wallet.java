package io.tatum.model.wallet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * The type Wallet.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class Wallet {
    /**
     * mnemonic seed
     */
    String mnemonic;

    /**
     * extended public key to derive addresses from
     */
    String xpub;

    /**
     * address
     */
    private String address;

    /**
     * secret
     */
    private String secret;

}
