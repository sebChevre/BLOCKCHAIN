package ch.sebooom.blockchain.application.blockchain.web.api.resources;

import ch.sebooom.blockchain.domain.noeuds.Noeud;
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

    public NoeudsConnectesStatusRessource(Noeud noeud, float balance){
        List<NoeudRessource> noeudRessources = noeud.noeudsConnectes().stream().map(noeudConnecte -> {

            return new NoeudRessource(noeud, balance);
        }).collect(Collectors.toList());

        this.noeudRessources = noeudRessources;
        this.noeudOrigine = new NoeudRessource(noeud,balance);
    }

}
