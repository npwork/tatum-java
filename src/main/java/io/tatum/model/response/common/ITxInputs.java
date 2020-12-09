package io.tatum.model.response.common;

import java.math.BigDecimal;

/**
 * @export
 * @interface ITxInputs
 */
public interface ITxInputs {

    /**
     * @type {ITxPrevout}
     * @memberof ITxInputs
     */
    ITxPrevout getPrevout();

    void setPrevout(ITxPrevout prevout);

    /**
     * Data generated by a spender which is almost always used as variables to satisfy a pubkey script.
     *
     * @type {string}
     * @memberof ITxInputs
     */
    String getScript();

    void setScript(String script);

    /**
     * Transaction witness.
     *
     * @type {string}
     * @memberof ITxInputs
     */
    String getWitness();

    void setWitness(String witness);

    /**
     * @type {number}
     * @memberof ITxInputs
     */
    BigDecimal getSequence();

    void setSequence(BigDecimal sequence);

    /**
     * @type {ITxCoin}
     * @memberof ITxInputs
     */
    ITxCoin getCoin();

    void setCoin(ITxCoin coin);
}
