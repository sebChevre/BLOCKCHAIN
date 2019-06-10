package ch.sebooom.blockchain.infrastructure.blockain.repository;

import ch.sebooom.blockchain.domain.noeuds.PorteFeuille;
import ch.sebooom.blockchain.domain.noeuds.PortefeuilleDistant;
import ch.sebooom.blockchain.domain.repository.PortefeuilleRepository;
import ch.sebooom.blockchain.infrastructure.momorydb.InMemoryDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */
@Repository
public class PortfeuilleInMemoryRepository implements PortefeuilleRepository {

    @Autowired
    InMemoryDataSource inMemoryDataSource;



    @Override
    public PorteFeuille getPortefeuille() {
        return inMemoryDataSource.getPortefeuille();
    }

    @Override
    public Optional<PortefeuilleDistant> getPortefeuilleDistantByAdresse (String adresse) {

        Optional<PortefeuilleDistant> porteFeuilleOptional = inMemoryDataSource.getNoeud().noeudsConnectes().stream().filter(noeud -> {
            return noeud.adressePortefeuille().equals(adresse);
        }).map(n -> {
            return PortefeuilleDistant.from(n.clePubliquePortefuille(),n.adressePortefeuille(),n.descriptionPortefeuille());
        }).findFirst();

        return porteFeuilleOptional;
    }


}
