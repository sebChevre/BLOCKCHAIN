package ch.sebooom.blockchain.application.blockchain.websocket;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.MessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */
public class SSEHandler implements EventHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(SSEHandler.class.getName());

    @Override
    public void onOpen() throws Exception {
        LOGGER.info("SSE Connection open");
    }

    @Override
    public void onClosed() throws Exception {
        LOGGER.info("SSE Connection closed");
    }

    @Override
    public void onMessage(String s, MessageEvent messageEvent) throws Exception {
        LOGGER.info("SSE Message received: {}, event: {}", messageEvent,s);
    }

    @Override
    public void onComment(String s) throws Exception {
        LOGGER.info("SSE Comment reiceived: {}",s);
    }

    @Override
    public void onError(Throwable throwable) {
        LOGGER.info("SSE Error: {}", throwable.getMessage());
    }
}
