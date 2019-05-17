package ch.sebooom.blockchain.domain.util;

import java.security.GeneralSecurityException;

/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */
public class KeyPairException extends RuntimeException {
    public KeyPairException(GeneralSecurityException e) {
        super(e);
    }
}
