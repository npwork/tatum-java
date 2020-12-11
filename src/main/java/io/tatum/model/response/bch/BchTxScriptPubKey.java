package io.tatum.model.response.bch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class BchTxScriptPubKey implements IBchTxScriptPubKey {

    private String hex;
    private String asm;
    private String[] addresses;
    private String type;

}
