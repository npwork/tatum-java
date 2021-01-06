package io.tatum.transaction.xrp;

import org.xrpl.rpc.v1.AccountAddress;
import org.xrpl.rpc.v1.Common.*;
import org.xrpl.rpc.v1.CurrencyAmount;
import org.xrpl.rpc.v1.XRPDropsAmount;

public class XrpUtil {

    private XrpUtil() {}

    public static Destination createDestination(String _destination) {
        AccountAddress destinationAddress = AccountAddress.newBuilder().setAddress(_destination).build();
        return Destination.newBuilder().setValue(destinationAddress).build();
    }

    public static CurrencyAmount createPayment(Long amount) {
        XRPDropsAmount sendAmount = XRPDropsAmount.newBuilder().setDrops(amount).build();
        return CurrencyAmount.newBuilder().setXrpAmount(sendAmount).build();
    }

    public static Amount createSenderAmount(CurrencyAmount paymentAmount) {
        return Amount.newBuilder().setValue(paymentAmount).build();
    }

    public static Account createSenderAccount(String account2) {
        AccountAddress senderAddress = AccountAddress.newBuilder().setAddress(account2).build();
        return Account.newBuilder().setValue(senderAddress).build();
    }
}
