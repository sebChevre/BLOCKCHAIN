package ch.sebooom.blockchain.domain;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

public class BlockTest {

    private final static Logger LOGGER = LoggerFactory.getLogger(BlockTest.class.getName());

    @Test
    public void testThat2BlocksAddDifferentHash () {

        Block block = new Block("test","123456dff");

        //simuler un timestamp différent
        try{
            Thread.sleep(100);
        }catch (Exception e){

        }

        Block block2 = new Block("test","023456dff");

        String hash = block.hash;
        String hash2 = block2.hash;


        LOGGER.info("hash1: " + hash);
        LOGGER.info("hash2: " + hash2);

        assertFalse("hash are different: ",hash.equals(hash2));

    }

    @Test
    public void testHashGeneration () {

        Block genesisBlock = new Block("Hi im the first block", "0");
        LOGGER.info("Hash for block 1 : " + genesisBlock.hash);

        Block secondBlock = new Block("Yo im the second block",genesisBlock.hash);
        LOGGER.info("Hash for block 2 : " + secondBlock.hash);

        Block thirdBlock = new Block("Hey im the third block",secondBlock.hash);
        LOGGER.info("Hash for block 3 : " + thirdBlock.hash);
    }
}