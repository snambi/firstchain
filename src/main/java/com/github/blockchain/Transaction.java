package com.github.blockchain;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import static com.github.blockchain.StringUtils.getStringFromKey;
/**
 *
 *
 *
 *
 * Read this to understand the concept behind Transactions
 * https://en.bitcoin.it/wiki/Coin_analogy
 *
 * https://en.bitcoin.it/wiki/Raw_Transactions
 */
public class Transaction {

    private String transactionId;
    private PublicKey sender;
    private PublicKey receiver;

    private float value;
    private byte[] signature;

    private ArrayList<TransactionInput> inputs;
    private ArrayList<TransactionOutput> outputs;

    public Transaction(PublicKey sender,
                       PublicKey receiver,
                       float value,
                       byte[] signature,
                       ArrayList<TransactionInput> inputs,
                       ArrayList<TransactionOutput> outputs) {

        this.sender = sender;
        this.receiver = receiver;
        this.value = value;
        this.signature = signature;
        this.inputs = inputs;
        this.outputs = outputs;
    }

    public String calculateHash(){
        return StringUtils.generateSha256Hash(
                getStringFromKey(sender) + getStringFromKey(receiver)+
                        Float.toString(value)
                    );
    }

    //Signs all the data. ( this is essentially encryption using private key )
    public void generateSignature(PrivateKey privateKey) {
        String data = StringUtils.getStringFromKey(sender) + StringUtils.getStringFromKey(receiver) + Float.toString(value)	;
        signature = StringUtils.createSignature(privateKey,data);
    }
    //Verifies the signed data hasn't been tampered with
    public boolean verifySignature() {
        String data = StringUtils.getStringFromKey(sender) + StringUtils.getStringFromKey(receiver) + Float.toString(value)	;
        return StringUtils.verifyECDSASig(sender, data, signature);
    }
}
