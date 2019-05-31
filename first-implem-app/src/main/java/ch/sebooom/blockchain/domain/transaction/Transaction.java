package ch.sebooom.blockchain.domain.transaction;

import ch.sebooom.blockchain.domain.util.CryptoUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Transaction {

    @Expose
    public String transactionId; // hash
    @JsonIgnore
    public PublicKey expediteur; // cle publique expediteur
    @JsonIgnore
    public PublicKey destinataire; // cle publique destinataire
    public float value;//montant de la transaction
    public byte[] signature; // previent le fait que personne d'autre ne peut utiliser mon protefeuille
    public List<TransactionInput> inputs = new ArrayList<TransactionInput>();
    public List<TransactionOutput> outputs = new ArrayList<TransactionOutput>();
    public String identifiant;

    private static int sequence = 0; // compteur du nombre de transaction générées

    // Constructor:
    public Transaction(PublicKey from, PublicKey to, float value,  List<TransactionInput> inputs) {
        this.expediteur = from;
        this.destinataire = to;
        this.value = value;
        this.inputs = inputs;
        this.identifiant = UUID.randomUUID().toString();
    }

    // Constructor:
    public Transaction(PublicKey from, PublicKey to, float value) {
        this(from,to,value,new ArrayList<>());
    }



    // This Calculates the transaction hash (which will be used as its Id)
    public String calulateHash() {
        sequence++; //increase the sequence to avoid 2 identical transactions having the same hash
        return CryptoUtil.sha256Hash(
                CryptoUtil.getStringFromKey(expediteur) +
                        CryptoUtil.getStringFromKey(destinataire) +
                        Float.toString(value) + sequence
        );
    }

    //Signs all the data we dont wish to be tampered with.
    public void generateSignature(PrivateKey clePrive) {
        String data = CryptoUtil.getStringFromKey(expediteur) + CryptoUtil.getStringFromKey(destinataire) + Float.toString(value)	;
        signature = CryptoUtil.applyECDSASignature(clePrive,data);
    }
    //Verifies the data we signed hasnt been tampered with
    public boolean verifiySignature() {
        String data = CryptoUtil.getStringFromKey(expediteur) + CryptoUtil.getStringFromKey(destinataire) + Float.toString(value)	;
        return CryptoUtil.checkECDSASignature(expediteur, data, signature);
    }

/**
    //Returns true if new transaction could be created.
    public boolean processTransaction() {

        if(verifiySignature() == false) {
            System.out.println("#Transaction Signature failed to verify");
            return false;
        }

        //gather transaction inputs (Make sure they are unspent):
        for(TransactionInput i : inputs) {
            i.UTXO = BlockChain.UTXOs.get(i.transactionOutputId);
        }

        //check if transaction is valid:
        if(getInputsValue() < BlockChain.minimumTransaction) {
            System.out.println("#Transaction Inputs to small: " + getInputsValue());
            return false;
        }

        //generate transaction outputs:
        float leftOver = getInputsValue() - value; //get value of inputs then the left over change:
        transactionId = calulateHash();
        outputs.add(new TransactionOutput( this.destinataire, value,transactionId)); //send value to recipient
        outputs.add(new TransactionOutput( this.expediteur, leftOver,transactionId)); //send the left over 'change' back to sender

        //add outputs to Unspent list
        for(TransactionOutput o : outputs) {
            BlockChain.UTXOs.put(o.id , o);
        }

        //remove transaction inputs from UTXO lists as spent:
        for(TransactionInput i : inputs) {
            if(i.UTXO == null) continue; //if Transaction can't be found skip it
            BlockChain.UTXOs.remove(i.UTXO.id);
        }

        return true;
    }*/

    //returns sum of inputs(UTXOs) values
    public float getInputsValue() {
        float total = 0;
        for(TransactionInput i : inputs) {
            if(i.UTXO == null) continue; //if Transaction can't be found skip it
            total += i.UTXO.value;
        }
        return total;
    }

    public String getJsonRepresentation () {

        return new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create().toJson(this);
    }
    //returns sum of outputs:
    public float getOutputsValue() {
        float total = 0;
        for(TransactionOutput o : outputs) {
            total += o.value;
        }
        return total;
    }

}
