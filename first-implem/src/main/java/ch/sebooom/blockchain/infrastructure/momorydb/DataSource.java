package ch.sebooom.blockchain.infrastructure.momorydb;

import ch.sebooom.blockchain.domain.BlockChain;
import ch.sebooom.blockchain.domain.PorteFeuille;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */
@Component
public class DataSource {

    private static final BlockChain blockChain = new BlockChain();
    private static final List<PorteFeuille> portefeuilles = new ArrayList<>();

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
