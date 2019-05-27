package ch.sebooom.blockchain.application.blockchain.websocket.client.joining;

import ch.sebooom.blockchain.domain.NodesConnected;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */
@Component
public class NodeJoiningFrameHandler implements StompFrameHandler {

    private Logger LOGGER = LoggerFactory.getLogger(NodeJoiningFrameHandler.class);

    @Autowired
    NodesConnected nodesConnected;

    @Override
    public Type getPayloadType(StompHeaders stompHeaders) {
        return NodeJoiningMessage.class;
    }

    //Gestion des message du serveur
    @Override
    public void handleFrame(StompHeaders stompHeaders, Object payload) {
        NodeJoiningMessage nodeJoiningMessage = (NodeJoiningMessage) payload;
        LOGGER.info("Message reçu : {}, {}:{} ,add noeud to connectedNodes",
                nodeJoiningMessage.getNoeud().getNodeId(),
                nodeJoiningMessage.getNoeud().getHost(),
                nodeJoiningMessage.getNoeud().getPort());
        nodesConnected.addNode(nodeJoiningMessage.getNoeud());

        nodeJoiningMessage.getLocalConnectedNoeuds().nodesConnected.forEach(noeud -> {
            nodesConnected.addNode(noeud);
        });
        LOGGER.info("Connected nodes{}: {}", nodesConnected.hashCode(),nodesConnected);

    }
}
