package ch.sebooom.blockchain.application.blockchain.websocket;

import ch.sebooom.blockchain.application.blockchain.web.api.resources.NoeudRessource;
import ch.sebooom.blockchain.domain.NodesConnected;
import ch.sebooom.blockchain.domain.Noeud;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */
@Controller
public class SSEController {

    private final static Logger LOGGER = LoggerFactory.getLogger(SSEController.class.getName());

    @Autowired
    NodesConnected nodesConnected;
    @Autowired
    Noeud noeud;

    @GetMapping("/stream-sse")
    public Flux<ServerSentEvent<String>> streamEvents() {

        return Flux.interval(Duration.ofSeconds(1))
                .map(sequence -> ServerSentEvent.<String> builder()
                        .id(String.valueOf(sequence))
                        .event("hearthbeat-event")
                        .data(new NoeudRessource(noeud).json())
                        .build());
    }

}
