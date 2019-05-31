package ch.sebooom.blockchain.application.blockchain.web.api;

import ch.sebooom.blockchain.application.blockchain.web.api.resources.NoeudDistantRessource;
import ch.sebooom.blockchain.application.blockchain.web.api.resources.NoeudRessource;
import ch.sebooom.blockchain.domain.noeuds.Noeud;
import ch.sebooom.blockchain.domain.noeuds.PorteFeuille;
import ch.sebooom.blockchain.domain.service.NoeudDomaineService;
import ch.sebooom.blockchain.domain.service.PortefeuilleDomaineService;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/noeud")
public class NoeudsController {

    private final static Logger LOGGER = LoggerFactory.getLogger(NoeudsController.class.getName());

    @Autowired
    NoeudDomaineService noeudDomaineService;
    @Autowired
    PortefeuilleDomaineService portefeuilleDomaineService;

    @GetMapping
    public ResponseEntity<NoeudRessource> getNoeudInfo(){
        Noeud noeud = noeudDomaineService.getNoeud();

        float balance = portefeuilleDomaineService.getBalanceForPortefeuille(noeud.porteFeuille());

        return ResponseEntity.ok(new NoeudRessource(noeud,balance));
    }

    @GetMapping("/connected")
    public ResponseEntity<List<NoeudDistantRessource>> getConnectedNodes () {
        Noeud noeud = noeudDomaineService.getNoeud();

        LOGGER.info("NodesConnected: {}", noeud);


        return ResponseEntity.ok(noeud.noeudsConnectes().stream().map(node -> {

            return new NoeudDistantRessource(node);
        }).collect(Collectors.toList()));


    }
}
