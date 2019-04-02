package com.github.blockchain;

import java.security.Security;
import java.util.ArrayList;

public class ChainClients {

    private static ArrayList<Block> blockchain = new ArrayList<>();
    static int difficulty = 4;
    static Wallet walletA;
    static Wallet walletB;

    public static void main( String[] args ){

        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        walletA = new Wallet();
        walletB = new Wallet();


    }
}
