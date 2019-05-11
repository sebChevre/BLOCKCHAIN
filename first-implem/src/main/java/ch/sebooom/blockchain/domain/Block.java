package ch.sebooom.blockchain.domain;

import ch.sebooom.blockchain.domain.util.StringUtil;

import java.util.Date;

/**
 * Représente un block de la chaine
 */
public class Block {
    private static final int MINING_DIFFICULTY = 5;
    public String hash; //le hash du bloc, la signature
    public String previousHash; //le hash du bloc précédent
    private String data; //données initiale un message
    private long timeStamp; //timestamp de creation
    private int nonce;

    /**
     * Constructuer d'un block
     * @param data les donnée sd ublock
     * @param previousHash le hash du block précédent
     */
    public Block(String data,String previousHash ) {
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        this.hash = calculeHashSignature();
    }

    public String calculeHashSignature () {

        String hashCalcule = StringUtil.applySha256(
                previousHash +
                        timeStamp +
                        Integer.toString(nonce) +
                        data
        );
        return hashCalcule;
    }


    public void mineBlock() {

        String target = new String(new char[MINING_DIFFICULTY]).replace('\0', '0'); //Create a string with difficulty * "0"

        while(!hash.substring( 0, MINING_DIFFICULTY).equals(target)) {
            nonce ++;
            hash = calculeHashSignature();
        }
        System.out.println("Block Mined!!! : " + hash);
    }


}
