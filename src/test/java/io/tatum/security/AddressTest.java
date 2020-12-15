package io.tatum.security;

import org.junit.Test;

import java.util.concurrent.ExecutionException;

public class AddressTest {

    @Test
    public void checkMaliciousAddressTest() throws ExecutionException, InterruptedException {
        Address address = new Address();
        String status = address.checkMaliciousAddress("0xeff92411ea883db8465ae4d8f93210f1f416e06a");
        System.out.println(status);
    }
}
