package ch.sebooom.blockchain.application.blockchain.web.api.resources;

import ch.sebooom.blockchain.domain.transaction.TransactionInput;
import lombok.Getter;

/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */
@Getter
public class InputRessource {

    private String transactionOutputId;
    private OutputRessource uxto;

    public InputRessource(TransactionInput input) {
        this.transactionOutputId = input.transactionOutputId;
        this.uxto = new OutputRessource(input.UTXO);
    }
}
