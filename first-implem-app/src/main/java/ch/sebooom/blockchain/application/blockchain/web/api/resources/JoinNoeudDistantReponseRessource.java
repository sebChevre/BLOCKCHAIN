package ch.sebooom.blockchain.application.blockchain.web.api.resources;

import ch.sebooom.blockchain.domain.noeuds.Noeud;
import ch.sebooom.blockchain.domain.noeuds.NoeudDistant;
import ch.sebooom.blockchain.domain.util.CryptoUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class JoinNoeudDistantReponseRessource {

    /* Informations du noeud  */
    private String identifiant;
    private String hote;
    private int port;
    /* information du portefeuille lié */
    private String clePubliquePortefuille;
    private String adressePortefeuille;
    private String descriptionPortefeuille;
    
    public JoinNoeudDistantReponseRessource () {}

    public JoinNoeudDistantReponseRessource(Noeud noeud){

        this.identifiant = noeud.identifiant();
        this.hote = noeud.hote();
        this.port = noeud.port();

        this.clePubliquePortefuille = CryptoUtil.getStringFromKey(noeud.porteFeuille().clePublique);
        this.adressePortefeuille = noeud.porteFeuille().adresse;
        this.descriptionPortefeuille = noeud.porteFeuille().description;
    }

    public NoeudDistant toNoeudDistant(){

        NoeudDistant noeudDistant = NoeudDistant.from(
                this.identifiant,
                this.hote,
                this.port,
                this.clePubliquePortefuille,
                this.adressePortefeuille,
                this.descriptionPortefeuille
        );

        return noeudDistant;
    }

}
