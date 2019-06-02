package ch.sebooom.blockchain.application.blockchain.web.api;

import ch.sebooom.blockchain.application.blockchain.web.api.command.JoinNoeudDistantCommand;
import ch.sebooom.blockchain.application.blockchain.web.api.resources.JoinNoeudDistantReponseRessource;
import ch.sebooom.blockchain.domain.noeuds.Noeud;
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
public class JoinNoeudDistantController {

    private final static Logger LOGGER = LoggerFactory.getLogger(JoinNoeudDistantController.class.getName());

    @Autowired
    NoeudDomaineService noeudDomaineService;
    @Autowired
    PortefeuilleDomaineService portefeuilleDomaineService;


    @PostMapping
    public ResponseEntity<JoinNoeudDistantReponseRessource> joinPeer(
            @RequestBody JoinNoeudDistantCommand joinNoeudDistantCommand){

        LOGGER.info("/join endpoint message: {}", joinNoeudDistantCommand);

        Noeud noeud = noeudDomaineService.getNoeud();
        //ajout du noeud reçu au noeuds distants connus
        noeud.ajouNoeudDistant(joinNoeudDistantCommand.toNoeudDistant());

        JoinNoeudDistantReponseRessource joinNoeudDistantReponseRessource = new JoinNoeudDistantReponseRessource(noeud);

        return ResponseEntity.ok(joinNoeudDistantReponseRessource);
    }
}
