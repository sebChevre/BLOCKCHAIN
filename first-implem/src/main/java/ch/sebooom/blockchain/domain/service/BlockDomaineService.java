package ch.sebooom.blockchain.domain.service;

import ch.sebooom.blockchain.domain.Block;
import ch.sebooom.blockchain.domain.Transaction;

/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */
public class BlockDomaineService {

    private TransactionDomaineService transactionDomaineService;

    public BlockDomaineService(TransactionDomaineService transactionDomaineService){
        this.transactionDomaineService = transactionDomaineService;
    }

    //Add transactions to this block
    public boolean addTransactionToBlock(Transaction transaction, Block block) {
        //process transaction and check if valid, unless block is genesis block then ignore.
        if(transaction == null) return false;

        if((block.hashPrecedent != "0")) {
            if((transactionDomaineService.processTransaction(transaction) != true)) {
                System.out.println("Transaction failed to process. Discarded.");
                return false;
            }
        }
        block.transactions.add(transaction);
        System.out.println("Transaction Successfully added to Block");
        return true;
    }
}
