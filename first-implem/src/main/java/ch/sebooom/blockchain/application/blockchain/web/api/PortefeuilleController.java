package ch.sebooom.blockchain.application.blockchain.web.api;

import ch.sebooom.blockchain.application.blockchain.web.api.resources.PortefeuilleRessource;
import ch.sebooom.blockchain.application.service.PortefeuilleService;
import ch.sebooom.blockchain.domain.PorteFeuille;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */
@RestController
@RequestMapping("/portefeuilles")
public class PortefeuilleController {


    @Autowired
    PortefeuilleService portefeuilleService;

    @GetMapping
    public ResponseEntity<List<PortefeuilleRessource>> getAllPortefeuille(){


        List<PortefeuilleRessource> portefeuilleRessourceList = new ArrayList<>();
        Map<PorteFeuille,Float> portefeuilWithBalance = portefeuilleService.getAllPortFeuilleWithBalance();

        portefeuilWithBalance.keySet().forEach(porteFeuille -> {

            float balance = portefeuilWithBalance.get(porteFeuille);
            portefeuilleRessourceList.add(new PortefeuilleRessource(porteFeuille.clePublique,porteFeuille.adresse,balance));
        });

        return ResponseEntity.ok(portefeuilleRessourceList);
    }

    @GetMapping("/{adresse}")
    public ResponseEntity<PortefeuilleRessource> getPortefeuilleByAdresse(@PathVariable("adresse") String adresse){

        ImmutablePair<PorteFeuille,Float> porteFeuilleByAdresse = portefeuilleService.getPortefeuilleByAdresse(adresse);

        return ResponseEntity.ok(new PortefeuilleRessource(porteFeuilleByAdresse.left.clePublique,
                porteFeuilleByAdresse.left.adresse,
                porteFeuilleByAdresse.right));

    }

    @PostMapping
    public ResponseEntity<PortefeuilleRessource> createPortefeuille () {

        PorteFeuille porteFeuille = portefeuilleService.createPortefeuille();

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new PortefeuilleRessource(porteFeuille.clePublique,porteFeuille.adresse,0f));
    }
}
