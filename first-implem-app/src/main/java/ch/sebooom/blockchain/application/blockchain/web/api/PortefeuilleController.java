package ch.sebooom.blockchain.application.blockchain.web.api;

import ch.sebooom.blockchain.application.blockchain.web.api.resources.PortefeuilleRessource;
import ch.sebooom.blockchain.domain.noeuds.PorteFeuille;
import ch.sebooom.blockchain.domain.service.PortefeuilleDomaineService;
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
    PortefeuilleDomaineService portefeuilleDomaineService;

    @GetMapping
    public ResponseEntity<List<PortefeuilleRessource>> getAllPortefeuille(){


        List<PortefeuilleRessource> portefeuilleRessourceList = new ArrayList<>();
        Map<PorteFeuille,Float> portefeuilWithBalance = portefeuilleDomaineService.getAllPortFeuilleWithBalance();

        portefeuilWithBalance.keySet().forEach(porteFeuille -> {

            float balance = portefeuilWithBalance.get(porteFeuille);
            portefeuilleRessourceList.add(new PortefeuilleRessource(porteFeuille,balance));
        });

        return ResponseEntity.ok(portefeuilleRessourceList);
    }

    @GetMapping("/{adresse}")
    public ResponseEntity<PortefeuilleRessource> getPortefeuilleByAdresse(@PathVariable("adresse") String adresse){

        ImmutablePair<PorteFeuille,Float> porteFeuilleByAdresse = portefeuilleDomaineService.getPortefeuilleByAdresse(adresse);

        return ResponseEntity.ok(new PortefeuilleRessource(porteFeuilleByAdresse.left, porteFeuilleByAdresse.right));

    }

    @PostMapping
    public ResponseEntity<PortefeuilleRessource> createPortefeuille () {

        PorteFeuille porteFeuille = portefeuilleDomaineService.createPortefeuille("POST REQUEST");

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new PortefeuilleRessource(porteFeuille,0f));
    }
}
