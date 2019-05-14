package ch.sebooom.blockchain.application.blockchain.web.api.resources;

import ch.sebooom.blockchain.domain.util.CryptoUtil;
import lombok.Getter;

import java.security.PublicKey;

/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */
@Getter
public class PortefeuilleRessource {

    private String clePublique;
    private float balance;

    public PortefeuilleRessource(PublicKey clePublique, float balance) {

        this.balance = balance;
        this.clePublique = CryptoUtil.getStringFromKey(clePublique);
    }


}
