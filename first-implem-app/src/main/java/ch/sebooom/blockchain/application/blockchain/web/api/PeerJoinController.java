package ch.sebooom.blockchain.application.blockchain.web.api;

import ch.sebooom.blockchain.application.blockchain.web.api.resources.NoeudRessource;
import ch.sebooom.blockchain.application.blockchain.web.api.resources.NoeudsConnectesStatusRessource;
import ch.sebooom.blockchain.domain.noeuds.Noeud;
import ch.sebooom.blockchain.domain.noeuds.NoeudDistant;
import ch.sebooom.blockchain.domain.service.NoeudDomaineService;
import ch.sebooom.blockchain.domain.service.PortefeuilleDomaineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/join")
public class PeerJoinController {

    private final static Logger LOGGER = LoggerFactory.getLogger(PeerJoinController.class.getName());

    @Autowired
    NoeudDomaineService noeudDomaineService;
    @Autowired
    PortefeuilleDomaineService portefeuilleDomaineService;


    @PostMapping
    public ResponseEntity<NoeudsConnectesStatusRessource> joinPeer(
            @RequestBody NoeudRessource noeudJoining){

        LOGGER.info("/join endpoint message: {}", noeudJoining);

        Noeud noeud = noeudDomaineService.getNoeud();

        noeud.ajouNoeudDistant(NoeudDistant.from(
                noeudJoining.getNoeudId(),
                noeudJoining.getPort(),
                noeudJoining.getPortefeuille()));

        float balance = portefeuilleDomaineService.getBalanceForPortefeuille(noeud.porteFeuille());

        NoeudsConnectesStatusRessource noeudsConnectesStatusRessource = new NoeudsConnectesStatusRessource(noeud,balance);

        return ResponseEntity.ok(noeudsConnectesStatusRessource);
    }
}
