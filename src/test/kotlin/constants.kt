import io.tatum.wallet.Wallet

// Valid mnemonics, seeds, xprvs, xpubs
const val VALID_MNEMONIC_15 =
    "awesome cost donate hub tank infant sheriff member awake spice all warrior pen column east"
const val SEED_OF_MNEM_15 =
    "2f842de3b551574e675845db1aecb23f30fe4682fb2a6a56b3400ce585e8f667e612d05ad20d7440717532abe03e7c35d34aa6b06d8e7f273b5a146bfe4610eb"
val SEED_IN_BYTES_OF_MNEM_15 =
    byteArrayOf(47, -124, 45, -29, -75, 81, 87, 78, 103, 88, 69, -37, 26, -20, -78, 63, 48, -2, 70, -126, -5, 42, 106, 86, -77, 64, 12, -27, -123, -24, -10, 103, -26, 18, -48, 90, -46, 13, 116, 64, 113, 117, 50, -85, -32, 62, 124, 53, -45, 74, -90, -80, 109, -114, 127, 39, 59, 90, 20, 107, -2, 70, 16, -21)
const val HEX_SEED_OF_MNEM_15 =
    "0x2f842de3b551574e675845db1aecb23f30fe4682fb2a6a56b3400ce585e8f667e612d05ad20d7440717532abe03e7c35d34aa6b06d8e7f273b5a146bfe4610eb"

const val TESTNET_PRIVATE_KEY_OF_MNEM_15_AT_0 = "0x0cfa57cd6d2c2a950727f01f4131049b8cc43db20cd7fd4609ca183998f64d6e"
const val TESTNET_PRIVATE_KEY_OF_MNEM_15_AT_1 = "0xdcd69daddaeb6d2c3123dce9828cebf5de849387ced42e341ee9379451ebb85a"
const val TESTNET_PRIVATE_KEY_OF_MNEM_15_AT_2 = "0xc3e392381e1f1a5bf7723b520a6c5175c734829fce1e94693f2be5a2a0d20680"
const val TESTNET_PRIVATE_KEY_OF_MNEM_15_AT_3 = "0x0e19a6bce13efb949821878a85408f169837e927fad847772970d44502dfad06"
const val TESTNET_PRIVATE_KEY_OF_MNEM_15_AT_4 = "0xcb2d3fde6086c9c8920feb20ef36f6fff5686e30338e6ed3040884a6044dbd3e"

const val MAINNET_PRIVATE_KEY_OF_MNEM_15_AT_0 = "0x0038afde80383cb8baa260601c3a7d33d6339e4517bb44e72e62ed8194463d57"
const val MAINNET_PRIVATE_KEY_OF_MNEM_15_AT_1 = "0x73b5d882f6c936a475fb910630fdca77ec337a44398dc6c3b9f1356d19dda0b5"
const val MAINNET_PRIVATE_KEY_OF_MNEM_15_AT_2 = "0xd3873bc7db8487d091fbcb1afd67482f17b6b9b38de5bbbdf894849c2474f162"
const val MAINNET_PRIVATE_KEY_OF_MNEM_15_AT_3 = "0x228bef5d8c71775de2b94c0a013f423729c13250b4087a1aa3d1177ab7ced5a0"
const val MAINNET_PRIVATE_KEY_OF_MNEM_15_AT_4 = "0xd8bbf0b174b4b153f370846a680728ff579e1253a9c6debec0d8a1ac4c54fa4e"

const val TESTNET_XPRV_OF_MNEM_15 =
    "xprv9zYsejMX94AZ2DNSdubeqksRjHEFYuvxtVZuPaiUPjqfzJb3V3ietEKCWYcqd18Ji4JzVThFf1CZyBYo6VEr1NXrizXHmwVATWycn1tni1W"
const val TESTNET_XPUB_OF_MNEM_15 =
    "xpub6DYE4EtQyRirEhSujw8fCtpAHK4jxNepFiVWBy85x5Nes6vC2b2uS2dgMo22pULAHt7k79DDJnAa3DhXQnKEvc8j1s45cMC42Co7pXpnVQd"

