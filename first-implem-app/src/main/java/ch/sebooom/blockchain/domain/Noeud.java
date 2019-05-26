package ch.sebooom.blockchain.domain;

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
/**
 * Un noeud est le client de l'application <br/>
 *
 */
public class Noeud {

    private String nodeId;
    private String port;
    private String host = "localhost";


}
