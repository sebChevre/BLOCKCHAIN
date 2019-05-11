package ch.sebooom.blockchain.domain.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Classe fournissant des fonctions à but cryptographique
 */
public class CryptoUtil {

    private final static Logger LOGGER = LoggerFactory.getLogger(CryptoUtil.class.getName());

    private final static String DIGEST_ALGORITHME = "SHA-256";
    private final static String BYTE_ARRAY_ENCODING = "UTF-8";
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

}
