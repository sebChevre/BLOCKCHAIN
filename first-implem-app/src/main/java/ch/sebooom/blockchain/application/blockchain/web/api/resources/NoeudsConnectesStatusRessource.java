package ch.sebooom.blockchain.application.blockchain.web.api.resources;

import ch.sebooom.blockchain.domain.StatusNoeud;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class NoeudsConnectesStatusRessource {

    private List<NoeudRessource> noeudRessources;

    private NoeudRessource noeudOrigine;

    public NoeudsConnectesStatusRessource(){}

    public NoeudsConnectesStatusRessource(StatusNoeud statusNoeud){
        List<NoeudRessource> noeudRessources = statusNoeud.noeudsConnectes.stream().map(noeud -> {
            return new NoeudRessource(noeud);
        }).collect(Collectors.toList());

        this.noeudRessources = noeudRessources;
        this.noeudOrigine = new NoeudRessource(statusNoeud.noeud);
    }

}
