package ch.sebooom.blockchain.domain.service;

import ch.sebooom.blockchain.domain.blockchain.Block;
import ch.sebooom.blockchain.domain.blockchain.BlockChain;
import ch.sebooom.blockchain.domain.repository.BlockChainRepository;
import ch.sebooom.blockchain.domain.transaction.Transaction;
import ch.sebooom.blockchain.domain.transaction.TransactionInput;
import ch.sebooom.blockchain.domain.transaction.TransactionOutput;
import ch.sebooom.blockchain.domain.util.CryptoUtil;
import org.springframework.stereotype.Service;

@Service
public class BlockChainDomainService {

    private BlockChainRepository blockChainRepository;

    public BlockChainDomainService(BlockChainRepository blockChainRepository) {
        this.blockChainRepository = blockChainRepository;
    }

    public BlockChain getBlockChain() {

        return blockChainRepository.getBlockChain();
    }

    //Add transactions to this block
    public boolean addTransactionToBlock(Transaction transaction, Block block) {
        //process transaction and check if valid, unless block is genesis block then ignore.
        if(transaction == null) return false;

        if((block.hashPrecedent != "0")) {
            if((processTransaction(transaction) != true)) {
                System.out.println("Transaction failed to process. Discarded.");
                return false;
            }
        }
        block.transactions.add(transaction);
        System.out.println("Transaction Successfully added to Block");
        return true;
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
