package ch.sebooom.blockchain.application.service;

import ch.sebooom.blockchain.domain.PorteFeuille;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.Map;

/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */
public interface PortefeuilleService {

    Map<PorteFeuille,Float> getAllPortFeuilleWithBalance();

    PorteFeuille createPortefeuille();

    ImmutablePair<PorteFeuille, Float> getPortefeuilleByAdresse(String adresse);

    void savePortefeuille(PorteFeuille portefeuille);
}
