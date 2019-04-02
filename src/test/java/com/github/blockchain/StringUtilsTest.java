package com.github.blockchain;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class StringUtilsTest {

    @Test
    public void generateSha256(){

        String data = "hello world";

        String digest = StringUtils.generateSha256Hash(data);

        System.out.println(digest);

        //Assert.assertEquals("a948904f2f0f479b8f8197694b30184b0d2ed1c1cd2a1ec0fb85d299a192a447", digest);
    }

    @Test
    public void generateSha256Java(){

        try {
            String secret = "secret";
            String message = "Message";

            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);

            String hash = Base64.encodeBase64String(sha256_HMAC.doFinal(message.getBytes()));
            System.out.println(hash);
        }
        catch (Exception e){
            System.out.println("Error");
        }
    }


}
