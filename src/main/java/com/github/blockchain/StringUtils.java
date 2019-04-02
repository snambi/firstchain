package com.github.blockchain;


import org.apache.commons.codec.digest.DigestUtils;

import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;

public class StringUtils {

    public static String generateSha256Hash( String data){
        return DigestUtils.sha256Hex( data);
    }

    public static String getStringFromKey(Key key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }


    //Generated ECDSA Signature and returns the result ( as bytes ).
    public static byte[] createSignature(PrivateKey privateKey, String input) {

        Signature dsa;

        byte[] output = new byte[0];

        try {
            dsa = Signature.getInstance("ECDSA", "BC");
            dsa.initSign(privateKey);
            byte[] strByte = input.getBytes();
            dsa.update(strByte);
            byte[] realSig = dsa.sign();
            output = realSig;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return output;
    }


    //Verifies a String signature
    public static boolean verifyECDSASig(PublicKey publicKey, String data, byte[] signature) {

        try {

            Signature ecdsaVerify = Signature.getInstance("ECDSA", "BC");

            ecdsaVerify.initVerify(publicKey);
            ecdsaVerify.update(data.getBytes());

            return ecdsaVerify.verify(signature);

        }catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
