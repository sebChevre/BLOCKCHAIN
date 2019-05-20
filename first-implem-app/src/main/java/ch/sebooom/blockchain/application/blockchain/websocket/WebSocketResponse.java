package ch.sebooom.blockchain.application.blockchain.websocket;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */
@Getter
@Setter
public class WebSocketResponse {

    private String commandReceive;

    public WebSocketResponse(){}

    public WebSocketResponse(String ok) {
        this.commandReceive = ok;
    }
}
