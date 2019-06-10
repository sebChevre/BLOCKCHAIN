package ch.sebooom.blockchain.application.blockchain.web.api;

import ch.sebooom.blockchain.application.blockchain.web.api.resources.PortefeuilleDistantRessource;
import ch.sebooom.blockchain.application.blockchain.web.api.resources.PortefeuilleRessource;
import ch.sebooom.blockchain.domain.noeuds.PorteFeuille;
import ch.sebooom.blockchain.domain.noeuds.PortefeuilleDistant;
import ch.sebooom.blockchain.domain.service.PortefeuilleDomaineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */
@RestController
@RequestMapping("/portefeuille")
public class PortefeuilleController {


    @Autowired
    PortefeuilleDomaineService portefeuilleDomaineService;

    @GetMapping
    public ResponseEntity<PortefeuilleRessource> getPortefeuille(){

        PorteFeuille portefeuille = portefeuilleDomaineService.getPortFeuilleWithBalance();

        PortefeuilleRessource portefeuilleRessource = new PortefeuilleRessource(portefeuille);

        return ResponseEntity.ok(portefeuilleRessource);
    }

    @GetMapping("/{adresse}")
    public ResponseEntity<PortefeuilleDistantRessource> getPortefeuilleByAdresse(@PathVariable("adresse") String adresse){

        PortefeuilleDistant porteFeuille = portefeuilleDomaineService.getPortefeuilleDistantByAdresse(adresse);

        return ResponseEntity.ok(new PortefeuilleDistantRessource(porteFeuille));

    }


}
