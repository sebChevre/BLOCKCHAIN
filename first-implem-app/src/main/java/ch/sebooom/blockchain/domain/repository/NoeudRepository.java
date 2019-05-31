package ch.sebooom.blockchain.domain.repository;

import ch.sebooom.blockchain.domain.noeuds.PorteFeuille;
import ch.sebooom.blockchain.domain.noeuds.Noeud;

public interface NoeudRepository {


    boolean creerNoeud(Noeud noeud);

    Noeud getNoeud();

    void addPortefeuilleToNoeud(PorteFeuille premierPortefeuille);
}
