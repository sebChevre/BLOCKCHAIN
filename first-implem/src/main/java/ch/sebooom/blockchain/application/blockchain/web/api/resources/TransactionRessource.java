package ch.sebooom.blockchain.application.blockchain.web.api.resources;

import ch.sebooom.blockchain.domain.util.CryptoUtil;
import lombok.Getter;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */
@Getter
public class TransactionRessource {

    float inputsValue;
    float outputsValue;
    String expediteur;
    String destinataire;
    float value;
    private List<OutputRessource> outputs = new ArrayList<>();
    private List<InputRessource> inputs = new ArrayList<>();
    private String identifiant;



    public TransactionRessource(float inputsValue, float outputsValue, PublicKey expediteur, PublicKey destinataire, float value, String identifiant) {
        this.inputsValue = inputsValue;
        this.outputsValue = outputsValue;

        this.expediteur = CryptoUtil.getStringFromKey(expediteur);

        this.destinataire = CryptoUtil.getStringFromKey(destinataire);
        this.value = value;
        this.identifiant = identifiant;
    }

    public void add(OutputRessource outputRessource) {
        this.outputs.add(outputRessource);
    }

    public void addInput(InputRessource input) {
        this.inputs.add(input);

    }
}