const val MAINNET_XPRV_OF_MNEM_15 =
    "xprvA1TWXs9qLQkdAeuWoTKxMzRsLfh1CFwmjBsQ6R1meHe1aTSkZQC2HGZ2QncdH7AaaQCdnNLTujn3dVj3QFsUWn3Lob7A73xvzWQMVUFNSrh"
const val MAINNET_XPUB_OF_MNEM_15 =
    "xpub6ESrwNgjAnJvP8yyuUrxj8NbthXVbifd6QnztoRPCdAzTFmu6wWGq4sWG3GrYAouuWc2EejAVCAPbm5hyLxdHVcoGQqYV3CNU5s3bR1ZnkG"

// Wallets
val TESTNET_ETH_WALLET_OF_MNEM_15 = Wallet(
    xpub = TESTNET_XPUB_OF_MNEM_15,
    xprv = TESTNET_XPRV_OF_MNEM_15,
    mnemonic = VALID_MNEMONIC_15,
)

val MAINNET_ETH_WALLET_OF_MNEM_15 = Wallet(
    xpub = MAINNET_XPUB_OF_MNEM_15,
    xprv = MAINNET_XPRV_OF_MNEM_15,
    mnemonic = VALID_MNEMONIC_15,
)

// Addresses
const val TESTNET_ETH_ADDRESS_OF_MNEM_15_AT_0 = "0x7bfa6d5095de8581ce4275dfb19ce4ccf24c5add"
const val TESTNET_ETH_ADDRESS_OF_MNEM_15_AT_1 = "0xbd53bf6573321d159453f09e71b053d5dbf5ce31"
const val TESTNET_ETH_ADDRESS_OF_MNEM_15_AT_2 = "0x84161e72210d2e343727e5976eba8f7a1f17facf"
const val TESTNET_ETH_ADDRESS_OF_MNEM_15_AT_3 = "0x8598e67bd526e677b256b12eec7e65aed50ce6fe"
const val TESTNET_ETH_ADDRESS_OF_MNEM_15_AT_4 = "0xa0fe6f2a77a7b510cfd83797fa321c18ece97d45"

const val MAINNET_ETH_ADDRESS_OF_MNEM_15_AT_0 = "0x4a81c9a018d56b3fd9c1f2937e96fea0e26dff56"
const val MAINNET_ETH_ADDRESS_OF_MNEM_15_AT_1 = "0x483ba3b02daa65361674107aa5e9371fe2be9b19"
const val MAINNET_ETH_ADDRESS_OF_MNEM_15_AT_2 = "0xafdf9fa1c7b1a1a50080bf40b9ccef1570eb479e"
const val MAINNET_ETH_ADDRESS_OF_MNEM_15_AT_3 = "0x1013232942846e43f33b3f51e728c335db73a3a2"
const val MAINNET_ETH_ADDRESS_OF_MNEM_15_AT_4 = "0x366e5d8862b4541b7802288d911ff98fb82862c5"

const val VALID_MNEMONIC_24 =
    "exit solve dizzy response farm nasty rug pilot deliver laundry teach problem page stand permit quantum slot lunch comic fog bike moon entry wire"

// Invalid values
const val INVALID_MNEMONIC_STARTS_WITH_WHITESPACE =
    " exit solve dizzy response farm nasty rug pilot deliver laundry teach problem page stand permit quantum slot lunch comic fog bike moon entry wire"
const val INVALID_MNEMONIC_ENDS_WITH_WHITESPACE =
    "exit solve dizzy response farm nasty rug pilot deliver laundry teach problem page stand permit quantum slot lunch comic fog bike moon entry wire "
const val INVALID_MNEMONIC_CONTAINS_NON_ENGLISH_WORD =
    "exit solve dizzy response farm nasty rug pilot deliver laundry teach problem page stand permit quantum slot lunch comic fog bike moon entry halusky"
const val INVALID_MNEMONIC_TOO_SHORT = "awesome cost donate hub tank infant sheriff member awake"
const val INVALID_MNEMONIC_UNSUPPORTED_LANGUAGE =
    "Nad Tatrou sa blýska hromy divo bijú Zastavme ich bratia veď sa ony stratia Slováci ožijú To Slovensko"
