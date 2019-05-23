package ch.sebooom.blockchain.application.blockchain.web.api.resources;

import ch.sebooom.blockchain.domain.Node;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoeudRessource {

    private String noeudId;
    private String host;
    private String port;

    public NoeudRessource(Node noeud){
        this.noeudId = noeud.getNodeId();
        this.host = noeud.getHost();
        this.port = noeud.getPort();
    }

}
