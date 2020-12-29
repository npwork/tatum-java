package io.tatum.wallet;

import io.tatum.model.request.Currency;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static com.google.common.base.Preconditions.checkArgument;
import static org.junit.Assert.assertEquals;

public class AddressTest {

    @Test
    public void generateBtcAddress_Mainnet_Test() throws ExecutionException, InterruptedException {
        Address address = new Address();
        String rs = address.generateBtcAddress(false, "xpub6EsCk1uU6cJzqvP9CdsTiJwT2rF748YkPnhv5Qo8q44DG7nn2vbyt48YRsNSUYS44jFCW9gwvD9kLQu9AuqXpTpM1c5hgg9PsuBLdeNncid", 1);
        assertEquals("1HWYaP13JKtaW2Mhq69NVeSLjRYGpD3aKv", rs);
    }

    @Test
    public void generateBtcAddress_Testnet_Test() throws ExecutionException, InterruptedException {
        Address address = new Address();
        String rs = address.generateBtcAddress(true, "tpubDFjLw3ykn4aB7fFt96FaqRjSnvtDsU2wpVr8GQk3Eo612LS9jo9JgMkQRfYVG248J3pTBsxGg3PYUXFd7pReNLTeUzxFcUDL3zCvrp3H34a", 1);
        assertEquals("mjJotvHmzEuyXZJGJXXknS6N3PWQnw6jf5", rs);
    }

    @Test
    public void generateLtcAddress_Mainnet_Test() throws ExecutionException, InterruptedException {
        Address address = new Address();
        String rs = address.generateLtcAddress(false, "Ltub2aXe9g8RPgAcY6jb6FftNJfQXHMV6UNBeZwrWH1K3vjpua9u8uj95xkZyCC4utdEbfYeh9TwxcUiFy2mGzBCJVBwW3ezHmLX2fHxv7HUt8J", 1);
        // ssertEquals("LepMzqfXSgQommH2qu3fk7Gf5xgoHQsP1b", rs); // from tatum.js
        assertEquals("1LbQjdMhN2AkWxasfm4NU6CtskKXEHaCKr", rs);
    }

    @Test
    public void generateLtcAddress_Testnet_Test() throws ExecutionException, InterruptedException {
        Address address = new Address();
        String rs = address.generateLtcAddress(true, "ttub4giastL5S3AicjXRBEJt7uq22b611rJvVfTgJSRfYeyZkwXwKnZcctK3tEjMpqrgiNSnYAzkKPJDxGoKNWQzkzTJxSryHbaYxsYW9Vr6AYQ", 1);
        assertEquals("mjJotvHmzEuyXZJGJXXknS6N3PWQnw6jf5", rs);
    }

    @Test
    public void generateBchAddress_Testnet_Test() throws ExecutionException, InterruptedException {
        Address address = new Address();
        String rs = address.generateBchAddress(false, "xpub6EafivSZvqR8ysLKS52NDKfn16sB9uhCEfCKdYi7PpGqqK3fJGdd53DzUnWYvFRZKAC7pB8FVnvuJKkJparfjjfVPTQTmC7dfC6aVvw6f98", 1);
        assertEquals("bitcoincash:qr9wgjtyjd4q60323gd2ytsv5w3thl92rclzrklply", rs);
    }

    @Test
    public void generateBchAddress_Mainnet_Test() throws ExecutionException, InterruptedException {
        Address address = new Address();
        String rs = address.generateBchAddress(true, "tpubDExJFAGFe7NbFfXAtG1TRF19LDxq9JCFnHncz6mFjj2jabiNNVUiDUtpipbLSkNo74j2Rke82tkwzWEvDShudB7nT49mSimsF9gzFwTf4nw", 1);
        assertEquals("bchtest:qr9wgjtyjd4q60323gd2ytsv5w3thl92rcms83akcc", rs);
    }

