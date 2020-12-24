package io.tatum.wallet;

import io.tatum.model.wallet.MnemonicWallet;
import io.tatum.model.wallet.XrpWallet;
import io.xpring.xrpl.XrpException;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertNotNull;

public class WalletGeneratorTest {

    @Test
    public void generateXrpWalletTest() throws ExecutionException, InterruptedException {
        XrpWallet xrpWallet = WalletGenerator.generateXrpWallet();
        assertNotNull(xrpWallet);
        System.out.println(xrpWallet.getAddress());
        System.out.println(xrpWallet.getSecret());
    }

    @Test
    public void generateBtcWallet_Testnet_Test() throws ExecutionException, InterruptedException {
        MnemonicWallet wallet = WalletGenerator.generateBtcWallet(true,
                "quantum tobacco key they maid mean crime youth chief jungle mind design broken tilt bus shoulder leaf good forward erupt split divert bread kitten");

        assertThat(wallet, hasProperty("mnemonic", equalTo("quantum tobacco key they maid mean crime youth chief jungle mind design broken tilt bus shoulder leaf good forward erupt split divert bread kitten")));
        assertThat(wallet, hasProperty("xpub", equalTo("tpubDFjLw3ykn4aB7fFt96FaqRjSnvtDsU2wpVr8GQk3Eo612LS9jo9JgMkQRfYVG248J3pTBsxGg3PYUXFd7pReNLTeUzxFcUDL3zCvrp3H34a")));
    }

    @Test
    public void generateBtcWallet_Mainnet_Test() throws ExecutionException, InterruptedException {
        MnemonicWallet wallet = WalletGenerator.generateBtcWallet(false,
                "quantum tobacco key they maid mean crime youth chief jungle mind design broken tilt bus shoulder leaf good forward erupt split divert bread kitten");

        assertThat(wallet, hasProperty("mnemonic", equalTo("quantum tobacco key they maid mean crime youth chief jungle mind design broken tilt bus shoulder leaf good forward erupt split divert bread kitten")));
        assertThat(wallet, hasProperty("xpub", equalTo("xpub6DtevPxud8AJUCqddtVqpqxAJvjFrYYvtt4co2kZF7ifPW3d5FJ3B9i5x7xL4CZirb2eNDEVqCtBgiGZR6Kkee5RdHsDoJVbtxi946axjXJ")));
    }

    @Test
    public void generateLtcWallet_Testnet_Test() throws ExecutionException, InterruptedException {
        MnemonicWallet wallet = WalletGenerator.generateLtcWallet(true,
                "quantum tobacco key they maid mean crime youth chief jungle mind design broken tilt bus shoulder leaf good forward erupt split divert bread kitten");

        assertThat(wallet, hasProperty("mnemonic", equalTo("quantum tobacco key they maid mean crime youth chief jungle mind design broken tilt bus shoulder leaf good forward erupt split divert bread kitten")));
        assertThat(wallet, hasProperty("xpub", equalTo("ttub4giastL5S3AicjXRBEJt7uq22b611rJvVfTgJSRfYeyZkwXwKnZcctK3tEjMpqrgiNSnYAzkKPJDxGoKNWQzkzTJxSryHbaYxsYW9Vr6AYQ")));
    }

    @Test
    public void generateLtcWallet_Mainnet_Test() throws ExecutionException, InterruptedException {
        MnemonicWallet wallet = WalletGenerator.generateLtcWallet(false,
                "quantum tobacco key they maid mean crime youth chief jungle mind design broken tilt bus shoulder leaf good forward erupt split divert bread kitten");

        assertThat(wallet, hasProperty("mnemonic", equalTo("quantum tobacco key they maid mean crime youth chief jungle mind design broken tilt bus shoulder leaf good forward erupt split divert bread kitten")));
        assertThat(wallet, hasProperty("xpub", equalTo("Ltub2aXe9g8RPgAcY6jb6FftNJfQXHMV6UNBeZwrWH1K3vjpua9u8uj95xkZyCC4utdEbfYeh9TwxcUiFy2mGzBCJVBwW3ezHmLX2fHxv7HUt8J")));
    }
}
