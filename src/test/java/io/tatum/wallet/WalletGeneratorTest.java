package io.tatum.wallet;

import io.tatum.model.request.Currency;
import io.tatum.model.wallet.Wallet;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertNotNull;

public class WalletGeneratorTest {

    @Test
    public void generateXrpWalletTest() throws Exception {
        Wallet xrpWallet = WalletGenerator.generateWallet(Currency.XRP, true, null);
        assertNotNull(xrpWallet);
        System.out.println(xrpWallet.getAddress());
        System.out.println(xrpWallet.getSecret());
    }

    @Test
    public void generateBtcWallet_Testnet_Test() throws Exception {
        Wallet wallet = WalletGenerator.generateWallet(Currency.BTC, true,
                "quantum tobacco key they maid mean crime youth chief jungle mind design broken tilt bus shoulder leaf good forward erupt split divert bread kitten");

        assertThat(wallet, hasProperty("mnemonic", equalTo("quantum tobacco key they maid mean crime youth chief jungle mind design broken tilt bus shoulder leaf good forward erupt split divert bread kitten")));
        assertThat(wallet, hasProperty("xpub", equalTo("tpubDFjLw3ykn4aB7fFt96FaqRjSnvtDsU2wpVr8GQk3Eo612LS9jo9JgMkQRfYVG248J3pTBsxGg3PYUXFd7pReNLTeUzxFcUDL3zCvrp3H34a")));
    }

    @Test
    public void generateBtcWallet_Mainnet_Test() throws Exception {
        Wallet wallet = WalletGenerator.generateWallet(Currency.BTC,false,
                "quantum tobacco key they maid mean crime youth chief jungle mind design broken tilt bus shoulder leaf good forward erupt split divert bread kitten");

        assertThat(wallet, hasProperty("mnemonic", equalTo("quantum tobacco key they maid mean crime youth chief jungle mind design broken tilt bus shoulder leaf good forward erupt split divert bread kitten")));
        assertThat(wallet, hasProperty("xpub", equalTo("xpub6DtevPxud8AJUCqddtVqpqxAJvjFrYYvtt4co2kZF7ifPW3d5FJ3B9i5x7xL4CZirb2eNDEVqCtBgiGZR6Kkee5RdHsDoJVbtxi946axjXJ")));
    }

    @Test
    public void generateLtcWallet_Testnet_Test() throws Exception {
        Wallet wallet = WalletGenerator.generateWallet(Currency.LTC,true,
                "quantum tobacco key they maid mean crime youth chief jungle mind design broken tilt bus shoulder leaf good forward erupt split divert bread kitten");

        assertThat(wallet, hasProperty("mnemonic", equalTo("quantum tobacco key they maid mean crime youth chief jungle mind design broken tilt bus shoulder leaf good forward erupt split divert bread kitten")));
        assertThat(wallet, hasProperty("xpub", equalTo("ttub4giastL5S3AicjXRBEJt7uq22b611rJvVfTgJSRfYeyZkwXwKnZcctK3tEjMpqrgiNSnYAzkKPJDxGoKNWQzkzTJxSryHbaYxsYW9Vr6AYQ")));
    }

    @Test
    public void generateLtcWallet_Mainnet_Test() throws Exception {
        Wallet wallet = WalletGenerator.generateWallet(Currency.LTC,false,
                "quantum tobacco key they maid mean crime youth chief jungle mind design broken tilt bus shoulder leaf good forward erupt split divert bread kitten");

        assertThat(wallet, hasProperty("mnemonic", equalTo("quantum tobacco key they maid mean crime youth chief jungle mind design broken tilt bus shoulder leaf good forward erupt split divert bread kitten")));
        assertThat(wallet, hasProperty("xpub", equalTo("Ltub2aXe9g8RPgAcY6jb6FftNJfQXHMV6UNBeZwrWH1K3vjpua9u8uj95xkZyCC4utdEbfYeh9TwxcUiFy2mGzBCJVBwW3ezHmLX2fHxv7HUt8J")));
    }

    @Test
    public void generateEthWallet_Testnet_Test() throws Exception {
        Wallet wallet = WalletGenerator.generateWallet(Currency.ETH,true,
                "quantum tobacco key they maid mean crime youth chief jungle mind design broken tilt bus shoulder leaf good forward erupt split divert bread kitten");

        assertThat(wallet, hasProperty("mnemonic", equalTo("quantum tobacco key they maid mean crime youth chief jungle mind design broken tilt bus shoulder leaf good forward erupt split divert bread kitten")));
        assertThat(wallet, hasProperty("xpub", equalTo("xpub6FMiQpA54nciqs52guGVdWQ5TonZt5XtGsFpurgtttL7H3mSfaJDXv5aBdThjX6tW9HYaJSQ8wZVnLm1ixaQUu1MRQCwvwZ6U2cX6mwWT25")));
    }

