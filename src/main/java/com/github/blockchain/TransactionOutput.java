package com.github.blockchain;

import java.security.PublicKey;

public class TransactionOutput {

    private String id;
    private PublicKey recipient;
    private float value;
    private String parentTransactionId;

    public TransactionOutput(String id, PublicKey recipient, float value, String parentTransactionId) {
        this.id = id;
        this.recipient = recipient;
        this.value = value;
        this.parentTransactionId = parentTransactionId;
    }

    public boolean isMine( PublicKey publicKey){
        return  ( publicKey == recipient);
    }

    public String getId() {
        return id;
    }

    public PublicKey getRecipient() {
        return recipient;
    }

    public float getValue() {
        return value;
    }

    public String getParentTransactionId() {
        return parentTransactionId;
    }
}
