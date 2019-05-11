package ch.sebooom.blockchain.domain;

import ch.sebooom.blockchain.domain.util.CryptoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Objects;


/**
 * Représente un block de la chaine
 */
public class Block {

    private final static Logger LOGGER = LoggerFactory.getLogger(Block.class.getName());

    private static final int DEFAULT_MINING_DIFFICULTY = 5;
    private static final String GENESIS_HASH_PRECEDENT = "0";
    public String hash; //le hash du bloc, la signature
    public String hashPrecedent; //le hash du bloc précédent
    private String data; //données initiale un message
    private long timeStamp; //timestamp de creation
    private int nonce;

    /**
     * Constructuer d'un block
     * @param data les donnée sd ublock
     * @param hashPrecedent le hash du block précédent
     */
    public Block(String data,String hashPrecedent) {
        Objects.requireNonNull(data,"Data can't be null");
        checkHashPrecedent(hashPrecedent);

        this.data = data;
        this.hashPrecedent = hashPrecedent;
        this.timeStamp = new Date().getTime();
        this.hash = calculeHashSignature();
    }

    private void checkHashPrecedent(String hashToVerify){
        Objects.requireNonNull(hashToVerify);

        if(!hashToVerify.equals(GENESIS_HASH_PRECEDENT)){
            CryptoUtil.checkHashIntegrity(hashToVerify);
        }
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
                        data
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
        LOGGER.trace("Starting block mining..., hash : " + hash);
        Long start = new Date().getTime();

        String target = new String(new char[difficulty]).replace('\0', '0'); //Create a string with difficulty * "0"

        while(!hash.substring( 0, difficulty).equals(target)) {
            nonce ++;
            hash = calculeHashSignature();
        }

        LOGGER.trace("Block Mined in: " + (new Date().getTime() - start) +"ms, hash" + hash);
    }


}
