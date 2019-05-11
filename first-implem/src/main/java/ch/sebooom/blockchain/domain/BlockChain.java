package ch.sebooom.blockchain.domain;

import com.google.common.collect.Iterables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * La chain ede blocs
 */
public class BlockChain {

    private final static Logger LOGGER = LoggerFactory.getLogger(BlockChain.class.getName());

    public  List<Block> blockChain = new ArrayList<>();

    /**
     * Ajout d'un block à la chaine
     * @param block
     * @return
     */
    public boolean addBlock(Block block){
        blockChain.add(block);
        return true;
    }

    /**
     * Retourne le hash du dernier block de la chaine
     * @return
     */
    public String getLastHash(){
        return Iterables.getLast(blockChain).hash;
    }

    /**
     * On s'assure de la cohérence et de la validité de la chaine
     * @return un booléen indiquant le status de validité de la chaine
     */
    public  Boolean isChainValid() {

        Block currentBlock;
        Block previousBlock;

        //loop through blockchain to check hashes:
        for(int i=1; i < blockChain.size(); i++) {
            currentBlock = blockChain.get(i);
            previousBlock = blockChain.get(i-1);
            //compare registered hash and calculated hash:
            if(!currentBlock.hash.equals(currentBlock.calculeHashSignature()) ){
                LOGGER.debug("Current Hashes not equal");
                return false;
            }
            //compare previous hash and registered previous hash
            if(!previousBlock.hash.equals(currentBlock.previousHash) ) {
                LOGGER.debug("Previous Hashes not equal");
                return false;
            }
        }
        return true;
    }


    public boolean mineLastBlock () {

        Iterables.getLast(blockChain).mineBlock();

        return true;
    }
}


