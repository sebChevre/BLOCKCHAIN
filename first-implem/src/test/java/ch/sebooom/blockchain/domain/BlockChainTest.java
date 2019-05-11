package ch.sebooom.blockchain.domain;

import com.google.gson.GsonBuilder;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class BlockChainTest {

    @Test
    public void testBlockChainBasic () {
        BlockChain blockchain = new BlockChain();

        blockchain.addBlock(new Block("Bloc 0 genesis", "0"));
        blockchain.mineLastBlock();

        blockchain.addBlock(new Block("Bloc 1",blockchain.getLastHash()));
        blockchain.mineLastBlock();

        blockchain.addBlock(new Block("Block 2",blockchain.getLastHash()));
        blockchain.mineLastBlock();

        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
        System.out.println(blockchainJson);
        System.out.println("Validaty: " + blockchain.isChainValid());
        assertTrue(blockchain.isChainValid());
    }

    @Test
    public void testBlockChainInvalid () {
        BlockChain blockchain = new BlockChain();

        blockchain.addBlock(new Block("Bloc 0 genesis", "0"));
        blockchain.mineLastBlock();

        blockchain.addBlock(new Block("Bloc 1","22"));
        blockchain.mineLastBlock();

        blockchain.addBlock(new Block("Block 2",blockchain.getLastHash()));
        blockchain.mineLastBlock();

        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
        System.out.println(blockchainJson);
        System.out.println("Validity: " + blockchain.isChainValid());
        assertFalse(blockchain.isChainValid());
    }
}