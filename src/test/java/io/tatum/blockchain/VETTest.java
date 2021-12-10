package io.tatum.blockchain;

import io.tatum.model.request.EstimateGasVet;
import io.tatum.model.response.vet.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Vechain testnet
 * <p>
 * https://explore-testnet.vechain.org/blocks/0x00a66904e5f9ea388f9c4c10ef7d134d4cf714d880d4fbcb0b1d0cc03731e6e4
 * https://explore-testnet.vechain.org/accounts/0x745502e1892a97846fca30781b9232162dda43b9
 * https://explore-testnet.vechain.org/transactions/0x6c262504e9b23848400834ac805f3dd45a115095a97dde722bd845d06bc535d1
 */
public class VETTest {
    private final static VET VET = new VET();

    public static final String ADDRESS = "0x745502e1892a97846fca30781b9232162dda43b9";
    private static final String ADDRESS_NO_TX = "0x492eb8c15af354339b08ec23a500109f2c6a1570";
    private static final String ADDRESS_WRONG = "0x491eb1c11af154339b08ec23a500109f2c6a1570";

    private static final long BLOCK = 10_905_862;
    private static final String BLOCK_HASH = "0x00a66906e0b756d83b30955fa4a53675dff81d6eff670ee235519135ba1a0850";
    private static final String BLOCK_DOES_NOT_EXIST_HASH = "0x00a62906e1b756d23b32952fa4a53675dff81d6eff670ee235519135ba1a0850";

    private static final String TX = "0x6c262504e9b23848400834ac805f3dd45a115095a97dde722bd845d06bc535d1";
    private static final String TX_DOES_NOT_EXIST = "0x6c162104e1b21848401834ac805f3dd45a115095a97dde722bd845d06bc535d1";

    private static final String TX_SENDER = "0x4f6fc409e152d33843cf4982d414c1dd0879277e";

    @Test
    public void GetCurrentBlock() throws InterruptedException, ExecutionException {
        long currentBlock = VET.vetGetCurrentBlock();
        assertTrue(currentBlock > BLOCK);
    }

    @Nested
    class GetBlock {
        @Test
        public void valid() throws ExecutionException, InterruptedException {
            VetBlock expected = VetBlock.builder()
                    .number(BLOCK)
                    .id(BLOCK_HASH)
                    .size(588L)
                    .parentID("0x00a669056d3cc4e276d34c411e2a969020b91bf1423719acab0d45ba774ea446")
                    .timestamp(1639089870L)
                    .gasLimit(21000000L)
                    .beneficiary("0xb4094c25f86d628fdd571afc4077f0d0196afb48")
                    .gasUsed(52582L)
                    .totalScore(55898023L)
                    .txsRoot("0x7c1bf9e58f3f702b53f8efbfb3593b04a26713aec0f7f067ef39b57dff512675")
                    .txsFeatures(1L)
                    .stateRoot("0x2d7ed3e2df513dcd9c61773c166cdbed02281e7dfd028478a451130196c1c4cd")
                    .receiptsRoot("0x87c70ba39333c7df1a60d2cfe99b8ee5674265de94eb0f3aab10afdf43d5aeab")
                    .signer("0xd7b5750dbfae7d2aabc16b0ed16fbf2c048067ca")
                    .transactions(new String[]{TX})
                    .build();

            VetBlock block = VET.vetGetBlock(BLOCK_HASH);

            assertEquals(expected, block);
        }

        @Test
        public void notFound() throws ExecutionException, InterruptedException {
            VetBlock block = VET.vetGetBlock(BLOCK_DOES_NOT_EXIST_HASH);
            assertNull(block);
        }
    }

    @Nested
    class GetAccountBalance {
        @Test
        public void valid() throws ExecutionException, InterruptedException {
            BigDecimal balance = VET.vetGetAccountBalance(ADDRESS);
            assertEquals(BigDecimal.valueOf(500), balance);
        }

        @Test
        public void emptyAccount() throws ExecutionException, InterruptedException {
            BigDecimal balance = VET.vetGetAccountBalance(ADDRESS_NO_TX);
            assertEquals(BigDecimal.ZERO, balance);
        }

        @Test
        public void addressNotFound() throws ExecutionException, InterruptedException {
            BigDecimal balance = VET.vetGetAccountBalance(ADDRESS_WRONG);
            assertEquals(BigDecimal.ZERO, balance);
        }
    }

    @Nested
    class GetAccountEnergy {
        @Test
        public void valid() throws ExecutionException, InterruptedException {
            BigDecimal energy = VET.vetGetAccountEnergy(ADDRESS);
            assertTrue(energy.compareTo(BigDecimal.valueOf(50)) > 0);
        }

