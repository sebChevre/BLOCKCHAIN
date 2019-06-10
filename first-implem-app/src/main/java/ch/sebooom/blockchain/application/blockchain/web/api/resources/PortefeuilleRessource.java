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
public class PortefeuilleRessource {

    private String clePublique;
    private float balance;
    private String adresse;
    private boolean isBasePortefeuille;
    private String description;

    public PortefeuilleRessource () {}

    public PortefeuilleRessource(PorteFeuille porteFeuille) {

        this.adresse = porteFeuille.adresse;
        this.balance = porteFeuille.balance();
        this.clePublique = CryptoUtil.getStringFromKey(porteFeuille.clePublique);
        this.isBasePortefeuille = porteFeuille.isBasePortefeuille;
        this.description =  porteFeuille.description;

    }


}
