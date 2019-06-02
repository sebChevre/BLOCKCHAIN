package ch.sebooom.blockchain.domain.noeuds;

/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */
public class PortefeuilleDistant {


    public String clePublique;
    public String adresse;
    public String description;


    public static PortefeuilleDistant from(String clePublique, String adresse, String description) {
        PortefeuilleDistant portefeuilleDistant = new PortefeuilleDistant();
        portefeuilleDistant.adresse = adresse;
        portefeuilleDistant.clePublique = clePublique;
        portefeuilleDistant.description = description;
        return portefeuilleDistant;
    }
}
