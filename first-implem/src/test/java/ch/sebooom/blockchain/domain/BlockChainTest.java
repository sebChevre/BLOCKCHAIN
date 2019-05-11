package ch.sebooom.blockchain.domain;

import ch.sebooom.blockchain.domain.util.CryptoUtil;
import com.google.gson.GsonBuilder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class BlockChainTest {

    private final static Logger LOGGER = LoggerFactory.getLogger(BlockChainTest.class.getName());

    @Test
    public void testBlockChainBasic () {
        BlockChain blockchain = new BlockChain();

        Block genesis = new Block("Bloc 0 genesis", "0");
        blockchain.addBlock(genesis);
        genesis.mine(1);

        Block deux = new Block("Bloc 1",genesis.hash);
        blockchain.addBlock(deux);
        deux.mine(1);

        Block trois = new Block("Block 2",deux.hash);
        blockchain.addBlock(trois);
        trois.mine(1);

        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
        LOGGER.info(blockchainJson);
        LOGGER.info("Validaty: " + blockchain.isChainValid());
        assertTrue(blockchain.isChainValid());
    }

    @Test
    public void testBlockChainValidWithBlockChainObject () {
        BlockChain blockchain = new BlockChain();

        Block genesis  = new Block("Bloc 0 genesis", "0");
        blockchain.addBlock(genesis);
        genesis.mine(1);

        Block deux = new Block("Bloc 1",blockchain.getLastHash());
        blockchain.addBlock(deux);
        deux.mine(1);

        Block trois = new Block("Block 2",blockchain.getLastHash());
        blockchain.addBlock(trois);
        trois.mine(1);

        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
        LOGGER.info(blockchainJson);
        LOGGER.info("Validity: " + blockchain.isChainValid());
        assertTrue(blockchain.isChainValid());
    }

    @Test
    public void testBlockChainInValid () {
        BlockChain blockchain = new BlockChain();

        Block genesis  = new Block("Bloc 0 genesis", "0");
        blockchain.addBlock(genesis);
        genesis.mine(1);

        Block deux = new Block("Bloc 1",blockchain.getLastHash());
        blockchain.addBlock(deux);
        deux.mine(1);

        Block trois = new Block("Block 2", CryptoUtil.sha256Hash("123"));
        blockchain.addBlock(trois);
        trois.mine(1);

        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
        LOGGER.info(blockchainJson);
        LOGGER.info("Validity: " + blockchain.isChainValid());
        assertFalse(blockchain.isChainValid());
    }
}