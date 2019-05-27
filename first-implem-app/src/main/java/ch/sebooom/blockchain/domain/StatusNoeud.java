package ch.sebooom.blockchain.domain;

import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@ToString
@Component
public class StatusNoeud {

    public List<Noeud> noeudsConnectes = new ArrayList<>();

    public Noeud noeud = new Noeud();

    public void addNode(Noeud noeud){
        if(!noeud.equals(this.noeud) && !this.noeudsConnectes.contains(noeud)){
            noeudsConnectes.add(noeud);
        }

    }

    public List<Noeud> noeudsConnectes(){
        return noeudsConnectes;
    }
}
