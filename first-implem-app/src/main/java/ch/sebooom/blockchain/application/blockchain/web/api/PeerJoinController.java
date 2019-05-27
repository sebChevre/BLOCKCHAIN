package ch.sebooom.blockchain.application.blockchain.web.api;

import ch.sebooom.blockchain.application.blockchain.web.api.resources.NoeudRessource;
import ch.sebooom.blockchain.application.blockchain.web.api.resources.NoeudsConnectesStatusRessource;
import ch.sebooom.blockchain.domain.StatusNoeud;
import ch.sebooom.blockchain.domain.Noeud;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/join")
public class PeerJoinController {

    private final static Logger LOGGER = LoggerFactory.getLogger(PeerJoinController.class.getName());

    @Autowired
    StatusNoeud statusNoeud;


    @PostMapping
    public ResponseEntity<NoeudsConnectesStatusRessource> joinPeer(
            @RequestBody NoeudRessource noeudJoining){

        LOGGER.info("/join endpoint message: {}", noeudJoining);

        statusNoeud.addNode(new Noeud(noeudJoining.getNodeId(),noeudJoining.getPort()));

        NoeudsConnectesStatusRessource noeudsConnectesStatusRessource = new NoeudsConnectesStatusRessource(statusNoeud);

        return ResponseEntity.ok(noeudsConnectesStatusRessource);
    }
}
