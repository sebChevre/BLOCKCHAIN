package ch.sebooom.blockchain.application.service;

import ch.sebooom.blockchain.domain.PorteFeuille;

import java.util.Map;

/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */
public interface PortefeuilleService {

    Map<PorteFeuille,Float> getAllPortFeuilleWithBalance();

    void savePortefeuille(PorteFeuille portefeuille);
}
