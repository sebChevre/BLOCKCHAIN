package ch.sebooom.blockchain.infrastructure.momorydb;

import ch.sebooom.blockchain.domain.blockchain.BlockChain;
import ch.sebooom.blockchain.domain.noeuds.PorteFeuille;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */
@Component
public class InMemoryDataSource {

    private  BlockChain blockChain = new BlockChain();
    private  List<PorteFeuille> portefeuilles = new ArrayList<>();

    public  void addPortefeuille(PorteFeuille porteFeuille){
        portefeuilles.add(porteFeuille);
    }

    public  BlockChain getBlockChain(){
        return blockChain;
    }

    public  List<PorteFeuille> getAllPortefeuille(){
        return portefeuilles;
    }



}
