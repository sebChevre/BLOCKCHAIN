package ch.sebooom.blockchain.domain;

import com.google.common.collect.Iterables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.stream.IntStream;

/**
 * La chain ede blocs
 */
public class BlockChain {

    private final static Logger LOGGER = LoggerFactory.getLogger(BlockChain.class.getName());

    public  List<Block> blockChain = new ArrayList<>();

    /**
     * Ajout d'un block à la chaine
     * @param block
     * @return un boolean spécifiant si l'ajout au bloc s'est bien passé
     */
    public boolean addBlock(Block block){
        Objects.requireNonNull(block,"The block can't be null");
        blockChain.add(block);
        return true;
    }

    public Block getLastBlock () {
        return Iterables.getLast(blockChain);
    }
    /**
     * Retourne le hash du dernier block de la chaine
     * @return
     */
    public String getLastHash(){

        return getLastBlock().hash;
    }

    /**
     * On s'assure de la cohérence et de la validité de la chaine
     * @return un booléen indiquant le status de validité de la chaine
     */
    public  Boolean isChainValid() {

        //reduce dés qu'un élément est trouvé (id d'un bloc non integre
        OptionalInt blocNonIntegre = IntStream
                .range(1,blockChain.size())
                .filter(idxBlockCourant -> {
                    Block blocCourant = blockChain.get(idxBlockCourant);
                    Block blocPrecedent = blockChain.get(idxBlockCourant-1);

                    return !isBlockIntegre(blocCourant,blocPrecedent);
                }).findFirst();

                return !blocNonIntegre.isPresent();

    }

    private boolean isBlockIntegre(Block blocCourant, Block blocPrecedent) {

        //comparaison du hash stocké et du hash calculer
        if(!blocCourant.isBlockIntegre()){
            LOGGER.warn("Hash courant non égal");
            return false;
        }

        //compate le lien entre les blocs courant et précédent
        if(!blocPrecedent.hash.equals(blocCourant.hashPrecedent)){
            LOGGER.warn("Hash précédent no égal");
            return false;
        }

        return true;
    }



}


