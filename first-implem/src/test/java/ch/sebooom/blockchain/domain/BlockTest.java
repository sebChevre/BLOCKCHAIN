package ch.sebooom.blockchain.domain;

import ch.sebooom.blockchain.domain.util.CryptoUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.junit.Assert.assertFalse;


public class BlockTest {

    private final static Logger LOGGER = LoggerFactory.getLogger(BlockTest.class.getName());


    @Test
    public void givenBlockInstanticationWithNullValuesThenExceptionMusstBeThrown () {

        Throwable nullDataException = catchThrowable(() -> {
            Block b = new Block("",0);
        });
        assertThat(nullDataException).isInstanceOf(NullPointerException.class);

        Throwable nullHashException = catchThrowable(() -> {
            Block b = new Block(null,1);
        });
        assertThat(nullHashException).isInstanceOf(NullPointerException.class);

    }

    @Test
    public void givenTwoBlockThenHashMustBeDifferent () {

        Block block = new Block(CryptoUtil.sha256Hash("123"),0);

        //simuler un timestamp différent
        try{
            Thread.sleep(100);
        }catch (Exception e){ }

        Block block2 = new Block(CryptoUtil.sha256Hash("234"),1);

        String hash = block.hash;
        String hash2 = block2.hash;

        LOGGER.info("hash1: " + hash);
        LOGGER.info("hash2: " + hash2);

        assertFalse("hash are different: ",hash.equals(hash2));

    }

    @Test
    public void givenThreeBlockWhenGenerateBlockAndHashLinkAllMustBeDone () {

        Block genesisBlock = new Block( "0",0);
        LOGGER.info("Hash for block 1 : " + genesisBlock.hash);

        Block secondBlock = new Block(genesisBlock.hash,1);
        LOGGER.info("Hash for block 2 : " + secondBlock.hash);

        Block thirdBlock = new Block(secondBlock.hash,2);
        LOGGER.info("Hash for block 3 : " + thirdBlock.hash);
    }

    @Test
    public void givenABlockWhenBlockIsMinedThenHashMusttBeChanged () {

        Block block = new Block(CryptoUtil.sha256Hash("b1"),0);
        String initialHash = block.hash;

        block.mine(1);

        assertThat(initialHash).isNotEqualTo(block.hash);

    }


}