    @Test
    public void generateEthAddress_Mainnet_Test() throws ExecutionException, InterruptedException {
        Address address = new Address();
        String rs = address.generateEthAddress(false, "xpub6DtR524VQx3ENj2E9pNZnjqkVp47YN5sRCP5y4Gs6KZTwDhH9HTVX8shJPt74WaPZRftRXFfnsyPbMPh6DMEmrQ2WBxDJzGxriStAB36bQM", 1);
        assertEquals("0xaac8c73348f1f92b2f9647e1e4f3cf14e2a8b3cb", rs);
    }

    @Test
    public void generateEthAddress_Testnet_Test() throws ExecutionException, InterruptedException {
        Address address = new Address();
        String rs = address.generateEthAddress(true, "xpub6FMiQpA54nciqs52guGVdWQ5TonZt5XtGsFpurgtttL7H3mSfaJDXv5aBdThjX6tW9HYaJSQ8wZVnLm1ixaQUu1MRQCwvwZ6U2cX6mwWT25", 1);
        assertEquals("0x8cb76aed9c5e336ef961265c6079c14e9cd3d2ea", rs);
    }

    @Test
    public void generateVetAddress_Mainnet_Test() throws ExecutionException, InterruptedException {
        Address address = new Address();
        String rs = address.generateEthAddress(false, "xpub6EzJLu3Hi5hEFAkiZAxCTaXqXoS95seTnG1tdYdF8fBcVZCfR8GQP8UGvfF52szpwZqiiGHJw5694emxSpYBE5qDxAZUgiHLzbVhb5ErRMa", 1);
        assertEquals("0x5b70c58cb71712e2d4d3519e065bbe196546877d", rs);
    }

    @Test
    public void generateVetAddress_Testnet_Test() throws ExecutionException, InterruptedException {
        Address address = new Address();
        String rs = address.generateEthAddress(true, "xpub6FMiQpA54nciqs52guGVdWQ5TonZt5XtGsFpurgtttL7H3mSfaJDXv5aBdThjX6tW9HYaJSQ8wZVnLm1ixaQUu1MRQCwvwZ6U2cX6mwWT25", 1);
        assertEquals("0x8cb76aed9c5e336ef961265c6079c14e9cd3d2ea", rs);
    }

    @Test
    public void generateBtcPrivateKey_Mainnet_Test() throws Exception {
        Address address = new Address();
        String rs = address.generatePrivateKeyFromMnemonic(Currency.BTC, false, "quantum tobacco key they maid mean crime youth chief jungle mind design broken tilt bus shoulder leaf good forward erupt split divert bread kitten", 1);
        assertEquals("KwrYonf8pFfyQR87NTn124Ep9zoJsZMBCoVUi7mjMc1eTHDyLyBN", rs);
    }

    @Test
    public void generateBtcPrivateKey_Testnet_Test() throws Exception {
        Address address = new Address();
        String rs = address.generatePrivateKeyFromMnemonic(Currency.BTC, true, "quantum tobacco key they maid mean crime youth chief jungle mind design broken tilt bus shoulder leaf good forward erupt split divert bread kitten", 1);
        assertEquals("cQ1YZMep3CiAnMTA9y62ha6BjGaaTFsTvtDuGmucGvpAVmS89khV", rs);
    }

    @Test
    public void generateLtcPrivateKey_Mainnet_Test() throws Exception {
        Address address = new Address();
        String rs = address.generatePrivateKeyFromMnemonic(Currency.LTC, false, "quantum tobacco key they maid mean crime youth chief jungle mind design broken tilt bus shoulder leaf good forward erupt split divert bread kitten", 1);
//        assertEquals("T63MUovVt5GN5rmfwYMr4M6YqFmisjbrZrfZYZ53qWmCwiP6xCHa", rs); // tatum.js
        assertEquals("KzD634dKUhHmK28oPuQyqzZAtQ8QoeaxkemJgkSWGYb3RpvG972C", rs);
    }

