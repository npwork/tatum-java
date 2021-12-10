package io.tatum.blockchain;

import io.tatum.model.response.xlm.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.jupiter.api.Assertions.*;


/**
 * Stellar testnet
 * http://testnet.stellarchain.io/address/GDYTQXSTHRCV7GVGMMSA6ZXGNGZSSXED4RORYVKAICH5JVO5V7FGGZJW
 * http://testnet.stellarchain.io/tx/b7c7b6d69444faca7a130a8413281704f11decc8fbc45a11bb36527bf351522f
 * http://testnet.stellarchain.io/ledger/1405538
 */
public class XLMTest {
    private final static XLM XLM = new XLM();

    private final static long LEDGER_INDEX = 1405538L;
    private final static String LEDGER_HASH = "b218268fb72ab9c5fda457b5d6eba4e55361d8187634ea095b21b1927009126e";
    public final static String ADDRESS = "GDYTQXSTHRCV7GVGMMSA6ZXGNGZSSXED4RORYVKAICH5JVO5V7FGGZJW";
    private final static String ADDRESS_WITHOUT_TXS = "GDAV35IHODCQFP22QISJIID3VUSFL57GDX4DP5676TYY6DAMO4AUMBOU";
    private final static String ADDRESS_DOES_NOT_EXIST = "GDYTQ1ST1RC17G1G1M1A61XGNGZSSXED4RORYVKAICH5JVO5V7FGGZJW";
    private final static String TX = "b7c7b6d69444faca7a130a8413281704f11decc8fbc45a11bb36527bf351522f";
    private final static String TX_DOES_NOT_EXIST = "b7c7b6d614441ac17a13018411281704f11decc8fbc45a11bb36527bf351522f";

    @Test
    public void GetCurrentLedger() throws InterruptedException, ExecutionException {
        XlmInfo current = XLM.xlmGetCurrentLedger();
        assertTrue(current.getSequence() > LEDGER_INDEX);
    }

    @Test
    public void GetFee() throws ExecutionException, InterruptedException {
        long fee = XLM.xlmGetFee();
        assertTrue(fee > 0);
    }

    @Nested
    class GetAccountInfo {
        @Test
        public void valid() throws InterruptedException, ExecutionException {
            Account expected = Account.builder()
                    .id(ADDRESS)
                    .accountId(ADDRESS)
                    .sequence(6036739743285248L)
                    .subentryCount(0L)
                    .lastModifiedLedger(LEDGER_INDEX)
                    .lastModifiedTime("2021-12-09T21:08:24Z")
                    .thresholds(XlmAccountThresholds.builder()
                            .lowThreshold(0L)
                            .medThreshold(0L)
                            .highThreshold(0L)
                            .build())
                    .flags(XlmAccountFlags.builder()
                            .authRequired(false)
                            .authRevocable(false)
                            .authImmutable(false)
                            .authClawbackEnabled(false)
                            .build())
                    .balances(Collections.singletonList(
                            XlmBalance.builder()
                                    .balance(BigDecimal.valueOf(10000L).setScale(7))
                                    .buyingLiabilities(BigDecimal.ZERO.setScale(7))
                                    .sellingLiabilities(BigDecimal.ZERO.setScale(7))
                                    .assetType("native")
                                    .build()
                    ))
                    .signers(Collections.singletonList(
                            XlmSigner.builder()
                                    .weight(1L)
                                    .key(ADDRESS)
                                    .type("ed25519_public_key")
                                    .build()
                    ))
                    .numSponsored(0L)
                    .numSponsoring(0L)
                    .pagingToken(ADDRESS)
                    .build();

            Account account = XLM.xlmGetAccountInfo(ADDRESS);

            assertEquals(expected, account);
        }

        @Test
        public void notFound() throws InterruptedException, ExecutionException {
            Account account = XLM.xlmGetAccountInfo(ADDRESS_DOES_NOT_EXIST);
            assertNull(account);
        }
    }

    @Nested
    class GetLedger {
        @Test
        public void valid() throws ExecutionException, InterruptedException {
            XlmLedger expected = XlmLedger.builder()
                    .id(LEDGER_HASH)
                    .hash(LEDGER_HASH)
                    .pagingToken(6036739743285248L)
                    .prevHash("b76393771e1b8ba4832fe5dfa71a4958706134cafe8c34b76f03a2143a0467d7")
                    .sequence(LEDGER_INDEX)
                    .successfulTransactionCount(4L)
                    .failedTransactionCount(0L)
                    .operationCount(4L)
                    .txSetOperationCount(4L)
                    .closedAt("2021-12-09T21:08:24Z")
                    .totalCoins(BigDecimal.valueOf(100000000000L).setScale(7))
                    .feePool(BigDecimal.valueOf(10339.8744025))
                    .baseFeeInStroops(BigDecimal.valueOf(100L))
                    .baseReserveInStroops(BigDecimal.valueOf(5_000_000L))
                    .maxTxSetSize(BigDecimal.valueOf(105L))
                    .protocolVersion(18L)
                    .build();

            XlmLedger ledger = XLM.xlmGetLedger(LEDGER_INDEX);

            assertNotNull(ledger.getHeaderXdr());
            ledger.setHeaderXdr(null);

            assertEquals(expected, ledger);
        }

