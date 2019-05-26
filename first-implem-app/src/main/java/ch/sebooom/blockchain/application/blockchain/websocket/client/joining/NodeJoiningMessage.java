package ch.sebooom.blockchain.application.blockchain.websocket.client.joining;

import ch.sebooom.blockchain.domain.NodesConnected;
import ch.sebooom.blockchain.domain.Noeud;
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

    private Noeud noeud;
    private Date connectedDate;
    private NodesConnected localConnectedNoeuds;

    public void addConnectedNoeuds(NodesConnected nodesConnected) {
        this.localConnectedNoeuds = nodesConnected;
    }
}
