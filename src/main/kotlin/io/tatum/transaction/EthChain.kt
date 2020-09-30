package io.tatum.transaction

import org.kethereum.model.ChainId

enum class EthChain(val id: ChainId) {
    ETHEREUM_MAINNET(ChainId(1)),
    EXPANSE_MAINNET(ChainId(2)),
    ROPSTEN(ChainId(3)),
    RINKEBY(ChainId(4)),
    GOERLI(ChainId(5)),
    KOVAN(ChainId(42)),
    ROOTSTOCK_MAINNET(ChainId(30)),
    ROOTSTOCK_TESTNET(ChainId(31)),
    ETHEREUM_CLASSIC_MAINNET(ChainId(61)),
    ETHEREUM_CLASSIC_TESTNET(ChainId(62)),
}
