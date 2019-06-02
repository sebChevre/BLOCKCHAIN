package ch.sebooom.blockchain.application.blockchain.web.api.resources;

import ch.sebooom.blockchain.domain.noeuds.PortefeuilleDistant;
import lombok.Getter;


@Getter
public class PortefeuilleDistantRessource {

    private String clePublique;
    private String adresse;
    private String description;

    public PortefeuilleDistantRessource() {}

    public PortefeuilleDistantRessource(PortefeuilleDistant porteFeuille) {

        this.adresse = porteFeuille.adresse;
        this.clePublique = porteFeuille.clePublique;
        this.description =  porteFeuille.description;

    }


}
