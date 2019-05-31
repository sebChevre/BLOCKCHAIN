package ch.sebooom.blockchain.application.blockchain.web.api.resources;

import ch.sebooom.blockchain.domain.transaction.TransactionOutput;
import ch.sebooom.blockchain.domain.util.CryptoUtil;
import lombok.Getter;

/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */
@Getter
public class OutputRessource  {

    private String destinataire;
    private float valeur;
    private String identifiant;
    private String parentTransactionId;


    public OutputRessource(TransactionOutput output) {

        this.destinataire = CryptoUtil.getStringFromKey(output.destinataire);
        this.valeur = output.value;
        this.identifiant = output.id;
        this.parentTransactionId = output.parentTransactionId;

    }
}
