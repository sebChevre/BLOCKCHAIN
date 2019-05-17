package ch.sebooom.blockchain.domain;

import ch.sebooom.blockchain.domain.util.CryptoUtil;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PorteFeuille {

    public PrivateKey clePrive;
    public PublicKey clePublique;
    public String adresse;


    public Map<String,TransactionOutput> UTXOs = new HashMap<>(); //only UTXOs owned by this wallet.


    public PorteFeuille(){
        genererCles();
        this.adresse = UUID.randomUUID().toString();
    }

    public void genererCles() {
        try {
            KeyPair keyPair = CryptoUtil.generateKeyPair();
            // On défini les cles proves et publiques
            clePrive = keyPair.getPrivate();
            clePublique = keyPair.getPublic();



        }catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
/**
    public float getBalance() {
        float total = 0;
        for (Map.Entry<String, TransactionOutput> item: BlockChain.UTXOs.entrySet()){
            TransactionOutput UTXO = item.getValue();
            if(UTXO.isMine(clePublique)) { //if output belongs to me ( if coins belong to me )
                UTXOs.put(UTXO.id,UTXO); //add it to our list of unspent transactions.
                total += UTXO.value ;
            }
        }
        return total;
    }
 */
/**
    //Generates and returns a new transaction from this wallet.
    public Transaction sendFunds(PublicKey _recipient,float value ) {
        if(getBalance() < value) { //gather balance and check funds.
            System.out.println("#Not Enough funds to send transaction. Transaction Discarded.");
            return null;
        }
        //create array list of inputs
        List<TransactionInput> inputs = new ArrayList<>();

        float total = 0;
        for (Map.Entry<String, TransactionOutput> item: UTXOs.entrySet()){
            TransactionOutput UTXO = item.getValue();
            total += UTXO.value;
            inputs.add(new TransactionInput(UTXO.id));
            if(total > value) break;
        }

        Transaction newTransaction = new Transaction(clePublique, _recipient , value, inputs);
        newTransaction.generateSignature(clePrive);

        for(TransactionInput input: inputs){
            UTXOs.remove(input.transactionOutputId);
        }
        return newTransaction;
    }
*/
}
