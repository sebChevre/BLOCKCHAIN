package ch.sebooom.blockchain.domain.util;


import org.junit.Assert;
import org.junit.Test;

public class StringUtilTest {

    @Test
    public void testEncrypt () {

        String maChaine = "Hello Seb";

        String maChaineEncrypt = StringUtil.applySha256(maChaine);

        System.out.println(maChaineEncrypt);
        Assert.assertFalse(maChaineEncrypt.isEmpty());
        Assert.assertFalse(maChaine.equals(maChaineEncrypt));
    }
}