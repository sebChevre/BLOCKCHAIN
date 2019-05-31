package ch.sebooom.blockchain.infrastructure.noeuds;

import ch.sebooom.blockchain.domain.noeuds.PorteFeuille;
import ch.sebooom.blockchain.domain.noeuds.Noeud;
import ch.sebooom.blockchain.domain.repository.NoeudRepository;
import org.springframework.stereotype.Repository;

@Repository
public class NoeudInMemoryRepository implements NoeudRepository {

    Noeud noeud;

    @Override
    public boolean creerNoeud(Noeud noeud) {
        this.noeud = noeud;
        return true;
    }

    @Override
    public Noeud getNoeud(){
        return noeud;
    }

    @Override
    public void addPortefeuilleToNoeud(PorteFeuille premierPortefeuille) {
        this.noeud.creerPortefeuille(premierPortefeuille);
    }
}
