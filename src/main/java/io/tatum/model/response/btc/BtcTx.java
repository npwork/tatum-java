package io.tatum.model.response.btc;

import io.tatum.model.response.common.ITxInputs;
import io.tatum.model.response.common.ITxOutputs;

import java.math.BigDecimal;

public class BtcTx implements IBtcTx {

    private String hash;
    private String witnessHash;
    private BigDecimal fee;
    private BigDecimal rate;
    private BigDecimal mtime;
    private BigDecimal height;
    private String block;
    private BigDecimal time;
    private BigDecimal index;
    private BigDecimal version;
    private ITxInputs[] inputs;
    private ITxOutputs[] outputs;
    private BigDecimal locktime;

    @Override
    public String getHash() {
        return hash;
    }

    @Override
    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public String getWitnessHash() {
        return witnessHash;
    }

    @Override
    public void setWitnessHash(String witnessHash) {
        this.witnessHash = witnessHash;
    }

    @Override
    public BigDecimal getFee() {
        return fee;
    }

    @Override
    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    @Override
    public BigDecimal getRate() {
        return rate;
    }

    @Override
    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    @Override
    public BigDecimal getMtime() {
        return mtime;
    }

    @Override
    public void setMtime(BigDecimal mtime) {
        this.mtime = mtime;
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
    public String getBlock() {
        return block;
    }

    @Override
    public void setBlock(String block) {
        this.block = block;
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
    public BigDecimal getIndex() {
        return index;
    }

    @Override
    public void setIndex(BigDecimal index) {
        this.index = index;
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
    public ITxInputs[] getInputs() {
        return inputs;
    }

    @Override
    public void setInputs(ITxInputs[] inputs) {
        this.inputs = inputs;
    }

    @Override
    public ITxOutputs[] getOutputs() {
        return outputs;
    }

    @Override
    public void setOutputs(ITxOutputs[] outputs) {
        this.outputs = outputs;
    }

    @Override
    public BigDecimal getLocktime() {
        return locktime;
    }

    @Override
    public void setLocktime(BigDecimal locktime) {
        this.locktime = locktime;
    }
}
