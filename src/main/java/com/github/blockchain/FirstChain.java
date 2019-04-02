package com.github.blockchain;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;

/**
 * Hello world!
 *
 */
public class FirstChain {

    public static ArrayList<Block> blockchain = new ArrayList<Block>();
    public static int difficulty = 5;

    public static void main( String[] args ) {

        int numberOfBlocks = 5;
        String previousHash = "0";

        for( int i=1; i <= numberOfBlocks ; i++){

            Block block = new Block("Hello, this is block no. "+ i, previousHash );
            block.mineBlock(difficulty);

            previousHash = block.getHash();

            blockchain.add(block);
        }

        System.out.println("Is chain valid : " + validateChain());

        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
        System.out.println(blockchainJson);
    }


    static public boolean validateChain(){

        Block currentBlock;
        Block previousBlock;

        for( int i=1 ; i<blockchain.size() ; i++){

            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i-1);

            System.out.println("Block["+i+ "] \n"+ currentBlock.getHash() + "\n"+ currentBlock.calculateHash());
            if( !currentBlock.getHash().equals(currentBlock.calculateHash()) ){
                System.err.println("Hash is wrong for block "+ i);
                return false;
            }

            if( currentBlock.getPrevHash() != previousBlock.getHash()){
                System.err.println("Previous Hash is wrong for block "+ i);
                return false;
            }
        }

        return true;
    }
}
