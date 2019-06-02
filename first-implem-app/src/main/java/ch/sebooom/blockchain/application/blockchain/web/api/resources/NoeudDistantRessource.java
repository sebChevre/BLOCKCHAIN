package ch.sebooom.blockchain.application.blockchain.web.api.resources;

import ch.sebooom.blockchain.domain.noeuds.NoeudDistant;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NoeudDistantRessource {


    /* Informations du noeud  */
    private String identifiant;
    private String hote;
    private int port;
    /* information du portefeuille lié */
    private String clePubliquePortefuille;
    private String adressePortefeuille;
    private String descriptionPortefeuille;

    public NoeudDistantRessource (NoeudDistant noeudDistant){
        this.identifiant = noeudDistant.identifiant();
        this.hote = noeudDistant.hote();
        this.port = noeudDistant.port();

        this.clePubliquePortefuille = noeudDistant.clePubliquePortefuille();
        this.adressePortefeuille = noeudDistant.adressePortefeuille();
        this.descriptionPortefeuille = noeudDistant.descriptionPortefeuille();

    }

}
