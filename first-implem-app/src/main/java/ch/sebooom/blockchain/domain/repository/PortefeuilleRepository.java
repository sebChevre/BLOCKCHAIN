package ch.sebooom.blockchain.domain.repository;

import ch.sebooom.blockchain.domain.noeuds.PorteFeuille;
import ch.sebooom.blockchain.domain.noeuds.PortefeuilleDistant;

import java.util.Optional;

/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */
public interface PortefeuilleRepository {



    PorteFeuille getPortefeuille();


    Optional<PortefeuilleDistant> getPortefeuilleDistantByAdresse(String adresse);


}
