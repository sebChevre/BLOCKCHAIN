package ch.sebooom.blockchain.application.blockchain.web.api.resources;

import ch.sebooom.blockchain.domain.Noeud;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NoeudRessource {

    private String nodeId;
    private String host;
    private String port;

    public NoeudRessource(Noeud noeud){
        this.nodeId = noeud.getNodeId();
        this.host = noeud.getHost();
        this.port = noeud.getPort();
    }

    public NoeudRessource(){}

    public String json()  {

        ObjectMapper m = new ObjectMapper();

        try {
            return m.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

}
