package ch.sebooom.blockchain.application.blockchain.websocket;

import ch.sebooom.blockchain.application.blockchain.websocket.client.WebSocketStompSessionHandler;
import ch.sebooom.blockchain.application.blockchain.websocket.client.joining.NodeJoiningMessage;
import ch.sebooom.blockchain.domain.NodesConnected;
import ch.sebooom.blockchain.domain.Noeud;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.stereotype.Controller;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.concurrent.TimeUnit;

/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */
@Controller
public class WebSocketController {

    private final static Logger LOGGER = LoggerFactory.getLogger(WebSocketController.class.getName());

    @Autowired
    NodesConnected nodesConnected;

    @Autowired
    WebSocketStompSessionHandler webSocketStompSessionHandler;
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
    public NodeJoiningMessage joinNodeTopicEndPoint(NodeJoiningMessage message)  {

        LOGGER.info("Node joinig message received : " + message.getNoeud().getNodeId());
        //connectToclient(message.getNoeud());
        nodesConnected.addNode(message.getNoeud());
        return message;
    }

    private void connectToclient(Noeud noeud) {

        WebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        ListenableFuture<StompSession> future
                = stompClient.connect("ws://localhost:" + noeud.getPort() + "/join", webSocketStompSessionHandler);

        try {
            future.get(1, TimeUnit.SECONDS);

            LOGGER.info("Connection to node, port: {}, successfull!",noeud.getHost());
            //return true;
        } catch (Exception e) {
            LOGGER.warn("Connection to node, port: {}, failed! - {}",noeud.getPort(),e.getMessage());
            //return false;
        }
    }
}
