package ch.sebooom.blockchain.domain;

import ch.sebooom.blockchain.domain.util.StringUtil;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class Transaction {

    public String transactionId; // this is also the hash of the transaction.
    public PublicKey expediteur; // senders address/public key.
    public PublicKey destinataire; // Recipients address/public key.
    public float value;
    public byte[] signature; // this is to prevent anybody else from spending funds in our wallet.

    public List<TransactionInput> inputs = new ArrayList<TransactionInput>();
    public List<TransactionOutput> outputs = new ArrayList<TransactionOutput>();

    private static int sequence = 0; // a rough count of how many transactions have been generated.

    // Constructor:
    public Transaction(PublicKey from, PublicKey to, float value,  ArrayList<TransactionInput> inputs) {
        this.expediteur = from;
        this.destinataire = to;
        this.value = value;
        this.inputs = inputs;
    }

    // This Calculates the transaction hash (which will be used as its Id)
    private String calulateHash() {
        sequence++; //increase the sequence to avoid 2 identical transactions having the same hash
        return StringUtil.applySha256(
                StringUtil.getStringFromKey(expediteur) +
                        StringUtil.getStringFromKey(destinataire) +
                        Float.toString(value) + sequence
        );
    }

    //Signs all the data we dont wish to be tampered with.
    public void generateSignature(PrivateKey clePrive) {
        String data = StringUtil.getStringFromKey(expediteur) + StringUtil.getStringFromKey(destinataire) + Float.toString(value)	;
        signature = StringUtil.applyECDSASig(clePrive,data);
    }
    //Verifies the data we signed hasnt been tampered with
    public boolean verifiySignature() {
        String data = StringUtil.getStringFromKey(expediteur) + StringUtil.getStringFromKey(destinataire) + Float.toString(value)	;
        return StringUtil.verifyECDSASig(expediteur, data, signature);
    }

}
