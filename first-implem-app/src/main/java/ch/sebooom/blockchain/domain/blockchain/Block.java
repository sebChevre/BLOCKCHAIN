package ch.sebooom.blockchain.domain.blockchain;

import ch.sebooom.blockchain.domain.transaction.Transaction;
import ch.sebooom.blockchain.domain.util.CryptoUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Représente un block de la chaine
 */
public class Block {

    private final static Logger LOGGER = LoggerFactory.getLogger(Block.class.getName());

    private int blockNumber;
    private static final int DEFAULT_MINING_DIFFICULTY = 1;
    private static final String GENESIS_HASH_PRECEDENT = "0";
    public String hash; //le hash du bloc, la signature
    public String hashPrecedent; //le hash du bloc précédent
    private long timeStamp; //timestamp de creation
    private int nonce;
    public String merkleRoot;
    public List<Transaction> transactions = new ArrayList<>(); //our data will be a simple message.

    /**
     * Constructeur d'un block
     * @param hashPrecedent le hash du block précédent
     */
    public Block(String hashPrecedent, int blockNumber) {

        this.blockNumber = blockNumber;
        if(StringUtils.isBlank(hashPrecedent)){
            throw new IllegalArgumentException("hashPrecednt can't be null or empty");
        }
        checkHashPrecedent(hashPrecedent);


        this.hashPrecedent = hashPrecedent;
        this.timeStamp = new Date().getTime();
        this.hash = calculeHashSignature();
    }

    private void checkHashPrecedent(String hashToVerify){

        if(!hashToVerify.equals(GENESIS_HASH_PRECEDENT)){
            CryptoUtil.checkHashIntegrity(hashToVerify);
        }
    }

    public int blocknumber(){
        return this.blockNumber;
    }
    /**
     * Calcule le hash (signature) du bloc <br/>
     * Le hash est composé ainsi:
     * <ul>
     *     <li>le hashPrecedent</li>
     *     <li>le timeStamp</li>
     *     <li>le nonce, nombre d'itération pour résoudre le calcul</li>
     *     <li>lle message</li>
     * </ul>
     * @return le hashCalcule
     */
    public String calculeHashSignature () {
        LOGGER.trace("Starting calculate block hash");

        String hashCalcule = CryptoUtil.sha256Hash(
                hashPrecedent +
                        timeStamp +
                        Integer.toString(nonce) +
                        merkleRoot
        );

        LOGGER.trace("Hash block calculated");

        return hashCalcule;
    }

    public boolean isBlockIntegre(){
        return hash.equals(calculeHashSignature());
    }
    /**
     * Méthode permettant de miner le bloc, utilise la difficulté par défaut
     */
    public void mine() {
        mine(DEFAULT_MINING_DIFFICULTY);
    }

    /**
     * Méthode permettant de miner le bloc
     */
    public void mine(int difficulty){
        LOGGER.trace("> Starting block mining..., hash : " + hash);
        merkleRoot = CryptoUtil.getMerkleRoot(transactions);

        Long start = new Date().getTime();

        String target = new String(new char[difficulty]).replace('\0', '0'); //Create a string with difficulty * "0"

        while(!hash.substring( 0, difficulty).equals(target)) {
            nonce ++;
            hash = calculeHashSignature();
        }

        LOGGER.trace("Block Mined in: " + (new Date().getTime() - start) +"ms, hash" + hash);
    }
/**
    //Add transactions to this block
    public boolean addTransaction(Transaction transaction) {
        //process transaction and check if valid, unless block is genesis block then ignore.
        if(transaction == null) return false;
        if((hashPrecedent != "0")) {
            if((transaction.processTransaction() != true)) {
                System.out.println("Transaction failed to process. Discarded.");
                return false;
            }
        }
        transactions.add(transaction);
        System.out.println("Transaction Successfully added to Block");
        return true;
    }
*/

}
