package ch.sebooom.blockchain.application.blockchain.websocket.client.joining;

import ch.sebooom.blockchain.domain.Node;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */
@Getter
@Setter
public class NodeJoiningMessage {

    private Node node;
    private Date connectedDate;
}
