package io.tatum.model.wallet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class MnemonicWallet {
    /**
     * mnemonic seed
     */
    String mnemonic;

    /**
     * extended public key to derive addresses from
     */
    String xpub;
}
