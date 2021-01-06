package io.tatum.model.response.common;

/**
 * The interface Transaction hash.
 *
 * @export
 * @interface TransactionHash
 */
public interface ITransactionHash {

    /**
     * TX hash of successful transaction.
     *
     * @return the tx id
     * @type {string}
     * @memberof TransactionHash
     */
    String getTxId();

    /**
     * Sets tx id.
     *
     * @param txId the tx id
     */
    void setTxId(String txId);
}
