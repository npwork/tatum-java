package io.tatum.bip32

import org.kethereum.bip32.model.ExtendedKey
import org.kethereum.crypto.decompressKey
import org.kethereum.crypto.toECKeyPair
import org.kethereum.model.ECKeyPair
import org.kethereum.model.PRIVATE_KEY_SIZE
import org.kethereum.model.PrivateKey
import org.kethereum.model.PublicKey
import org.komputing.kbase58.decodeBase58WithChecksum
import java.math.BigInteger
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.security.KeyException

private const val EXTENDED_KEY_SIZE: Int = 78
private const val COMPRESSED_PUBLIC_KEY_SIZE = PRIVATE_KEY_SIZE + 1
private val xprv = byteArrayOf(0x04, 0x88.toByte(), 0xAD.toByte(), 0xE4.toByte())
private val xpub = byteArrayOf(0x04, 0x88.toByte(), 0xB2.toByte(), 0x1E.toByte())

inline class XPub(val xPub: String)

/**
 * Create ExtendedKey from XPUB.
 */
fun XPub.toExtendedKey(): ExtendedKey {
    val data = xPub.decodeBase58WithChecksum()
    if (data.size != EXTENDED_KEY_SIZE) {
        throw KeyException("invalid extended key")
    }

    val buff = ByteBuffer
        .wrap(data)
        .order(ByteOrder.BIG_ENDIAN)

    val versionBytes = ByteArray(4)
    buff.get(versionBytes)

    val hasPrivate = when {
        versionBytes.contentEquals(xprv) -> true
        versionBytes.contentEquals(xpub) -> false
        else -> throw KeyException("invalid version bytes for an extended key")
    }

    val depth = buff.get()
    val parent = buff.int
    val sequence = buff.int

    val chainCode = ByteArray(PRIVATE_KEY_SIZE)
    buff.get(chainCode)

    val keyPair = if (hasPrivate) {
        buff.get() // ignore the leading 0
        val privateBytes = ByteArray(PRIVATE_KEY_SIZE)
        buff.get(privateBytes)
        PrivateKey(privateBytes).toECKeyPair()
    } else {
        val compressedPublicBytes = ByteArray(COMPRESSED_PUBLIC_KEY_SIZE)
        buff.get(compressedPublicBytes)
        val uncompressedPublicBytes = decompressKey(compressedPublicBytes)
        ECKeyPair(
            PrivateKey(BigInteger.ZERO),
            PublicKey(BigInteger(1, uncompressedPublicBytes))
        )
    }

    return ExtendedKey(
        keyPair = keyPair,
        chainCode = chainCode,
        depth = depth,
        parentFingerprint = parent,
        sequence = sequence,
        versionBytes = versionBytes,
    )
}
