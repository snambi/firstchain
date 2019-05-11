package com.github.blockchain;

import java.security.PublicKey;

public class TransactionOutput {

    private String id;
    private PublicKey receipient;
    private float value;
    private String parentTransactionId;

    public TransactionOutput(String id, PublicKey receipient, float value, String parentTransactionId) {
        this.id = id;
        this.receipient = receipient;
        this.value = value;
        this.parentTransactionId = parentTransactionId;
    }

    public boolean isMine( PublicKey publicKey){
        return  ( publicKey == receipient );
    }

    public String getId() {
        return id;
    }

    public PublicKey getReceipient() {
        return receipient;
    }

    public float getValue() {
        return value;
    }

    public String getParentTransactionId() {
        return parentTransactionId;
    }
}
