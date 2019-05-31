package ch.sebooom.blockchain.application.blockchain.web.api.resources;

import ch.sebooom.blockchain.domain.noeuds.NoeudDistant;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NoeudDistantRessource {


    private String noeudId;
    private String hote;
    private int port;
    private PortefeuilleDistantRessource portefeuille;

    public NoeudDistantRessource (NoeudDistant noeud){
        this.noeudId = noeud.identifiant();
        this.hote = noeud.hote();
        this.port = noeud.port();
        this.portefeuille = new PortefeuilleDistantRessource(noeud.porteFeuille);

    }

}
