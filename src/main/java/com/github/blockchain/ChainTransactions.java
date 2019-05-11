package com.github.blockchain;

import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChainTransactions {

    private static ArrayList<Block> blockchain = new ArrayList<>();
    static int difficulty = 4;
    static Wallet walletA;
    static Wallet walletB;

    static Map<String, TransactionOutput> UTXO = new HashMap<String, TransactionOutput>();

    public static void main( String[] args ){

        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        walletA = new Wallet();
        walletB = new Wallet();


        // print the private and public keys
        System.out.println("WalletA Private key: ");
        System.out.println( StringUtils.getStringFromKey(walletA.getPrivateKey()));
        System.out.println("WalletA Public key: ");
        System.out.println( StringUtils.getStringFromKey(walletA.getPublicKey()));

        // the wallet A creates a transaction to send money to wallet B.

        // create a test transaction from wallet A to wallet B
        Transaction txn = new Transaction( walletA.getPublicKey(), walletB.getPublicKey(), 5, null);

        txn.generateSignature(walletA.getPrivateKey());

        System.out.print("Is signature verified: ");
        System.out.println(txn.verifySignature());
    }


}
