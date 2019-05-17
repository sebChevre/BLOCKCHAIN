package ch.sebooom.blockchain.domain.service;

import ch.sebooom.blockchain.domain.PorteFeuille;
import ch.sebooom.blockchain.domain.Transaction;
import ch.sebooom.blockchain.domain.TransactionInput;
import ch.sebooom.blockchain.domain.TransactionOutput;
import ch.sebooom.blockchain.domain.repository.BlockChainRepository;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */
public class PortefeuilleDomaineService {


    BlockChainRepository blockChainRepository;

    public PortefeuilleDomaineService(BlockChainRepository blockChainRepository){
        this.blockChainRepository = blockChainRepository;
    }



    public float getBalanceForPortefeuille(PorteFeuille portefeuille){

            float total = 0;
            PublicKey clePublique = portefeuille.clePublique;

            for (Map.Entry<String, TransactionOutput> item: blockChainRepository.getBlockChain().UTXOs.entrySet()){
                TransactionOutput UTXO = item.getValue();
                if(UTXO.isMine(clePublique)) { //if output belongs to me ( if coins belong to me )
                    portefeuille.UTXOs.put(UTXO.id,UTXO); //add it to our list of unspent transactions.
                    total += UTXO.value ;
                }
            }
            return total;

    }

    //Generates and returns a new transaction from this wallet.
    public Transaction sendFunds(PorteFeuille expediteur, PublicKey _recipient, float value ) {
        if(getBalanceForPortefeuille(expediteur) < value) { //gather balance and check funds.
            System.out.println("#Not Enough funds to send transaction. Transaction Discarded.");
            return null;
        }
        //create array list of inputs
        List<TransactionInput> inputs = new ArrayList<>();

        float total = 0;
        for (Map.Entry<String, TransactionOutput> item: expediteur.UTXOs.entrySet()){
            TransactionOutput UTXO = item.getValue();
            total += UTXO.value;
            inputs.add(new TransactionInput(UTXO.id));
            if(total > value) break;
        }

        Transaction newTransaction = new Transaction(expediteur.clePublique, _recipient , value, inputs);
        newTransaction.generateSignature(expediteur.clePrive);

        for(TransactionInput input: inputs){
            expediteur.UTXOs.remove(input.transactionOutputId);
        }
        return newTransaction;
    }
}
