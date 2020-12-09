package io.tatum.model.response.btc;

import java.math.BigDecimal;

public class BtcBlock implements IBtcBlock {

    private String hash;
    private BigDecimal height;
    private BigDecimal depth;
    private BigDecimal version;
    private String prevBlock;
    private String merkleRoot;
    private BigDecimal time;
    private BigDecimal bits;
    private BigDecimal nonce;
    private IBtcTx[] txs;

    @Override
    public String getHash() {
        return hash;
    }

    @Override
    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public BigDecimal getHeight() {
        return height;
    }

    @Override
    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    @Override
    public BigDecimal getDepth() {
        return depth;
    }

    @Override
    public void setDepth(BigDecimal depth) {
        this.depth = depth;
    }

    @Override
    public BigDecimal getVersion() {
        return version;
    }

    @Override
    public void setVersion(BigDecimal version) {
        this.version = version;
    }

    @Override
    public String getPrevBlock() {
        return prevBlock;
    }

    @Override
    public void setPrevBlock(String prevBlock) {
        this.prevBlock = prevBlock;
    }

    @Override
    public String getMerkleRoot() {
        return merkleRoot;
    }

    @Override
    public void setMerkleRoot(String merkleRoot) {
        this.merkleRoot = merkleRoot;
    }

    @Override
    public BigDecimal getTime() {
        return time;
    }

    @Override
    public void setTime(BigDecimal time) {
        this.time = time;
    }

    @Override
    public BigDecimal getBits() {
        return bits;
    }

    @Override
    public void setBits(BigDecimal bits) {
        this.bits = bits;
    }

    @Override
    public BigDecimal getNonce() {
        return nonce;
    }

    @Override
    public void setNonce(BigDecimal nonce) {
        this.nonce = nonce;
    }

    @Override
    public IBtcTx[] getTxs() {
        return txs;
    }

    @Override
    public void setTxs(IBtcTx[] txs) {
        this.txs = txs;
    }
}
