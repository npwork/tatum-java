package io.tatum.blockchain;

import io.tatum.model.response.xrp.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.jupiter.api.Assertions.*;

/**
 * XRP testnet
 * <p>
 * https://blockexplorer.one/xrp/testnet/address/rPVb2HeAhFGZ3T2eCwT1TiPJJ97fdFUxxQ
 * https://blockexplorer.one/xrp/testnet/tx/efb7a664e9d406e902195e6eae5385d628f7424b0258a0a4354af997ca6f2dc4
 */
public class XRPTest {
    private final static XRP XRP = new XRP();

    private final static long LEDGER_INDEX = 23425067L;
    public final static String ADDRESS = "rPVb2HeAhFGZ3T2eCwT1TiPJJ97fdFUxxQ";
    private final static String ADDRESS_WITHOUT_TXS = "rwcsGiYMcgmic3nHQDeUEZPJqZfqvMLD85";
    private final static String ADDRESS_DOES_NOT_EXIST = "rwcsYiYMgcmic5gHQDeUEZPJqZfqvMLD85";
    private final static String TX = "efb7a664e9d406e902195e6eae5385d628f7424b0258a0a4354af997ca6f2dc4";
    private final static String TX_DOES_NOT_EXIST = "efb7a615e9d422e502195ee5ae5315d628f7424b0258a0a4354af997ca6f2dc4";

