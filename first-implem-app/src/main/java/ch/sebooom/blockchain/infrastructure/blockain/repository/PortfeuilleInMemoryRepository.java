package ch.sebooom.blockchain.infrastructure.blockain.repository;

import ch.sebooom.blockchain.domain.PorteFeuille;
import ch.sebooom.blockchain.domain.repository.PortefeuilleRepository;
import ch.sebooom.blockchain.infrastructure.momorydb.DataSource;
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
    DataSource dataSource;

    @Override
    public List<PorteFeuille> getAllPortefeuille() {
        return dataSource.getAllPortefeuille();
    }

    @Override
    public Optional<PorteFeuille> getPortefeuilleByAdresse (String adresse) {

        Optional<PorteFeuille> porteFeuilleOptional = dataSource.getAllPortefeuille().stream().filter(porteFeuille -> {
            return porteFeuille.adresse.equals(adresse);
        }).findFirst();

        return porteFeuilleOptional;
    }

    @Override
    public void savePortefeuille(PorteFeuille porteFeuille) {
        dataSource.addPortefeuille(porteFeuille);
    }
}
