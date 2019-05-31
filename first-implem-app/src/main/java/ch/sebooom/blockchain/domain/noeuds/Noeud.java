package ch.sebooom.blockchain.domain.noeuds;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Représente le noeud courant de l'application
 */
public final class Noeud {

    /* Identifiant unique du noeud sur le réseau */
    private String identifiant;
    /* hôte du noeud */
    private String hote;
    /* port du noeud */
    private int port;
    /* Liste des noeuds distants connectés */
    private List<NoeudDistant> noeudsConnectes = new ArrayList<>();
    /* Le prortefuille lié au noeud */
    private PorteFeuille porteFeuille;

    private Noeud(String hote, int port) {
        this.hote = hote;
        this.port = port;
    }

    public static Noeud from(String identifiant, int port){
        Noeud noeud = initNoeud(port);
        noeud.identifiant = identifiant;
        return noeud;
    }

    public static Noeud initNoeud(int port){
        Noeud noeud = new Noeud("localhost",port);
        noeud.identifiant = UUID.randomUUID().toString();
        return noeud;
    }

    public void ajouNoeudDistant(NoeudDistant noeudDistant){
        //le noeud distant doit être différent du noeud courant, et le noeud ne doit pas être dans la liste
        if(!noeudDistant.identifiant().equals(this.identifiant()) &&
                !this.noeudsConnectes.contains(noeudDistant)){
            noeudsConnectes.add(noeudDistant);
        }

    }

    public PorteFeuille porteFeuille () {
        return this.porteFeuille;
    }

    public String identifiant() {
        return this.identifiant;
    }

    public String hote() {
        return this.hote;
    }

    public int port() {
        return this.port;
    }

    public List<NoeudDistant> noeudsConnectes(){
        return noeudsConnectes;
    }

    public void creerPortefeuille(PorteFeuille premierPortefeuille) {
        this.porteFeuille = premierPortefeuille;
    }
}