    @Test
    public void GetFee() throws InterruptedException, ExecutionException {
        BigDecimal fee = XRP.xrpGetFee();
        assertTrue(fee.compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    public void GetCurrentLedger() throws InterruptedException, ExecutionException {
        Long currentLedger = XRP.xrpGetCurrentLedger();
        assertTrue(currentLedger > LEDGER_INDEX);
    }

    @Nested
    class GetAccountInfo {
        @Test
        public void valid() throws InterruptedException, ExecutionException {
            AccountDataDetails expected = AccountDataDetails.builder()
                    .account(ADDRESS)
                    .balance(new BigDecimal(1000).scaleByPowerOfTen(6).setScale(0, RoundingMode.HALF_EVEN))
                    .flags(0L)
                    .ledgerEntryType("AccountRoot")
                    .ownerCount(0L)
                    .previousTxnID("EFB7A664E9D406E902195E6EAE5385D628F7424B0258A0A4354AF997CA6F2DC4")
                    .previousTxnLgrSeq(23424979)
                    .sequence(23424979)
                    .index("B5AA32FF8B9439D63200E2D8DA205E7C67000A4B428712FFE1144CBCEF6E5270")
                    .build();
            AccountData accountData = XRP.xrpGetAccountInfo(ADDRESS);

            assertEquals(expected, accountData.getDetails());

            assertThat(accountData, hasProperty("ledgerCurrentIndex"));
            assertThat(accountData, hasProperty("validated"));
        }

        @Test
        public void notFound() throws InterruptedException, ExecutionException {
            AccountData accountData = XRP.xrpGetAccountInfo(ADDRESS_DOES_NOT_EXIST);
            assertNull(accountData);
        }
    }

    @Nested
    class GetLedger {
        @Test
        public void valid() throws ExecutionException, InterruptedException {
            String ledgerHash = "EBB8429A6D45E35FAC34709DE8E07BCA985E9338E8AFBB6111D930C9E7DA04DD";
            XrpLedger expected = XrpLedger.builder()
                    .ledgerHash(ledgerHash)
                    .ledgerIndex(LEDGER_INDEX)
                    .validated(true)
                    .details(XrpLedgerDetails.builder()
                            .accepted(true)
                            .accountHash("FDF125BB4861E49E6A9543E5E636EC131CB16BBF24178C07C53C4A912F429079")
                            .closeFlags(0L)
                            .closeTime(692391953L)
                            .closeTimeHuman("2021-Dec-09 19:05:53.000000000 UTC")
                            .closeTimeResolution(10L)
                            .closed(true)
                            .hash(ledgerHash)
                            .ledgerHash(ledgerHash)
                            .ledgerIndex(LEDGER_INDEX)
                            .parentCloseTime(692391952L)
                            .parentHash("5628D4C3AE84C8145ABB23FDABFE7EB6E848E037BCE8B48AD315178ABDFC5B35")
                            .seqNum(LEDGER_INDEX)
                            .totalCoins(new BigDecimal(99999617487869449L))
                            .transactionHash("B4BE690ECD22EF78B02FE889082C33D99934552AB2EF4AA4B073106CB77FBAFF")
                            .build()
                    )
                    .build();

            XrpLedger ledger = XRP.xrpGetLedger(LEDGER_INDEX);

            assertEquals(2, ledger.getDetails().getTransactions().size());

            ledger.getDetails().setTransactions(null);
            assertEquals(expected, ledger);
        }

        @Test
        public void notFound() throws ExecutionException, InterruptedException {
            XrpLedger ledger = XRP.xrpGetLedger(Long.MAX_VALUE);
            assertNull(ledger);
        }
    }

    @Nested
    class GetAccountBalance {
        @Test
        public void valid() throws ExecutionException, InterruptedException {
            BigInteger balance = XRP.xrpGetAccountBalance(ADDRESS);
            long balanceDivided = (long) (balance.longValue() / Math.pow(10, 6));
            assertEquals(1000L, balanceDivided);
        }

        @Test
        public void notFound() throws ExecutionException, InterruptedException {
            BigInteger balance = XRP.xrpGetAccountBalance(ADDRESS_DOES_NOT_EXIST);
            assertNull(balance);
        }
    }

    @Nested
    class GetTransaction {
        @Test
        public void valid() throws ExecutionException, InterruptedException {
            XrpTransaction expected = getTransaction();

            XrpTransaction xrpTransaction = XRP.xrpGetTransaction(TX);

            assertEquals(expected, xrpTransaction);
        }

        @Test
        public void notFound() throws ExecutionException, InterruptedException {
            XrpTransaction xrpTransaction = XRP.xrpGetTransaction(TX_DOES_NOT_EXIST);
            assertNull(xrpTransaction);
        }
    }

    @Nested
    class GetAccountTransactions {
        @Test
        public void valid() throws ExecutionException, InterruptedException {
            XrpAccountTransactions expected = XrpAccountTransactions.builder()
                    .account(ADDRESS)
                    .ledgerIndexMin(21996156L)
                    .limit(0L)
                    .validated(true)
                    .transactions(
                            Collections.singletonList(XrpAccountTransactionsObject.builder()
                                    .tx(getTransaction())
                                    .validated(true)
                                    .meta(XrpTransactionMetadata.builder()
                                            .transactionIndex(1L)
                                            .transactionResult("tesSUCCESS")
                                            .build()
                                    )
                                    .build())
                    )
                    .build();
            XrpAccountTransactions accountTransactions = XRP.xrpGetAccountTransactions(ADDRESS);

            assertTrue(accountTransactions.getLedgerIndexMax() > LEDGER_INDEX);
            accountTransactions.setLedgerIndexMax(null);

            assertEquals(expected, accountTransactions);
        }

        @Test
        public void withoutTxs() throws ExecutionException, InterruptedException {
            XrpAccountTransactions expected = XrpAccountTransactions.builder()
                    .account(ADDRESS_WITHOUT_TXS)
                    .ledgerIndexMin(21996156L)
                    .limit(0L)
                    .transactions(new ArrayList<>())
                    .validated(true)
                    .build();
            XrpAccountTransactions accountTransactions = XRP.xrpGetAccountTransactions(ADDRESS_WITHOUT_TXS);

            assertTrue(accountTransactions.getLedgerIndexMax() > LEDGER_INDEX);
            accountTransactions.setLedgerIndexMax(null);

            assertEquals(expected, accountTransactions);
        }

        @Test
        public void notFound() throws ExecutionException, InterruptedException {
            XrpAccountTransactions accountTransactions = XRP.xrpGetAccountTransactions(ADDRESS_DOES_NOT_EXIST);
            assertNull(accountTransactions);
        }
    }

    private XrpTransaction getTransaction() {
        return XrpTransaction.builder()
                .account("rPT1Sjq2YGrBMTttX4GZHjKu9dyfzbpAYe")
                .destination(ADDRESS)
                .fee(BigDecimal.valueOf(12))
                .flags(BigDecimal.valueOf(2147483648L))
                .lastLedgerSequence(BigDecimal.valueOf(23424982L))
                .sequence(BigDecimal.valueOf(3065107))
                .signingPubKey("02356E89059A75438887F9FEE2056A2890DB82A68353BE9C0C0C8F89C0018B37FC")
                .txnSignature("3045022100B79F224A8830F9EB0315BC5A2433611ADC7B4AFC80E4BB12F5EEFAD9302AC77A0220559580DE2D4307ECFAF040A162BB2ABE19579CC1A97E01FA526A328DF4483F17")
                .transactionType("Payment")
                .hash(TX.toUpperCase())
                .build();
    }
}
