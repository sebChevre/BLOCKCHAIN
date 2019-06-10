package ch.sebooom.blockchain.infrastructure.momorydb;

import ch.sebooom.blockchain.domain.blockchain.BlockChain;
import ch.sebooom.blockchain.domain.noeuds.Noeud;
import ch.sebooom.blockchain.domain.noeuds.PorteFeuille;
import org.springframework.stereotype.Component;

/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */
@Component
public class InMemoryDataSource {

    /* etat local de la chain */
    private  BlockChain blockChain = new BlockChain();
    /* le noeud */
    private Noeud noeud;


    public  BlockChain getBlockChain(){
        return blockChain;
    }

    public  PorteFeuille getPortefeuille(){
        return noeud.porteFeuille();
    }

    public Noeud getNoeud () {
        return noeud;
    }

    public void setNoeud(Noeud noeud){
        this.noeud = noeud;
    }



}
