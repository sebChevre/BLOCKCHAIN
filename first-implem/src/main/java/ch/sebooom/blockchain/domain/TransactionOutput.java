package ch.sebooom.blockchain.domain;

import ch.sebooom.blockchain.domain.util.CryptoUtil;

import java.security.PublicKey;

public class TransactionOutput {

    public String id;
    public PublicKey destinataire; //also known as the new owner of these coins.
    public float value; //the amount of coins they own
    public String parentTransactionId; //the id of the transaction this output was created in

    //Constructor
    public TransactionOutput(PublicKey destinataire, float value, String parentTransactionId) {
        this.destinataire = destinataire;
        this.value = value;
        this.parentTransactionId = parentTransactionId;
        this.id = CryptoUtil.sha256Hash(CryptoUtil.getStringFromKey(destinataire)+Float.toString(value)+parentTransactionId);
    }

    //Check if coin belongs to you
    public boolean isMine(PublicKey publicKey) {
        return (publicKey == destinataire);
    }
}