        @Test
        public void emptyAccount() throws ExecutionException, InterruptedException {
            BigDecimal energy = VET.vetGetAccountEnergy(ADDRESS_NO_TX);
            assertEquals(BigDecimal.ZERO, energy);
        }

        @Test
        public void addressNotFound() throws ExecutionException, InterruptedException {
            BigDecimal energy = VET.vetGetAccountEnergy(ADDRESS_WRONG);
            assertEquals(BigDecimal.ZERO, energy);
        }
    }

    @Nested
    class GetTransaction {
        @Test
        public void valid() throws ExecutionException, InterruptedException {
            VetTx expected = VetTx.builder()
                    .id(TX)
                    .chainTag("0x27")
                    .blockRef("0x00a66904e5f9ea38")
                    .expiration(32L)
                    .gasPriceCoef(BigDecimal.valueOf(255))
                    .gas(BigDecimal.valueOf(100000))
                    .origin(TX_SENDER)
                    .nonce("0x702e531f044b6af4")
                    .size(BigDecimal.valueOf(225))
                    .clauses(new VetTxClauses[]{
                            new VetTxClauses(
                                    ADDRESS,
                                    new BigDecimal(500).scaleByPowerOfTen(18).setScale(0, RoundingMode.HALF_EVEN),
                                    "0x"
                            ),
                            new VetTxClauses(
                                    "0x0000000000000000000000000000456e65726779",
                                    BigDecimal.ZERO,
                                    "0xa9059cbb000000000000000000000000745502e1892a97846fca30781b9232162dda43b9000000000000000000000000000000000000000000000002b5e3af16b1880000"
                            )
                    })
                    .meta(new VetTxMeta(BLOCK_HASH, BLOCK, 1639089870L))
                    .blockNumber(BLOCK)
                    .build();

            VetTx tx = VET.vetGetTransaction(TX);

            assertEquals(expected, tx);
        }

        @Test
        public void notFound() throws ExecutionException, InterruptedException {
            VetTx tx = VET.vetGetTransaction(TX_DOES_NOT_EXIST);
            assertNull(tx);
        }
    }

    // @TODO add estimategas
    @Disabled
    @Test
    public void vetEstimateGasTest() throws InterruptedException, ExecutionException, IOException {
        VET vet = new VET();
        EstimateGasVet body = new EstimateGasVet();
        body.setData("0x0d7A1a48a8996Dd51F94d9e9118cCb028562B955");
        body.setFrom("123456789012345678901234567890123456789012345678901234567890123456");
        body.setTo("0x0d7A1a48a8996Dd51F94d9e9118cCb028562B955");
        body.setNonce(new BigInteger("100"));
        body.setValue("123321");
        VetEstimateGas vetEstimateGas = vet.vetEstimateGas(body);
    }

    @Nested
    class GetTransactionReceipt {
        @Test
        public void valid() throws ExecutionException, InterruptedException {
            VetTxReceipt expected = VetTxReceipt.builder()
                    .gasUsed(BigDecimal.valueOf(52582))
                    .gasPayer(TX_SENDER)
                    .paid("0xe982d0217978000")
                    .reward("0x460da4d6d7a4000")
                    .reverted(false)
                    .meta(new VetTxReceiptMeta(BLOCK_HASH, BLOCK, 1639089870L, TX, TX_SENDER))
                    .outputs(new VetTxReceiptOutputs[]{
                            new VetTxReceiptOutputs(new Event[0], new VetTxReceiptTransfers[]{
                                    new VetTxReceiptTransfers(TX_SENDER, ADDRESS, "0x1b1ae4d6e2ef500000"),
                            }),
                            new VetTxReceiptOutputs(
                                    new Event[]{new Event(new String[]{"0xddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef", "0x0000000000000000000000004f6fc409e152d33843cf4982d414c1dd0879277e", "0x000000000000000000000000745502e1892a97846fca30781b9232162dda43b9"})},
                                    new VetTxReceiptTransfers[0]
                            )
                    })
                    .blockNumber(BLOCK)
                    .blockHash(BLOCK_HASH)
                    .transactionHash(TX)
                    .status("0x1")
                    .build();

            VetTxReceipt txReceipt = VET.vetGetTransactionReceipt(TX);

            assertEquals(expected, txReceipt);
        }

        @Test
        public void notFound() throws ExecutionException, InterruptedException {
            VetTxReceipt txReceipt = VET.vetGetTransactionReceipt(TX_DOES_NOT_EXIST);
            assertNull(txReceipt);
        }
    }
}
