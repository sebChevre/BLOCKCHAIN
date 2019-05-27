package ch.sebooom.blockchain.application.events;

import ch.sebooom.blockchain.application.blockchain.websocket.SSEHandler;
import ch.sebooom.blockchain.application.blockchain.websocket.client.WebSocketStompSessionHandler;
import ch.sebooom.blockchain.domain.NodesConnected;
import ch.sebooom.blockchain.domain.Noeud;
import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.EventSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class ApplicationEventListener {


    @Autowired
    Noeud noeud;
    @Autowired
    Environment environment;
    @Autowired
    WebSocketStompSessionHandler webSocketStompSessionHandler;
    @Autowired
    NodesConnected nodesConnected;

    private final static Logger LOGGER = LoggerFactory.getLogger(ApplicationEventListener.class.getName());


    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationready(){

        finaliserConfigurationNoeud();

        connectToNode();

    }

    /**
     * Finalisation de la configuration du noeud
     */
    private void finaliserConfigurationNoeud() {
        noeud.setNodeId(UUID.randomUUID().toString());
        noeud.setPort(environment.getProperty("local.server.port"));
    }

    /**
     * Démarrage de la tentative de connection aux port définis
     */
    private void connectToNode() {

        EventHandler eventHandler = new SSEHandler();



        //iteration sur la liste des noeuds a trouver pour la connection initiale
        getIpsToFindNodes().stream()
                .filter(noeudPort ->
                        noeudPort != noeud.getPort()
                    ).forEach(noeudPort -> {
                        connectionToNode(noeudPort,eventHandler);
        });


    }

    public List<String> getIpsToFindNodes () {
        return Arrays.asList("9090","9091","9092","9093","9094","9095",
                "9096","9097","9098","9099");
    }


    /**
     * Tentative de connection au serveur ws sur le port passé en paramètre
     * @param nodePort le port
     * @param eventHandler le gestionnaire d'écévnements
     * @return un boolean corresponsant à l'état de la connection
     */
    private void connectionToNode(String nodePort, EventHandler eventHandler) {

        LOGGER.info("Trying to connect to noeud, port: {}",nodePort);

        String url = String.format("http://localhost:%s/sse-stream",nodePort);
        EventSource.Builder builder = new EventSource.Builder(eventHandler, URI.create(url));
        builder.connectTimeoutMs(1000);
        builder.

        try (EventSource eventSource = builder.build()) {
            eventSource.setReconnectionTimeMs(3000);
            eventSource.start();

            TimeUnit.MINUTES.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
