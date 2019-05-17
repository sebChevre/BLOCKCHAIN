package ch.sebooom.blockchain.application.service.impl;

import ch.sebooom.blockchain.application.service.PortefeuilleService;
import ch.sebooom.blockchain.domain.PorteFeuille;
import ch.sebooom.blockchain.domain.exception.PortefeuilleNonExistantException;
import ch.sebooom.blockchain.domain.service.PortefeuilleDomaineService;
import ch.sebooom.blockchain.domain.repository.BlockChainRepository;
import ch.sebooom.blockchain.domain.repository.PortefeuilleRepository;
import org.apache.commons.lang3.tuple.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */
@Service
public class PortefeuilleServiceImpl implements PortefeuilleService {

    @Autowired
    PortefeuilleRepository portefeuilleRepository;

    @Autowired
    BlockChainRepository blockChainRepository;

    @Override
    public Map<PorteFeuille,Float> getAllPortFeuilleWithBalance(){

        PortefeuilleDomaineService portefeuilleDomaineService = new PortefeuilleDomaineService(blockChainRepository);
        Map<PorteFeuille,Float> portefeuilleWithBalance = new HashMap<>();

        List<PorteFeuille> porteFeuilleList = portefeuilleRepository.getAllPortefeuille();;

        porteFeuilleList.forEach(porteFeuille -> {
            portefeuilleWithBalance.put(porteFeuille,portefeuilleDomaineService.getBalanceForPortefeuille(porteFeuille));
        });
        return portefeuilleWithBalance;

    }

    @Override
    public PorteFeuille createPortefeuille() {
        PorteFeuille newPortefeuille = new PorteFeuille();
        portefeuilleRepository.savePortefeuille(newPortefeuille);
        return newPortefeuille;

    }

    @Override
    public ImmutablePair<PorteFeuille, Float> getPortefeuilleByAdresse(String adresse){

        PorteFeuille porteFeuille = portefeuilleRepository.getPortefeuilleByAdresse(adresse).orElseThrow(() ->
                new PortefeuilleNonExistantException(adresse)
        );

        PortefeuilleDomaineService portefeuilleDomaineService = new PortefeuilleDomaineService(blockChainRepository);
        float balance = portefeuilleDomaineService.getBalanceForPortefeuille(porteFeuille);

        ImmutablePair<PorteFeuille,Float> portefeuilleWithBalance = new ImmutablePair<>(porteFeuille,balance);

        return portefeuilleWithBalance;
    }

    @Override
    public void savePortefeuille(PorteFeuille portefeuille){
        portefeuilleRepository.savePortefeuille(portefeuille);
    }
}
