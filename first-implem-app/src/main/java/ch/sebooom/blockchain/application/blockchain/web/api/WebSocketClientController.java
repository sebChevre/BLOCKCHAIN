package ch.sebooom.blockchain.application.blockchain.web.api;

import ch.sebooom.blockchain.application.blockchain.websocket.client.WebSocketStompSessionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */
@RestController
public class WebSocketClientController {

    private final static Logger LOGGER = LoggerFactory.getLogger(WebSocketClientController.class.getName());

    @RequestMapping("/ws-client")
    public ResponseEntity wsClientConnect () {
        WebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);

        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        StompSessionHandler sessionHandler = new WebSocketStompSessionHandler();
        stompClient.connect("ws://localhost:8080/chat", sessionHandler);

       return ResponseEntity.ok("ok");
    }
}
