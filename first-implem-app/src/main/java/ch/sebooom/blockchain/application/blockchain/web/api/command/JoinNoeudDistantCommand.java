package ch.sebooom.blockchain.application.blockchain.web.api.command;

import ch.sebooom.blockchain.domain.noeuds.Noeud;
import ch.sebooom.blockchain.domain.noeuds.NoeudDistant;
import ch.sebooom.blockchain.domain.util.CryptoUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Commande permettant de se joindre à l'api des autres clients du réseau
 */
@Getter
@Setter
@ToString
public class JoinNoeudDistantCommand {

    /* Informations du noeud  */
    private String identifiant;
    private String hote;
    private int port;
    /* information du portefeuille lié */
    private String clePubliquePortefuille;
    private String adressePortefeuille;
    private String descriptionPortefeuille;

    public JoinNoeudDistantCommand() {}

    public JoinNoeudDistantCommand fromNoeud(Noeud noeud){

        this.identifiant = noeud.identifiant();
        this.hote = noeud.hote();
        this.port = noeud.port();

        this.clePubliquePortefuille = CryptoUtil.getStringFromKey(
                noeud.porteFeuille().clePublique);
        this.adressePortefeuille = noeud.porteFeuille().adresse;
        this.descriptionPortefeuille = noeud.porteFeuille().description;

        return this;
    }

    public  NoeudDistant toNoeudDistant(){

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



    public String json()  {

        ObjectMapper m = new ObjectMapper();

        try {
            return m.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
