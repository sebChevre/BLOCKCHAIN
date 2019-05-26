package ch.sebooom.blockchain.domain;

import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@ToString
@Component
public class NodesConnected {

    public List<Noeud> nodesConnected = new ArrayList<>();

    public void addNode(Noeud noeud){
        nodesConnected.add(noeud);
    }

    public List<Noeud> getConnectedNodes(){
        return nodesConnected;
    }
}
