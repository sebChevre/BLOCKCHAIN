package ch.sebooom.blockchain.domain.service;

import ch.sebooom.blockchain.domain.exception.PortefeuilleNonExistantException;
import ch.sebooom.blockchain.domain.noeuds.PorteFeuille;
import ch.sebooom.blockchain.domain.noeuds.PortefeuilleDistant;
import ch.sebooom.blockchain.domain.repository.BlockChainRepository;
import ch.sebooom.blockchain.domain.repository.NoeudRepository;
import ch.sebooom.blockchain.domain.repository.PortefeuilleRepository;
import ch.sebooom.blockchain.domain.transaction.Transaction;
import ch.sebooom.blockchain.domain.transaction.TransactionInput;
import ch.sebooom.blockchain.domain.transaction.TransactionOutput;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */
@Service
public class PortefeuilleDomaineService {

    BlockChainRepository blockChainRepository;
    NoeudRepository noeudRepository;
    PortefeuilleRepository portefeuilleRepository;

    public PortefeuilleDomaineService(NoeudRepository noeudRepository, BlockChainRepository blockChainRepository, PortefeuilleRepository portefeuilleRepository ){
        this.noeudRepository = noeudRepository;
        this.blockChainRepository = blockChainRepository;
        this.portefeuilleRepository = portefeuilleRepository;
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


    public PorteFeuille getPortFeuilleWithBalance(){

        PorteFeuille porteFeuille = noeudRepository.getNoeud().porteFeuille();

        porteFeuille.balance(getBalanceForPortefeuille(porteFeuille));

        return porteFeuille;

    }


    public PortefeuilleDistant getPortefeuilleDistantByAdresse(String adresse){

        PortefeuilleDistant porteFeuille = portefeuilleRepository.getPortefeuilleDistantByAdresse(adresse).orElseThrow(() ->
                new PortefeuilleNonExistantException(adresse)
        );

        return porteFeuille;
    }

}
