package ch.sebooom.blockchain.domain.service;

import ch.sebooom.blockchain.domain.BlockChain;
import ch.sebooom.blockchain.domain.Transaction;
import ch.sebooom.blockchain.domain.TransactionInput;
import ch.sebooom.blockchain.domain.TransactionOutput;
import ch.sebooom.blockchain.domain.repository.BlockChainRepository;
import ch.sebooom.blockchain.domain.util.CryptoUtil;

/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */
public class TransactionDomaineService {

    private BlockChainRepository blockChainRepository;

    public TransactionDomaineService(BlockChainRepository blockChainRepository){
        this.blockChainRepository = blockChainRepository;
    }

    //Returns true if new transaction could be created.
    public boolean processTransaction(Transaction transaction) {

        if(verifiySignature(transaction) == false) {
            System.out.println("#Transaction Signature failed to verify");
            return false;
        }

        //gather transaction inputs (Make sure they are unspent):
        for(TransactionInput i : transaction.inputs) {
            i.UTXO = blockChainRepository.getBlockChain().UTXOs.get(i.transactionOutputId);
        }

        //check if transaction is valid:
        if(transaction.getInputsValue() < BlockChain.minimumTransaction) {
            System.out.println("#Transaction Inputs to small: " + transaction.getInputsValue());
            return false;
        }

        //generate transaction outputs:
        float leftOver = transaction.getInputsValue() - transaction.value; //get value of inputs then the left over change:
        transaction.transactionId = transaction.calulateHash();
        transaction.outputs.add(new TransactionOutput( transaction.destinataire, transaction.value,transaction.transactionId)); //send value to recipient
        transaction.outputs.add(new TransactionOutput( transaction.expediteur, leftOver,transaction.transactionId)); //send the left over 'change' back to sender

        //add outputs to Unspent list
        for(TransactionOutput o : transaction.outputs) {
            blockChainRepository.getBlockChain().UTXOs.put(o.id , o);
        }

        //remove transaction inputs from UTXO lists as spent:
        for(TransactionInput i : transaction.inputs) {
            if(i.UTXO == null) continue; //if Transaction can't be found skip it
            blockChainRepository.getBlockChain().UTXOs.remove(i.UTXO.id);
        }

        return true;
    }

    //Verifies the data we signed hasnt been tampered with
    public boolean verifiySignature(Transaction transaction) {
        String data = CryptoUtil.getStringFromKey(transaction.expediteur) + CryptoUtil.getStringFromKey(transaction.destinataire) + Float.toString(transaction.value)	;
        return CryptoUtil.checkECDSASignature(transaction.expediteur, data, transaction.signature);
    }

}
