package ch.sebooom.blockchain.domain.repository;

import ch.sebooom.blockchain.domain.PorteFeuille;

import java.util.List;

/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */
public interface PortefeuilleRepository {

    List<PorteFeuille> getAllPortefeuille();

    void savePortefeuille(PorteFeuille porteFeuille);
}
