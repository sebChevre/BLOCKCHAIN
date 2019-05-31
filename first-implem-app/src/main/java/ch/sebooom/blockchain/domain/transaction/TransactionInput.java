package ch.sebooom.blockchain.domain.transaction;

public class TransactionInput {

    public String transactionOutputId; //Reference to TransactionOutputs -> transactionId
    public TransactionOutput UTXO; //Contient les transaction non dépensés

    public TransactionInput(String transactionOutputId) {
        this.transactionOutputId = transactionOutputId;
    }


}
