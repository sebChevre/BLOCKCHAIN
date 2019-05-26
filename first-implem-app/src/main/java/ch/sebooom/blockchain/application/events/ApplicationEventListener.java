package ch.sebooom.blockchain.application.events;

import ch.sebooom.blockchain.application.blockchain.websocket.client.WebSocketStompSessionHandler;
import ch.sebooom.blockchain.domain.Noeud;
import ch.sebooom.blockchain.domain.NodesConnected;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

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

        WebSocketClient client = new StandardWebSocketClient();

        //iteration sur la liste des noeuds a trouver pour la connection initiale
        getIpsToFindNodes().stream()
                .filter(nodePort -> connectionToNode(nodePort,client))
                .findFirst();
    }

    public List<String> getIpsToFindNodes () {
        return Arrays.asList("9090","9091","9092","9093","9094","9095",
                "9096","9097","9098","9099");
    }


    /**
     * Tentative de connection au serveur ws sur le port passé en paramètre
     * @param nodePort le port
     * @param client le client ws
     * @return un boolean corresponsant à l'état de la connection
     */
    private boolean connectionToNode(String nodePort, WebSocketClient client) {

        LOGGER.info("Trying to connect to node, port: {}",nodePort);
        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        ListenableFuture<StompSession> future
                = stompClient.connect("ws://localhost:" + nodePort + "/join", webSocketStompSessionHandler);

        try {
            future.get(1, TimeUnit.SECONDS);

            LOGGER.info("Connection to node, port: {}, successfull!",nodePort);
            return true;
        } catch (Exception e) {
            LOGGER.warn("Connection to node, port: {}, failed! - {}",nodePort,e.getMessage());
            return false;
        }
    }

}
