package ch.sebooom.blockchain.application.blockchain.web.api.resources;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */
@Getter
public class BlockRessource {

    private String hash;
    private String hashPrecedent;
    private String merkleRoot;

    private List<TransactionRessource> transactions = new ArrayList<>();

    public BlockRessource(String hash, String hashPrecedent, String merkleRoot) {
        this.hash = hash;
        this.hashPrecedent = hashPrecedent;
        this.merkleRoot = merkleRoot;
    }

    public void addTransaction(TransactionRessource transactionRessource) {
        transactions.add(transactionRessource);
    }
}
