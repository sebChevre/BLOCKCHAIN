package ch.sebooom.blockchain.application.blockchain.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        LOGGER.info("WebSocket Config");

        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat").setAllowedOrigins("http://localhost:3001");
        registry.addEndpoint("/chat").setAllowedOrigins("http://localhost:3001").withSockJS();
        registry.addEndpoint("/join").setAllowedOrigins("http://localhost:3001");
        registry.addEndpoint("/join").setAllowedOrigins("http://localhost:3001").withSockJS();
    }
}
