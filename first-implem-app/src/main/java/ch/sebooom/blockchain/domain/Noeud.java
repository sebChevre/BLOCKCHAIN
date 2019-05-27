package ch.sebooom.blockchain.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "nodeId")
/**
 * Un noeud est le client de l'application <br/>
 *
 */
public class Noeud {

    private String nodeId;
    private String port;
    private String host = "localhost";

    public Noeud(){}

    public Noeud(String nodeId, String port){
        this.nodeId = nodeId;
        this.port = port;
    }

}
