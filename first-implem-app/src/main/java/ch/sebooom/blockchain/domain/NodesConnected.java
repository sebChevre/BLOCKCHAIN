package ch.sebooom.blockchain.domain;

import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@ToString
@Component
public class NodesConnected {

    public List<Node> nodesConnected = new ArrayList<>();

    public void addNode(Node node){
        nodesConnected.add(node);
    }

    public List<Node> getConnectedNodes(){
        return nodesConnected;
    }
}
