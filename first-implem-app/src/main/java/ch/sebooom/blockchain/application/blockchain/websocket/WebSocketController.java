package ch.sebooom.blockchain.application.blockchain.websocket;

import ch.sebooom.blockchain.application.blockchain.websocket.client.joining.NodeJoiningMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */
@Controller
public class WebSocketController {

    private final static Logger LOGGER = LoggerFactory.getLogger(WebSocketController.class.getName());

    /**
    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public WebSocketResponse send(WebSocketMessage message) throws Exception {

        LOGGER.info("WS Socket Server msg : " + message.getCommande());
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        return new WebSocketResponse("ok");
    }*/

    @MessageMapping("/join")
    @SendTo("/topic/nodes")
    public NodeJoiningMessage joinNodeTopic(NodeJoiningMessage message) throws Exception {

        LOGGER.info("Node joinig message received : " + message.getNode().getNodeId());

        return message;
    }
}