    @Test
    public void generateEthWallet_Mainnet_Test() throws Exception {
        Wallet wallet = WalletGenerator.generateWallet(Currency.ETH,false,
                "quantum tobacco key they maid mean crime youth chief jungle mind design broken tilt bus shoulder leaf good forward erupt split divert bread kitten");

        assertThat(wallet, hasProperty("mnemonic", equalTo("quantum tobacco key they maid mean crime youth chief jungle mind design broken tilt bus shoulder leaf good forward erupt split divert bread kitten")));
        assertThat(wallet, hasProperty("xpub", equalTo("xpub6DtR524VQx3ENj2E9pNZnjqkVp47YN5sRCP5y4Gs6KZTwDhH9HTVX8shJPt74WaPZRftRXFfnsyPbMPh6DMEmrQ2WBxDJzGxriStAB36bQM")));
    }

    @Test
    public void generateBchWallet_Testnet_Test() throws Exception {
        Wallet wallet = WalletGenerator.generateWallet(Currency.BCH,true,
                "quantum tobacco key they maid mean crime youth chief jungle mind design broken tilt bus shoulder leaf good forward erupt split divert bread kitten");

        assertThat(wallet, hasProperty("mnemonic", equalTo("quantum tobacco key they maid mean crime youth chief jungle mind design broken tilt bus shoulder leaf good forward erupt split divert bread kitten")));
        assertThat(wallet, hasProperty("xpub", equalTo("tpubDExJFAGFe7NbFfXAtG1TRF19LDxq9JCFnHncz6mFjj2jabiNNVUiDUtpipbLSkNo74j2Rke82tkwzWEvDShudB7nT49mSimsF9gzFwTf4nw")));
    }

    @Test
    public void generateBchWallet_Mainnet_Test() throws Exception {
        Wallet wallet = WalletGenerator.generateWallet(Currency.BCH,false,
                "quantum tobacco key they maid mean crime youth chief jungle mind design broken tilt bus shoulder leaf good forward erupt split divert bread kitten");

        assertThat(wallet, hasProperty("mnemonic", equalTo("quantum tobacco key they maid mean crime youth chief jungle mind design broken tilt bus shoulder leaf good forward erupt split divert bread kitten")));
        assertThat(wallet, hasProperty("xpub", equalTo("xpub6EafivSZvqR8ysLKS52NDKfn16sB9uhCEfCKdYi7PpGqqK3fJGdd53DzUnWYvFRZKAC7pB8FVnvuJKkJparfjjfVPTQTmC7dfC6aVvw6f98")));
    }

    @Test
    public void generateVetWallet_Testnet_Test() throws Exception {
        Wallet wallet = WalletGenerator.generateWallet(Currency.VET,true,
                "quantum tobacco key they maid mean crime youth chief jungle mind design broken tilt bus shoulder leaf good forward erupt split divert bread kitten");

        assertThat(wallet, hasProperty("mnemonic", equalTo("quantum tobacco key they maid mean crime youth chief jungle mind design broken tilt bus shoulder leaf good forward erupt split divert bread kitten")));
        assertThat(wallet, hasProperty("xpub", equalTo("xpub6FMiQpA54nciqs52guGVdWQ5TonZt5XtGsFpurgtttL7H3mSfaJDXv5aBdThjX6tW9HYaJSQ8wZVnLm1ixaQUu1MRQCwvwZ6U2cX6mwWT25")));
    }

    @Test
    public void generateVetWallet_Mainnet_Test() throws Exception {
        Wallet wallet = WalletGenerator.generateWallet(Currency.VET,false,
                "quantum tobacco key they maid mean crime youth chief jungle mind design broken tilt bus shoulder leaf good forward erupt split divert bread kitten");

        assertThat(wallet, hasProperty("mnemonic", equalTo("quantum tobacco key they maid mean crime youth chief jungle mind design broken tilt bus shoulder leaf good forward erupt split divert bread kitten")));
        assertThat(wallet, hasProperty("xpub", equalTo("xpub6EzJLu3Hi5hEFAkiZAxCTaXqXoS95seTnG1tdYdF8fBcVZCfR8GQP8UGvfF52szpwZqiiGHJw5694emxSpYBE5qDxAZUgiHLzbVhb5ErRMa")));
    }

    @Test
    public void generateTronWallet_Test() throws Exception {
        Wallet wallet = WalletGenerator.generateWallet(Currency.TRON,false,
                "quantum tobacco key they maid mean crime youth chief jungle mind design broken tilt bus shoulder leaf good forward erupt split divert bread kitten");

        assertThat(wallet, hasProperty("mnemonic", equalTo("quantum tobacco key they maid mean crime youth chief jungle mind design broken tilt bus shoulder leaf good forward erupt split divert bread kitten")));
        assertThat(wallet, hasProperty("xpub", equalTo("0244b3f40c6e570ae0032f6d7be87737a6c4e5314a4a1a82e22d0460a0d0cd794936c61f0c80dc74ace4cd04690d4eeb1aa6555883be006e1748306faa7ed3a26a")));
    }
}
