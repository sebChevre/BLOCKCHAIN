package ch.sebooom.blockchain.domain.util;


import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class CryptoUtilTest {

    private final static int SHA256_SIZE = 64;

    @Test
    public void givenAStringWhenApplySHA256HashThenStringIsHashed () {

        String chaine = "Hello Seb";
        String hashChaine = CryptoUtil.sha256Hash(chaine);

        assertThat(hashChaine)
                .isNotBlank()
                .hasSize(SHA256_SIZE)
                .doesNotContain(chaine);
    }

    @Test
    public void givenTwoHashFromSameInputThenHashMustBeEquals () {

        String chaine = "Hello Seb";
        String hashChaine = CryptoUtil.sha256Hash(chaine);
        String hashChaine2 = CryptoUtil.sha256Hash(chaine);

        assertThat(hashChaine)
                .isNotBlank()
                .hasSize(SHA256_SIZE)
                .doesNotContain(chaine)
                .isEqualTo(hashChaine2);
    }

    @Test
    public void givenTwoHashFromDifferentInputThenHashMustNotBeEquals () {

        String chaine = "Hello Seb";
        String chaine2 = "Hello Seb2";
        String hashChaine = CryptoUtil.sha256Hash(chaine);
        String hashChaine2 = CryptoUtil.sha256Hash(chaine2);

        assertThat(hashChaine)
                .isNotBlank()
                .hasSize(SHA256_SIZE)
                .doesNotContain(chaine)
                .isNotEqualTo(hashChaine2);
    }
}