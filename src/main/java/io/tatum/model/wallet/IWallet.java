package io.tatum.model.wallet;

public interface IWallet {

    void setAddress(String address);
    String getAddress();

    void  setSecret(String secret);
    String getSecret();
}
