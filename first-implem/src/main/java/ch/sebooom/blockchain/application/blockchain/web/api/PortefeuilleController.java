package ch.sebooom.blockchain.application.blockchain.web.api;

import ch.sebooom.blockchain.application.blockchain.web.api.resources.PortefeuilleRessource;
import ch.sebooom.blockchain.application.service.PortefeuilleService;
import ch.sebooom.blockchain.domain.PorteFeuille;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */
@RestController
@RequestMapping("/portefeuille")
public class PortefeuilleController {


    @Autowired
    PortefeuilleService portefeuilleService;

    @GetMapping
    public ResponseEntity<List<PortefeuilleRessource>> getAllPortefeuille(){


        List<PortefeuilleRessource> portefeuilleRessourceList = new ArrayList<>();
        Map<PorteFeuille,Float> portefeuilWithBalance = portefeuilleService.getAllPortFeuilleWithBalance();

        portefeuilWithBalance.keySet().forEach(porteFeuille -> {

            float balance = portefeuilWithBalance.get(porteFeuille);
            portefeuilleRessourceList.add(new PortefeuilleRessource(porteFeuille.clePublique,balance));
        });

        return ResponseEntity.ok(portefeuilleRessourceList);
    }
}
