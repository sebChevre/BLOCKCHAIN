package ch.sebooom.blockchain.application.blockchain.web.api;

import ch.sebooom.blockchain.application.blockchain.web.api.resources.NoeudRessource;
import ch.sebooom.blockchain.domain.StatusNoeud;
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
@RequestMapping("/noeuds")
public class NoeudsController {


    @Autowired
    StatusNoeud statusNoeud;


    private final static Logger LOGGER = LoggerFactory.getLogger(NoeudsController.class.getName());
    @GetMapping
    public ResponseEntity<NoeudRessource> getNoeudInfo(){
        return ResponseEntity.ok(new NoeudRessource(statusNoeud.noeud));
    }

    @GetMapping("/connected")
    public ResponseEntity<List<NoeudRessource>> getConnectedNodes () {

        LOGGER.info("NodesConnected: {}", statusNoeud);

        return ResponseEntity.ok(statusNoeud.noeudsConnectes().stream().map(node -> {
            return new NoeudRessource(node);
        }).collect(Collectors.toList()));


    }
}