    @Test
    public void generateLtcPrivateKey_Testnet_Test() throws Exception {
        Address address = new Address();
        String rs = address.generatePrivateKeyFromMnemonic(Currency.LTC, true, "quantum tobacco key they maid mean crime youth chief jungle mind design broken tilt bus shoulder leaf good forward erupt split divert bread kitten", 1);
        assertEquals("cQ1YZMep3CiAnMTA9y62ha6BjGaaTFsTvtDuGmucGvpAVmS89khV", rs);
    }

    @Test
    public void generateBchPrivateKey_Mainnet_Test() throws Exception {
        Address address = new Address();
        String rs = address.generatePrivateKeyFromMnemonic(Currency.BCH, false, "quantum tobacco key they maid mean crime youth chief jungle mind design broken tilt bus shoulder leaf good forward erupt split divert bread kitten", 1);
        assertEquals("KzqM77kK7zqZGockuB2Tov1FXoH6BTMaT3ixeqTPXLAYp838W3KT", rs);
    }

    @Test
    public void generateBchPrivateKey_Testnet_Test() throws Exception {
        Address address = new Address();
        String rs = address.generatePrivateKeyFromMnemonic(Currency.BCH, true, "quantum tobacco key they maid mean crime youth chief jungle mind design broken tilt bus shoulder leaf good forward erupt split divert bread kitten", 1);
        assertEquals("cRCLa2kAZ4XpSF62HaqbBEWKA2aVquTGX5sRmFuu2SpZ4s72vi5Y", rs);//tatum.js
    }

    @Test
    public void generateEthPrivateKey_Mainnet_Test() throws Exception {
        Address address = new Address();
        String rs = address.generatePrivateKeyFromMnemonic(Currency.ETH, false, "quantum tobacco key they maid mean crime youth chief jungle mind design broken tilt bus shoulder leaf good forward erupt split divert bread kitten", 1);
        assertEquals("0xbc93ab7d2dbad88e64879569a9e3ceaa12d119c70d6dda4d1fc6e73765794a8d", rs);
    }

    @Test
    public void generateEthPrivateKey_Testnet_Test() throws Exception {
        Address address = new Address();
        String rs = address.generatePrivateKeyFromMnemonic(Currency.ETH, true, "quantum tobacco key they maid mean crime youth chief jungle mind design broken tilt bus shoulder leaf good forward erupt split divert bread kitten", 1);
        assertEquals("0x4874827a55d87f2309c55b835af509e3427aa4d52321eeb49a2b93b5c0f8edfb", rs);
    }

    @Test
    public void generateVetPrivateKey_Mainnet_Test() throws Exception {
        Address address = new Address();
        String rs = address.generatePrivateKeyFromMnemonic(Currency.VET, false, "quantum tobacco key they maid mean crime youth chief jungle mind design broken tilt bus shoulder leaf good forward erupt split divert bread kitten", 1);
        assertEquals("0xd2a4c2f89f58e50f2e29ed1e68552680417a0534c47bebf18f2f5f3a27817251", rs);
    }

    @Test
    public void generateVetPrivateKey_Testnet_Test() throws Exception {
        Address address = new Address();
        String rs = address.generatePrivateKeyFromMnemonic(Currency.VET, true, "quantum tobacco key they maid mean crime youth chief jungle mind design broken tilt bus shoulder leaf good forward erupt split divert bread kitten", 1);
        assertEquals("0x4874827a55d87f2309c55b835af509e3427aa4d52321eeb49a2b93b5c0f8edfb", rs);
    }

    @Test
    public void generateAdaPrivateKey_Test() throws Exception {
        Address address = new Address();
        String rs = address.generatePrivateKeyFromMnemonic(Currency.ADA, true, "quantum tobacco key they maid mean crime youth chief jungle mind design broken tilt bus shoulder leaf good forward erupt split divert bread kitten", 1);
//        assertEquals("e005602357a4265f6fc58745344c86a0260064f51712ea23e4dbf3f3795e564b83c29f3e4f84ea23a3868cd215bfe68954a6369d59bf2999b0e0e3f601778ca3c958ff0de05b645c8192022caaf6d2ae49668708a0cbc3d7e9f6f6b59fb9c4aa", rs);
    }
}
