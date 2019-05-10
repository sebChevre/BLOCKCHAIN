package ch.sebooom.blockchain.domain;

import ch.sebooom.blockchain.domain.util.StringUtil;
import org.junit.Test;

import java.security.Security;

import static org.junit.Assert.assertFalse;


public class PorteFeuilleTest {

    @Test
    public void standardPorteFeuilleTest(){
        //Setup Bouncey castle as a Security Provider
       Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

       PorteFeuille ptf1 = new PorteFeuille();
       PorteFeuille ptf2 = new PorteFeuille();

        System.out.println("Key pair - Private : " + StringUtil.getStringFromKey(ptf1.clePrive) +
                ", publique: " + StringUtil.getStringFromKey(ptf1.clePublique));

        assertFalse(StringUtil.getStringFromKey(ptf1.clePrive).isEmpty());
        assertFalse(StringUtil.getStringFromKey(ptf1.clePublique).isEmpty());

       Transaction t = new Transaction(ptf1.clePublique, ptf2.clePublique,5, null);
       t.generateSignature(ptf1.clePrive);
       System.out.println("Signature verified: " + t.verifiySignature());

    }

}