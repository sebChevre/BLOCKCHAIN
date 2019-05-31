package ch.sebooom.blockchain.application.blockchain.web.api;

import ch.sebooom.blockchain.application.blockchain.web.api.resources.TransfertOperationRessource;
import ch.sebooom.blockchain.domain.blockchain.Block;
import ch.sebooom.blockchain.domain.noeuds.PorteFeuille;
import ch.sebooom.blockchain.domain.service.BlockChainDomainService;
import ch.sebooom.blockchain.domain.transaction.Transaction;
import ch.sebooom.blockchain.domain.service.PortefeuilleDomaineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

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
    PortefeuilleDomaineService portefeuilleDomaineService;

    @Autowired
    BlockChainDomainService blockChainDomainService;

    @PostMapping(value = "{from}/{to}/{montant}")
    public ResponseEntity<TransfertOperationRessource> transfert(@PathVariable String from,
                                                                 @PathVariable String to,
                                                                 @PathVariable float montant ){

        //PortefeuilleDomaineService portefeuilleDomaineService = new PortefeuilleDomaineService(blockChainRepository);
        //TransactionDomaineService transactionDomaineService = new TransactionDomaineService(blockChainRepository);
        //BlockDomaineService blockDomaineService = new BlockDomaineService(transactionDomaineService);

        LOGGER.info("Transaction from: {}, to: {}, for montant,{}",from,to,montant);

        //chargement et vérification des portefuilles
        PorteFeuille porteFeuilleDebit = portefeuilleDomaineService.getPortefeuilleByAdresse(from).left;
        PorteFeuille porteFeuilleCredit = portefeuilleDomaineService.getPortefeuilleByAdresse(to).left;

        //Création de la transaction
        Transaction transaction = portefeuilleDomaineService.sendFunds(porteFeuilleDebit,porteFeuilleCredit.clePublique, montant);
        //Créazion du block
        Date d = new Date();
        LOGGER.info("Block creation");
        int lastBlockNumber = blockChainDomainService.getBlockChain().getLastBlock().blocknumber();
        Block block = new Block(blockChainDomainService.getBlockChain().getLastHash(),lastBlockNumber+1);
        blockChainDomainService.addTransactionToBlock(transaction,block);
        blockChainDomainService.getBlockChain().addBlock(block);
        LOGGER.info("Block mined in " + (new Date().getTime() - d.getTime()));
        return ResponseEntity.ok(null);
    }
}
