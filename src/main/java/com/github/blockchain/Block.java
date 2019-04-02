package com.github.blockchain;

import java.util.Date;

public class Block {

    private String hash;
    private String prevHash;
    private String data;
    private Long timestamp;
    private int nonce;

    public Block( String data, String prevHash) {

        this.data = data;
        this.prevHash = prevHash;

        this.timestamp = new Date().getTime();
        this.hash = calculateHash();
    }


    /**
     * Calculate SHA-256 Hash value of the block.
     * Apply the SHA-256 on previous-hash + data + timestamp
     */
    public String calculateHash(){

        StringBuilder sb = new StringBuilder();
        sb.append( getPrevHash());
        sb.append( getData());
        sb.append( Integer.toString(nonce));
        sb.append( Long.toString( getTimestamp() ));

        String hash = StringUtils.generateSha256Hash(sb.toString());
        return hash;
    }

    public void mineBlock( int difficulty){

        String target = new String( new char[difficulty]).replace('\0','0');

        while( !hash.substring(0,difficulty).equals(target)){
            nonce++;
            hash = calculateHash();
        }

        System.out.println("Block mined");
    }


    public String getHash() {
        return hash;
    }
    public String getPrevHash() {
        return prevHash;
    }
    public String getData() {
        return data;
    }
    public Long getTimestamp() {
        return timestamp;
    }
    public int getNonce() {
        return nonce;
    }

}
