package ch.sebooom.blockchain.application.blockchain.web.api;

import ch.sebooom.blockchain.application.blockchain.web.api.resources.TransfertOperationRessource;
import ch.sebooom.blockchain.application.service.PortefeuilleService;
import ch.sebooom.blockchain.domain.Block;
import ch.sebooom.blockchain.domain.PorteFeuille;
import ch.sebooom.blockchain.domain.Transaction;
import ch.sebooom.blockchain.domain.repository.BlockChainRepository;
import ch.sebooom.blockchain.domain.service.BlockDomaineService;
import ch.sebooom.blockchain.domain.service.PortefeuilleDomaineService;
import ch.sebooom.blockchain.domain.service.TransactionDomaineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    PortefeuilleService portefeuilleService;

    @Autowired
    BlockChainRepository blockChainRepository;

    @PostMapping(value = "{from}/{to}/{montant}")
    public ResponseEntity<TransfertOperationRessource> transfert(@PathVariable String from,
                                                                 @PathVariable String to,
                                                                 @PathVariable float montant ){

        PortefeuilleDomaineService portefeuilleDomaineService = new PortefeuilleDomaineService(blockChainRepository);
        TransactionDomaineService transactionDomaineService = new TransactionDomaineService(blockChainRepository);
        BlockDomaineService blockDomaineService = new BlockDomaineService(transactionDomaineService);

        LOGGER.info("Transaction from: {}, to: {}, for montant,{}",from,to,montant);

        //chargement et vérification des portefuilles
        PorteFeuille porteFeuilleDebit = portefeuilleService.getPortefeuilleByAdresse(from).left;
        PorteFeuille porteFeuilleCredit = portefeuilleService.getPortefeuilleByAdresse(to).left;

        //Création de la transaction
        Transaction transaction = portefeuilleDomaineService.sendFunds(porteFeuilleDebit,porteFeuilleCredit.clePublique, montant);
        //Créazion du block
        int lastBlockNumber = blockChainRepository.getBlockChain().getLastBlock().blocknumber();
        Block block = new Block(blockChainRepository.getBlockChain().getLastHash(),lastBlockNumber+1);
        blockDomaineService.addTransactionToBlock(transaction,block);
        blockChainRepository.getBlockChain().addBlock(block);

        return ResponseEntity.ok(null);
    }
}
