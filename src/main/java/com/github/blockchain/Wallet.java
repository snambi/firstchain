package com.github.blockchain;

import java.security.*;
import java.security.spec.ECGenParameterSpec;

public class Wallet {

    private PublicKey publicKey;
    private PrivateKey privateKey;

    public Wallet() {
        generateKeyPair();
    }

    public void generateKeyPair(){

        KeyPairGenerator keyGen = null;

        try {

            keyGen = KeyPairGenerator.getInstance("ECDSA","BC");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");

            ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
            keyGen.initialize(ecSpec, random);   //256 bytes provides an acceptable security level

        } catch (NoSuchAlgorithmException|NoSuchProviderException|InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        }

        // Initialize the key generator and generate a KeyPair
        KeyPair keyPair = keyGen.generateKeyPair();

        // Set the public and private keys from the keyPair
        privateKey = keyPair.getPrivate();
        publicKey = keyPair.getPublic();
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }
}
