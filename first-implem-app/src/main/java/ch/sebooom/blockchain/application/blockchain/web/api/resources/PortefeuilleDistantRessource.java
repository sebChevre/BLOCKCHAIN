package ch.sebooom.blockchain.application.blockchain.web.api.resources;

import ch.sebooom.blockchain.domain.noeuds.PorteFeuille;
import ch.sebooom.blockchain.domain.util.CryptoUtil;
import lombok.Getter;

/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */
@Getter
public class PortefeuilleDistantRessource {

    private String clePublique;
    private float balance;
    private String adresse;
    private boolean isBasePortefeuille;
    private String description;

    public PortefeuilleDistantRessource() {}

    public PortefeuilleDistantRessource(PorteFeuille porteFeuille) {

        this.adresse = porteFeuille.adresse;
        this.clePublique = porteFeuille.clePubliqueStr;
        this.isBasePortefeuille = porteFeuille.isBasePortefeuille;
        this.description =  porteFeuille.description;

    }


}
