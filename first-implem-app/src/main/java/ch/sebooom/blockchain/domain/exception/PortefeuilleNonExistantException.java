package ch.sebooom.blockchain.domain.exception;

public class PortefeuilleNonExistantException extends RuntimeException{

    public PortefeuilleNonExistantException(String adresse) {
        super("Portefeuille adresse: [" + adresse +"] cant be found");
    }
}
