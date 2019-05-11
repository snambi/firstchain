package com.github.blockchain;

public class TransactionInput {

    private String transactionOutputId;
    private TransactionOutput UTXO;

    public TransactionInput(String transactionOutputId) {
        this.transactionOutputId = transactionOutputId;
    }

    public TransactionOutput getUTXO(){
        return this.UTXO;
    }
}
