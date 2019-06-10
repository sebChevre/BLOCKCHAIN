package ch.sebooom.blockchain.infrastructure.noeuds.repository;

import ch.sebooom.blockchain.domain.noeuds.PorteFeuille;
import ch.sebooom.blockchain.domain.noeuds.Noeud;
import ch.sebooom.blockchain.domain.repository.NoeudRepository;
import ch.sebooom.blockchain.infrastructure.momorydb.InMemoryDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class NoeudInMemoryRepository implements NoeudRepository {


    @Autowired
    InMemoryDataSource inMemoryDataSource;


    @Override
    public boolean creerNoeud(Noeud noeud) {
        inMemoryDataSource.setNoeud(noeud);
        return true;
    }

    @Override
    public Noeud getNoeud(){
        return inMemoryDataSource.getNoeud();
    }

    @Override
    public void addPortefeuilleToNoeud(PorteFeuille portefeuille) {
        inMemoryDataSource.getNoeud().definirPortefeuille(portefeuille);
    }
}
