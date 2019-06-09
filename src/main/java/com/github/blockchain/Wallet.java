package com.github.blockchain;


import org.bouncycastle.asn1.x509.X509Name;
import org.bouncycastle.openssl.PEMWriter;
import org.bouncycastle.util.Strings;
import org.bouncycastle.x509.X509V1CertificateGenerator;

import javax.security.auth.x500.X500Principal;
import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.Random;

public class Wallet {

    private static final String ALGORITHM_RSA = "RSA";
    private KeyPair keyPair;
    private X509Certificate cert;

    static {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    public Wallet() {
        generateKeyPair();
    }

    public void generateKeyPair() {

        try {

            // yesterday
            Date validityBeginDate = new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000);
            // in 2 years
            Date validityEndDate = new Date(System.currentTimeMillis() + 2 * 365 * 24 * 60 * 60 * 1000);


            // GENERATE THE PUBLIC/PRIVATE RSA KEY PAIR
            KeyPairGenerator keyPairGenerator = null;
            keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM_RSA, "BC");
            keyPairGenerator.initialize(2048, new SecureRandom());

            keyPair = keyPairGenerator.generateKeyPair();

            //System.out.println( keyPair.getPrivate() );
            //System.out.println( keyPair.getPublic() );


            // GENERATE THE X509 CERTIFICATE
            X509V1CertificateGenerator certGen = new X509V1CertificateGenerator();
            X500Principal dnName = new X500Principal("CN=John Doe");


            certGen.setSubjectDN(dnName);
            certGen.setIssuerDN(dnName); // use the same
            certGen.setNotBefore(validityBeginDate);
            certGen.setNotAfter(validityEndDate);
            certGen.setPublicKey(keyPair.getPublic());
            certGen.setSignatureAlgorithm("SHA256WithRSAEncryption");
            certGen.setSubjectDN( new X509Name("C=AU, ST=Victoria"));
            BigInteger bigInteger = new BigInteger(String.valueOf((new Random()).nextInt(Integer.SIZE - 1)));

            //System.out.println("BigInteger: "+ bigInteger );
            certGen.setSerialNumber( bigInteger );

            cert = certGen.generate(keyPair.getPrivate(), "BC");


        } catch (NoSuchAlgorithmException | NoSuchProviderException | CertificateEncodingException |
                SignatureException | InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    public  byte[] createSignature( String input) {

        Signature dsa;

        byte[] output = new byte[0];

        try {

            dsa = Signature.getInstance(ALGORITHM_RSA, "BC");

            dsa.initSign( getPrivateKey() );

            byte[] strByte = input.getBytes();

            dsa.update(strByte);

            byte[] realSig = dsa.sign();
            output = realSig;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return output;
    }


    /**
     * This constructor automatically identifies "inputs" based on value
     *
     * @param value
     * @param receiver
     * @return
     */
    public Transaction createTransaction(float value, PublicKey receiver) {
        Transaction transaction = new Transaction(getPublicKey(), receiver, value, null);
        return transaction;
    }

    public PublicKey getPublicKey() {
        return keyPair.getPublic();
    }

    public PrivateKey getPrivateKey() {
        return keyPair.getPrivate();
    }


    public void toStdout() {

        // A KeyFactory is used to convert encoded keys to their actual Java classes
        KeyFactory ecKeyFac = null;

        try {

            // DUMP CERTIFICATE AND KEY PAIR
            System.out.println(repeat('=', 80));
            System.out.println("CERTIFICATE TO_STRING");
            System.out.println(repeat('=', 80));
            System.out.println();
            System.out.println(cert);
            System.out.println();

            System.out.println(repeat('=', 80));
            System.out.println("CERTIFICATE PEM (to store in a cert.pem file)");
            System.out.println(repeat('=', 80));
            System.out.println();

            PEMWriter pemWriter = new PEMWriter(new PrintWriter(System.out));
            pemWriter.writeObject(cert);
            pemWriter.flush();
            System.out.println();

            System.out.println(repeat('=', 80));
            System.out.println("PUBLIC KEY PEM (to store in a public-key.pem file)");
            System.out.println(repeat('=', 80));
            System.out.println();

            pemWriter.writeObject(keyPair.getPublic());
            pemWriter.flush();
            System.out.println();

            System.out.println(repeat('=', 80));
            System.out.println("PRIVATE KEY PEM (to store in a private-key.pem file)");
            System.out.println(repeat('=', 80));
            System.out.println();

            pemWriter.writeObject(keyPair.getPrivate());
            pemWriter.flush();
            System.out.println();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String cert_ToString(){

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PEMWriter pemWriter = new PEMWriter(new PrintWriter(baos));

        try {

            pemWriter.writeObject(cert);
            pemWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return baos.toString();
    }

    public String publicKey_ToString(){

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PEMWriter pemWriter = new PEMWriter(new PrintWriter(baos));

        try {

            pemWriter.writeObject(keyPair.getPublic());
            pemWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return baos.toString();
    }

    public String privateKey_ToString(){

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PEMWriter pemWriter = new PEMWriter(new PrintWriter(baos));

        try {

            pemWriter.writeObject(keyPair.getPrivate());
            pemWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return baos.toString();
    }

    private static String repeat( char ch, int count ) {
        StringBuilder buffer = new StringBuilder();

        for ( int i = 0; i < count; ++i )
            buffer.append( ch );

        return buffer.toString();
    }
}
