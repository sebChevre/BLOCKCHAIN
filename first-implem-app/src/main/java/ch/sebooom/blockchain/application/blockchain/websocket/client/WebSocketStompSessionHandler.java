package ch.sebooom.blockchain.application.blockchain.websocket.client;

import ch.sebooom.blockchain.application.blockchain.websocket.client.joining.NodeJoiningFrameHandler;
import ch.sebooom.blockchain.application.blockchain.websocket.client.joining.NodeJoiningMessage;
import ch.sebooom.blockchain.domain.NodesConnected;
import ch.sebooom.blockchain.domain.Noeud;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.stereotype.Component;

/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */
@Component
public class WebSocketStompSessionHandler extends StompSessionHandlerAdapter {

    private Logger LOGGER = LoggerFactory.getLogger(WebSocketStompSessionHandler.class);

    @Autowired
    public Noeud noeud;

    @Autowired
    NodesConnected nodesConnected;

    @Autowired
    public NodeJoiningFrameHandler nodeJoiningFrameHandler;

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        LOGGER.info("New session established : " + session.getSessionId());
        LOGGER.info("New session established : " + connectedHeaders);

        session.subscribe("/topic/nodes", nodeJoiningFrameHandler);
        LOGGER.info("Subscribed to /topic/nodes");

        //envoi message join
        session.send("/app/join", getNodeJoiningMessage());
        LOGGER.info("Join message sending to /app/join");
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        LOGGER.error("Got an exception", exception);
    }




    private NodeJoiningMessage getNodeJoiningMessage () {
        LOGGER.info("Construct noeud joining message....");
        NodeJoiningMessage nodeJoiningMessage = new NodeJoiningMessage();
        nodeJoiningMessage.setNoeud(noeud);
        nodeJoiningMessage.addConnectedNoeuds(nodesConnected);
        LOGGER.info("Message ok, with noeud: " + noeud);
        return nodeJoiningMessage;
    }


}