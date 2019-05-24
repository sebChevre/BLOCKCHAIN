package ch.sebooom.blockchain.application.blockchain.websocket;

import ch.sebooom.blockchain.application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeFailureException;
import org.springframework.web.socket.server.HandshakeHandler;

import java.util.Map;

@Component
public class CustomHandShakeHandler implements HandshakeHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(CustomHandShakeHandler.class.getName());

    @Override
    public boolean doHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws HandshakeFailureException {

        LOGGER.info(serverHttpRequest.toString());

        return false;
    }
}
