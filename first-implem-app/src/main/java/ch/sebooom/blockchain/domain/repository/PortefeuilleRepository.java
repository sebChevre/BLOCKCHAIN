package ch.sebooom.blockchain.domain.repository;

import ch.sebooom.blockchain.domain.noeuds.PorteFeuille;

import java.util.List;
import java.util.Optional;

/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */
public interface PortefeuilleRepository {

    List<PorteFeuille> getAllPortefeuille();

    Optional<PorteFeuille> getPortefeuilleByAdresse(String adresse);

    void savePortefeuille(PorteFeuille porteFeuille);
}
