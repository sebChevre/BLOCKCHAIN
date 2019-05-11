package ch.sebooom.blockchain.domain.util;

import java.security.MessageDigest;

public class CryptoUtil {

    //Applies Sha256 to a string and returns the result.

    /**
     * Application de l'algorithme SHA256 sur la chaine passé en paramètre
     * @param input la chaine a crypter
     * @return la chaine cryptée
     */
    public static String applySha256(String input){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            //Application de  sha256 sur la chaine d'entrée
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer(); // Hash en hexadecimale
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

}
