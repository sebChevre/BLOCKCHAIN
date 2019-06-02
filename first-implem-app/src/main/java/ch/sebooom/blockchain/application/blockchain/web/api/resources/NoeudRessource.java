package ch.sebooom.blockchain.application.blockchain.web.api.resources;

import ch.sebooom.blockchain.domain.noeuds.Noeud;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NoeudRessource {

    private String identifiant;
    private String hote;
    private int port;
    private PortefeuilleRessource portefeuille;

    public NoeudRessource(Noeud noeud, float balance){
        this.identifiant = noeud.identifiant();
        this.hote = noeud.hote();
        this.port = noeud.port();
        this.portefeuille = new PortefeuilleRessource(noeud.porteFeuille(),balance);
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
