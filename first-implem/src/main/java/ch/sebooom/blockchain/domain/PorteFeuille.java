package ch.sebooom.blockchain.domain;

import java.security.*;
import java.security.spec.ECGenParameterSpec;

public class PorteFeuille {

    public PrivateKey clePrive;
    public PublicKey clePublique;

    public PorteFeuille(){
        generateKeyPair();
    }

    public void generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA","BC");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
            // Initialize the key generator and generate a KeyPair
            keyGen.initialize(ecSpec, random);   //256 bytes provides an acceptable security level
            KeyPair keyPair = keyGen.generateKeyPair();
            // Set the public and private keys from the keyPair
            clePrive = keyPair.getPrivate();
            clePublique = keyPair.getPublic();
        }catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

}
