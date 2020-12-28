package io.tatum.wallet;

import com.google.common.collect.ImmutableList;
import io.tatum.model.request.Currency;
import org.bitcoinj.core.Base58;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDUtils;
import org.bitcoinj.crypto.LazyECPoint;
import org.bouncycastle.math.ec.ECPoint;
import org.junit.Test;
import org.web3j.crypto.Bip32ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

import javax.annotation.Nullable;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutionException;

import static com.google.common.base.Preconditions.checkArgument;
import static org.junit.Assert.assertEquals;
import static org.web3j.crypto.Hash.sha256;

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
        String rs = address.generateEthAddress(false, "xpub68NZiKmJWnxxS6aaHmn81bvJeTESw724CRDs6HbuccFQN9Ku14VQrADWgqbhhTHBaohPX4CjNLf9fq9MYo6oDaPPLPxSb7gwQN3ih19Zm4Y", 1);
        assertEquals("0x31f67640a3ef168e5885fbe46e99c0096cde5cb4", rs);
    }

    /** Deserialize a base-58-encoded HD Key with no parent */
    public static Bip32ECKeyPair deserializeB58(String base58, NetworkParameters params) {
        return deserializeB58(null, base58, params);
    }

    public static Bip32ECKeyPair deserializeB58(@Nullable DeterministicKey parent, String base58, NetworkParameters params) {
        return deserialize(params, Base58.decodeChecked(base58), parent);
    }

    /**
     * Deserialize an HD Key.
     * @param parent The parent node in the given key's deterministic hierarchy.
     */
    public static Bip32ECKeyPair deserialize(NetworkParameters params, byte[] serializedKey, @Nullable DeterministicKey parent) {
        ByteBuffer buffer = ByteBuffer.wrap(serializedKey);
        int header = buffer.getInt();
        final boolean pub = header == params.getBip32HeaderP2PKHpub() || header == params.getBip32HeaderP2WPKHpub();
        final boolean priv = header == params.getBip32HeaderP2PKHpriv() || header == params.getBip32HeaderP2WPKHpriv();
        if (!(pub || priv))
            throw new IllegalArgumentException("Unknown header bytes: " + toBase58(serializedKey).substring(0, 4));
        int depth = buffer.get() & 0xFF; // convert signed byte to positive int since depth cannot be negative
        final int parentFingerprint = buffer.getInt();
        final int i = buffer.getInt();
        final ChildNumber childNumber = new ChildNumber(i);
        ImmutableList<ChildNumber> path;
        if (parent != null) {
            if (parentFingerprint == 0)
                throw new IllegalArgumentException("Parent was provided but this key doesn't have one");
            if (parent.getFingerprint() != parentFingerprint)
                throw new IllegalArgumentException("Parent fingerprints don't match");
            path = HDUtils.append(parent.getPath(), childNumber);
            if (path.size() != depth)
                throw new IllegalArgumentException("Depth does not match");
        } else {
            if (depth >= 1)
                // We have been given a key that is not a root key, yet we lack the object representing the parent.
                // This can happen when deserializing an account key for a watching wallet.  In this case, we assume that
                // the client wants to conceal the key's position in the hierarchy.  The path is truncated at the
                // parent's node.
                path = ImmutableList.of(childNumber);
            else path = ImmutableList.of();
        }
        byte[] chainCode = new byte[32];
        buffer.get(chainCode);
        byte[] data = new byte[33];
        buffer.get(data);


        checkArgument(!buffer.hasRemaining(), "Found unexpected data in key");
        if (pub) {
//            return new DeterministicKey(path, chainCode, new LazyECPoint(ECKey.CURVE.getCurve(), data), parent, depth, parentFingerprint);
//            return new DeterministicKey(path, chainCode, new LazyECPoint(ECKey.CURVE.getCurve(), data), parent, depth, parentFingerprint);
            return new Bip32ECKeyPair(
                    null, Sign.publicFromPoint(data), i, chainCode, null);
        } else {
//            return new DeterministicKey(path, chainCode, new BigInteger(1, data), parent, depth, parentFingerprint);

            return new Bip32ECKeyPair(
                    null, new BigInteger(1, data), i, chainCode, null);
        }
    }

    static String toBase58(byte[] ser) {
        return Base58.encode(addChecksum2(ser));
    }

    static byte[] addChecksum(byte[] input) {
        int inputLength = input.length;
        byte[] checksummed = new byte[inputLength + 4];
        System.arraycopy(input, 0, checksummed, 0, inputLength);
        byte[] checksum = hashTwice(input);
        System.arraycopy(checksum, 0, checksummed, inputLength, 4);
        return checksummed;
    }

    static byte[] addChecksum2(byte[] input) {
        int inputLength = input.length;
        byte[] checksummed = new byte[inputLength + 4];
        System.arraycopy(input, 0, checksummed, 0, inputLength);
        byte[] checksum = Sha256Hash.hashTwice(input);
        System.arraycopy(checksum, 0, checksummed, inputLength, 4);
        return checksummed;
    }

    private static byte[] hashTwice(byte[] input) {
        return sha256(sha256(input));
    }

    @Test
    public void testGetAddressString() throws ExecutionException, InterruptedException {
        String PUBLIC_KEY_STRING = "0x506bc1dc099358e5137292f4efdd57e400f29ba5132aa5d12b18dac1c1f6aaba645c0b7b58158babbfa6c6cd5a48aa7340a8749176b120e8516216787a13dc76";

        String xpub = Base58.encode(addChecksum("0488B21E506bc1dc099358e5137292f4efdd57e400f29ba5132aa5d12b18dac1c1f6aaba645c0b7b58158babbfa6c6cd5a48aa7340a8749176b120e8516216787a13dc76".getBytes()));
        System.out.println(xpub);
        String ADDRESS = "0xef678007d18427e6022059dbc264f27507cd1ffc";
        String ADDRESS_NO_PREFIX = Numeric.cleanHexPrefix(ADDRESS);

//        assertEquals(Keys.getAddress(PUBLIC_KEY_STRING), (ADDRESS_NO_PREFIX));

        Address address = new Address();
        String rs = address.generateEthAddress(false, xpub, 1);
        assertEquals(ADDRESS, rs);
    }

    private static byte[] serialize(Bip32ECKeyPair pair, int header, boolean pub) {
        ByteBuffer ser = ByteBuffer.allocate(78);
        ser.putInt(header);
        ser.put((byte) pair.getDepth());
        ser.putInt(pair.getParentFingerprint());
        ser.putInt(pair.getChildNumber());
        ser.put(pair.getChainCode());
        ser.put(pub ? pair.getPublicKeyPoint().getEncoded(true) : pair.getPrivateKeyBytes33());
        return ser.array();
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