        @Test
        public void notFound() throws ExecutionException, InterruptedException {
            XlmLedger ledger = XLM.xlmGetLedger(Long.MAX_VALUE);
            assertNull(ledger);
        }
    }

    @Nested
    class GetLedgerTx {
        @Test
        public void valid() throws ExecutionException, InterruptedException {
            XlmTransaction expectedFirst = getXlmTransaction();

            XlmTransaction[] xlmLedgerTxes = XLM.xlmGetLedgerTx(LEDGER_INDEX);
            assertEquals(4, xlmLedgerTxes.length);

            XlmTransaction first = xlmLedgerTxes[0];

            verifyAndNullFields(first);
            assertEquals(expectedFirst, first);
        }

        @Test
        public void notFound() throws ExecutionException, InterruptedException {
            XlmTransaction[] xlmLedgerTxes = XLM.xlmGetLedgerTx(Long.MAX_VALUE);
            assertNull(xlmLedgerTxes);
        }
    }

    @Nested
    class GetTransaction {
        @Test
        public void valid() throws ExecutionException, InterruptedException {
            XlmTransaction expected = getXlmTransaction();

            XlmTransaction tx = XLM.xlmGetTransaction(TX);

            verifyAndNullFields(tx);
            assertEquals(expected, tx);
        }

        @Test
        public void notFound() throws ExecutionException, InterruptedException {
            XlmTransaction tx = XLM.xlmGetTransaction(TX_DOES_NOT_EXIST);
            assertNull(tx);
        }
    }

    @Nested
    class GetTransactionsForAccount {
        @Test
        public void valid() throws ExecutionException, InterruptedException {
            XlmTransaction expectedFirst = getXlmTransaction();

            XlmTransaction[] txs = XLM.xlmGetAccountTransactions(ADDRESS);
            assertEquals(1, txs.length);

            XlmTransaction first = txs[0];

            verifyAndNullFields(first);
            assertEquals(expectedFirst, first);
        }

        @Test
        public void withoutTx() throws ExecutionException, InterruptedException {
            XlmTransaction[] txs = XLM.xlmGetAccountTransactions(ADDRESS_WITHOUT_TXS);
            assertNull(txs);
        }

        @Test
        public void notFound() throws ExecutionException, InterruptedException {
            XlmTransaction[] txs = XLM.xlmGetAccountTransactions(ADDRESS_DOES_NOT_EXIST);
            assertNull(txs);
        }
    }

    private void verifyAndNullFields(XlmTransaction tx) {
        assertEquals(2, tx.getSignatures().size());

        assertThat(tx, hasProperty("envelopeXdr"));
        assertThat(tx, hasProperty("resultXdr"));
        assertThat(tx, hasProperty("resultMetaXdr"));
        assertThat(tx, hasProperty("feeMetaXdr"));

        tx.setEnvelopeXdr(null);
        tx.setResultXdr(null);
        tx.setResultMetaXdr(null);
        tx.setFeeMetaXdr(null);
        tx.setSignatures(null);
    }

    private XlmTransaction getXlmTransaction() {
        return XlmTransaction.builder()
                .id(TX)
                .hash(TX)
                .pagingToken("6036739743301632")
                .successful(true)
                .createdAt("2021-12-09T21:08:24Z")
                .sourceAccount("GAKUNBGP2IJZSTE3SVWRAEAGUCDFUZW6DHIFWQFRONTEXXPSUYHI4ADM")
                .sourceAccountSequence(4521934842757167L)
                .feeAccount("GAKUNBGP2IJZSTE3SVWRAEAGUCDFUZW6DHIFWQFRONTEXXPSUYHI4ADM")
                .feeCharged(BigDecimal.valueOf(100))
                .memoType("none")
                .maxFee(BigDecimal.valueOf(100000))
                .operationCount(1L)
                .validAfter("1970-01-01T00:00:00Z")
                .ledgerAttr(LEDGER_INDEX)
                .build();
    }
}
