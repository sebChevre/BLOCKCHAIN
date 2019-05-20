package ch.sebooom.blockchain.application.blockchain.websocket.client.joining;

import ch.sebooom.blockchain.domain.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */
@Component
public class NodeJoiningFrameHandler implements StompFrameHandler {

    private Logger LOGGER = LoggerFactory.getLogger(NodeJoiningFrameHandler.class);

    @Autowired
    public List<Node> nodesConnected;

    @Override
    public Type getPayloadType(StompHeaders stompHeaders) {
        return NodeJoiningMessage.class;
    }

    //Gestion des message du serveur
    @Override
    public void handleFrame(StompHeaders stompHeaders, Object payload) {
        NodeJoiningMessage nodeJoiningMessage = (NodeJoiningMessage) payload;
        LOGGER.info("Message reçu : " + payload + ", add node to connectedNodes");
        nodesConnected.add(nodeJoiningMessage.getNode());
    }
}
