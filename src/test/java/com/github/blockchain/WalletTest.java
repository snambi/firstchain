package com.github.blockchain;

import org.junit.Assert;
import org.junit.Test;

public class WalletTest {

    @Test
    public void stringTest(){



        Wallet wallet = new Wallet();

        System.out.println( wallet.getPublicKey());
        System.out.println(">>>");


        System.out.println( wallet.cert_ToString());

        System.out.println( wallet.publicKey_ToString());

        System.out.println( wallet.privateKey_ToString());

        Assert.assertNotNull(wallet);
    }
}
