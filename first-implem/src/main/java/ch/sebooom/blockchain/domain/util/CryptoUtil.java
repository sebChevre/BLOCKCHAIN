package ch.sebooom.blockchain.domain.util;

import ch.sebooom.blockchain.domain.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * Classe fournissant des fonctions à but cryptographique
 */
public class CryptoUtil {

    private final static Logger LOGGER = LoggerFactory.getLogger(CryptoUtil.class.getName());

    private final static String DIGEST_ALGORITHME = "SHA-256";
    private final static String BYTE_ARRAY_ENCODING = "UTF-8";
    private final static String ECDA_SIGNATURE = "ECDSA";
    private final static String ECDA_PROVIDER = "BC";
    private final static String ECDSA_STD_NAME = "prime192v1";

    public static final int HASH_SIZE = 64;

    /**
     * Application de l'algorithme SHA256 sur la chaine passée en paramètre
     * @param input la chaine a hacher
     * @return la chaine hachée
     */
    public static String sha256Hash(String input){
        try {
            MessageDigest digest = MessageDigest.getInstance(DIGEST_ALGORITHME);

            //Application de  sha256 sur la chaine d'entrée
            byte[] hash = digest.digest(input.getBytes(BYTE_ARRAY_ENCODING));
            LOGGER.debug("Hash (byte array): {}", hash.toString());

            String hexString = toHexFormat(hash);

            LOGGER.debug("Hash (hex format): {}", hexString);
            return hexString;
        }
        catch(NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void checkHashIntegrity (String hash) {
        if(hash == null || hash.isEmpty() || hash.length() != HASH_SIZE){
            throw new NonValidSHA256HashException();
        }
    }

    /**
     * Convertir un tableau de bytes en chaine hexadécimal
     * @param hash le tableau de byte à convertir
     * @return la chaine issu du tableau convertit
     */
    private static String toHexFormat(byte[] hash) {
        StringBuffer hexString = new StringBuffer(); // Hash en hexadecimale

        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static KeyPair generateKeyPair() {
        KeyPairGenerator keyGen = null;
        SecureRandom random = null;

        try {
            keyGen = KeyPairGenerator.getInstance(ECDA_SIGNATURE,ECDA_PROVIDER);
            random = SecureRandom.getInstance("SHA1PRNG");
            //parametrage alogorithme ecdsa
            ECGenParameterSpec ecSpec = new ECGenParameterSpec(ECDSA_STD_NAME);
            // Initialize the key generator and generate a KeyPair
            keyGen.initialize(ecSpec, random);   //256 bytes provides an acceptable security level
            KeyPair keyPair = keyGen.generateKeyPair();
            return keyPair;

        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidAlgorithmParameterException  e) {
            throw new KeyPairException(e);
        }

    }

    //Applies ECDSA Signature and returns the result ( as bytes ).
    public static byte[] applyECDSASignature(PrivateKey clePrive, String input) {
        Signature dsa;
        byte[] output = new byte[0];
        try {
            dsa = Signature.getInstance(ECDA_SIGNATURE, ECDA_PROVIDER);
            dsa.initSign(clePrive);
            byte[] strByte = input.getBytes();
            dsa.update(strByte);
            byte[] realSig = dsa.sign();
            output = realSig;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return output;
    }

    //Verifies a String signature
    public static boolean checkECDSASignature(PublicKey clePublique, String data, byte[] signature) {
        try {
            Signature ecdsaVerify = Signature.getInstance(ECDA_SIGNATURE, ECDA_PROVIDER);
            ecdsaVerify.initVerify(clePublique);
            ecdsaVerify.update(data.getBytes());
            return ecdsaVerify.verify(signature);
        }catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getStringFromKey(Key cle) {
        return Base64.getEncoder().encodeToString(cle.getEncoded());


    }


    //Tacks in array of transactions and returns a merkle root.
    public static String getMerkleRoot(List<Transaction> transactions) {
        int count = transactions.size();
        List<String> previousTreeLayer = new ArrayList<String>();
        for(Transaction transaction : transactions) {
            previousTreeLayer.add(transaction.transactionId);
        }
        List<String> treeLayer = previousTreeLayer;
        while(count > 1) {
            treeLayer = new ArrayList<String>();
            for(int i=1; i < previousTreeLayer.size(); i++) {
                treeLayer.add(sha256Hash(previousTreeLayer.get(i-1) + previousTreeLayer.get(i)));
            }
            count = treeLayer.size();
            previousTreeLayer = treeLayer;
        }
        String merkleRoot = (treeLayer.size() == 1) ? treeLayer.get(0) : "";
        return merkleRoot;
    }

}
