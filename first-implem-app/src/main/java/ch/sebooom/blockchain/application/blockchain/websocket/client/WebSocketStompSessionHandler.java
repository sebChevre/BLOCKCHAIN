package ch.sebooom.blockchain.application.blockchain.websocket.client;

import ch.sebooom.blockchain.application.blockchain.websocket.WebSocketMessage;
import ch.sebooom.blockchain.application.blockchain.websocket.WebSocketResponse;
import ch.sebooom.blockchain.application.blockchain.websocket.client.joining.NodeJoiningFrameHandler;
import ch.sebooom.blockchain.application.blockchain.websocket.client.joining.NodeJoiningMessage;
import ch.sebooom.blockchain.domain.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */
@Component
public class WebSocketStompSessionHandler extends StompSessionHandlerAdapter {

    private Logger LOGGER = LoggerFactory.getLogger(WebSocketStompSessionHandler.class);

    @Autowired
    public Node node;

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

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return WebSocketResponse.class;
    }

    /**
    @Override
    public void handleFrame(StompHeaders headers, Object payload) {

        WebSocketResponse msg = (WebSocketResponse) payload;

        LOGGER.info("Message received: " + msg.getCommandReceive());


        //WebSocketMessage msg = (WebSocketMessage) payload;
        //logger.info("Received : " + msg.getText() + " from : " + msg.getFrom());
    }*/

    private NodeJoiningMessage getNodeJoiningMessage () {
        LOGGER.info("Construct node joining message....");
        NodeJoiningMessage nodeJoiningMessage = new NodeJoiningMessage();
        nodeJoiningMessage.setNode(node);
        LOGGER.info("Message ok, with node: " + node);
        return nodeJoiningMessage;
    }


}
