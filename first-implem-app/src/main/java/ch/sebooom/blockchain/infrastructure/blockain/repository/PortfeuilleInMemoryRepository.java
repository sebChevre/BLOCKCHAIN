package ch.sebooom.blockchain.infrastructure.blockain.repository;

import ch.sebooom.blockchain.domain.noeuds.PorteFeuille;
import ch.sebooom.blockchain.domain.repository.PortefeuilleRepository;
import ch.sebooom.blockchain.infrastructure.momorydb.InMemoryDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
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
    public List<PorteFeuille> getAllPortefeuille() {
        return inMemoryDataSource.getAllPortefeuille();
    }

    @Override
    public Optional<PorteFeuille> getPortefeuilleByAdresse (String adresse) {

        Optional<PorteFeuille> porteFeuilleOptional = inMemoryDataSource.getAllPortefeuille().stream().filter(porteFeuille -> {
            return porteFeuille.adresse.equals(adresse);
        }).findFirst();

        return porteFeuilleOptional;
    }

    @Override
    public void savePortefeuille(PorteFeuille porteFeuille) {
        inMemoryDataSource.addPortefeuille(porteFeuille);
    }
}
