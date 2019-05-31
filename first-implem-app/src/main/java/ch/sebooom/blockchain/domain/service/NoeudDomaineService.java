package ch.sebooom.blockchain.domain.service;

import ch.sebooom.blockchain.domain.noeuds.Noeud;
import ch.sebooom.blockchain.domain.noeuds.PorteFeuille;
import ch.sebooom.blockchain.domain.repository.NoeudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoeudDomaineService {

    private NoeudRepository noeudRepository;

    @Autowired
    public NoeudDomaineService(NoeudRepository noeudRepository){
        this.noeudRepository = noeudRepository;
    }


    public void creerNoeud(Noeud noeud) {
        this.noeudRepository.creerNoeud(noeud);
    }

    public Noeud getNoeud() {
        return this.noeudRepository.getNoeud();
    }

    public void addPortefeuilleToNoeud(PorteFeuille portefeuille) {
        this.noeudRepository.addPortefeuilleToNoeud(portefeuille);
    }
}
