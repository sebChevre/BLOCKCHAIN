package ch.sebooom.blockchain.domain.noeuds;

import ch.sebooom.blockchain.application.blockchain.web.api.resources.PortefeuilleRessource;
import lombok.EqualsAndHashCode;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

/**
 * Représente un noeud distant référencé dans l'aplication
 */
@EqualsAndHashCode(of = "identifiant")
public class NoeudDistant {

    /* Identifiant unique du noeud sur le réseau */
    private String identifiant;
    /* hôte du noeud */
    private String hote;
    /* port du noeud */
    private int port;
    /* Liste des noeuds distants connectés */
    public List<NoeudDistant> noeudsConnectes = new ArrayList<>();
    /* Le prortefuille lié au noeud */
    public PorteFeuille porteFeuille;


    public static NoeudDistant from(String identifiant, int port, PortefeuilleRessource portefeuille){
        NoeudDistant noeudDistant = new NoeudDistant();
        noeudDistant.identifiant = identifiant;
        noeudDistant.port = port;
        noeudDistant.hote = "localhost";

        noeudDistant.porteFeuille = PorteFeuille.creerPortefeuilleDistant(portefeuille.getClePublique(),portefeuille.getAdresse());
        return noeudDistant;
    }

    public String identifiant() {
        return this.identifiant;
    }

    public String hote() {
        return this.hote;
    }

    public int port() {
        return this.port;
    }

    
}
