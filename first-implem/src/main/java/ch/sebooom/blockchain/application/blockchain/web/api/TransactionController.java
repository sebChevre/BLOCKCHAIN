package ch.sebooom.blockchain.application.blockchain.web.api;

import ch.sebooom.blockchain.application.blockchain.web.api.resources.TransfertOperationRessource;
import ch.sebooom.blockchain.domain.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */
@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final static Logger LOGGER = LoggerFactory.getLogger(TransactionController.class.getName());


    @PostMapping(value = "{from}/{to}/{montant}")
    public ResponseEntity<TransfertOperationRessource> transfert(@PathVariable String from, @PathVariable String to, @PathVariable float montant ){

        LOGGER.info("Transaction from: {}, to: {}, for montant,{}",from,to,montant);

        Transaction transaction;// =

        return ResponseEntity.ok(null);
    }
}
