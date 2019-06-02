package ch.sebooom.blockchain.domain.noeuds;

import lombok.EqualsAndHashCode;

/**
 * Représente un noeud distant référencé dans l'aplication
 */
@EqualsAndHashCode(of = "identifiant")
public class NoeudDistant {

    /* Informations du noeud  */
    private String identifiant;
    private String hote;
    private int port;
    /* information du portefeuille lié */
    private String clePubliquePortefuille;
    private String adressePortefeuille;
    private String descriptionPortefeuille;


    public static NoeudDistant from(String identifiant, String hote, int port,
            String clePubliquePortefuille, String adressePortefeuille, String descriptionPortefeuille){

        NoeudDistant noeudDistant = new NoeudDistant();
        noeudDistant.identifiant = identifiant;
        noeudDistant.port = port;
        noeudDistant.hote = hote;

        noeudDistant.clePubliquePortefuille = clePubliquePortefuille;
        noeudDistant.adressePortefeuille = adressePortefeuille;
        noeudDistant.descriptionPortefeuille = descriptionPortefeuille;

        return noeudDistant;
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

    public String clePubliquePortefuille () {
        return  this.clePubliquePortefuille;
    }

    public String adressePortefeuille () {
        return this.adressePortefeuille;
    }

    public  String descriptionPortefeuille () {
        return  this.descriptionPortefeuille;
    }

    
}
