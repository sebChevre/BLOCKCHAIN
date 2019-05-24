package ch.sebooom.blockchain.application.blockchain.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final static Logger LOGGER = LoggerFactory.getLogger(WebSocketConfig.class.getName());

    @Autowired
    CustomHandShakeHandler customHandShakeHandler;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        LOGGER.info("WebSocket Config");

        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        registry.addEndpoint("/join").setAllowedOrigins("*");
        registry.addEndpoint("/join").setAllowedOrigins("*").withSockJS();
    }
}
