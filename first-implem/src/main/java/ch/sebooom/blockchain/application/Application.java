package ch.sebooom.blockchain.application;

import ch.sebooom.blockchain.application.service.BlockchainService;
import ch.sebooom.blockchain.application.service.PortefeuilleService;
import ch.sebooom.blockchain.domain.*;
import ch.sebooom.blockchain.domain.repository.BlockChainRepository;
import ch.sebooom.blockchain.domain.service.BlockDomaineService;
import ch.sebooom.blockchain.domain.service.PortefeuilleDomaineService;
import ch.sebooom.blockchain.domain.service.TransactionDomaineService;
import ch.sebooom.blockchain.domain.util.CryptoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;
import java.security.Security;

/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */
@SpringBootApplication
@ComponentScan(basePackages = "ch.sebooom.blockchain")
public class Application {

    //@Autowired
    //public BlockChain blockChain;

    @Autowired
    BlockchainService blockchainService;

    @Autowired
    PortefeuilleService portefeuilleService;

    @Autowired
    BlockChainRepository blockChainRepository;


    private final static Logger LOGGER = LoggerFactory.getLogger(Application.class.getName());



    public  static void main(String args[]) {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider()); //Setup Bouncey castle as a Security Provider

        SpringApplication.run(Application.class);
    }

    @PostConstruct
    public void initApplication () {
        TransactionDomaineService transactionDomaineService = new TransactionDomaineService(blockChainRepository);
        BlockDomaineService blockDomaineService = new BlockDomaineService(transactionDomaineService);
        PortefeuilleDomaineService portefeuilleDomaineService = new PortefeuilleDomaineService(blockChainRepository);

        LOGGER.info("> Starting application init...");
        LOGGER.info("> Blockchain generated auto with datasource!");

        LOGGER.info("> Creating portefeuille-base....");
        PorteFeuille portefeuilleBase = new PorteFeuille();
        portefeuilleService.savePortefeuille(portefeuilleBase);
        LOGGER.info("> Portefeuille portefeuille-base créé *****");
        LOGGER.info("> Clé publique, {}", CryptoUtil.getStringFromKey(portefeuilleBase.clePublique));

        LOGGER.info("> Creating premier portefeuille....");
        PorteFeuille premierPortefeuille = new PorteFeuille();
        portefeuilleService.savePortefeuille(premierPortefeuille);
        LOGGER.info("> Portefeuille premier-portefeuille créé *****");
        LOGGER.info("> Clé publique, {}", CryptoUtil.getStringFromKey(premierPortefeuille.clePublique));

        LOGGER.info("> Creating deuxieme portefeuille....");
        PorteFeuille deuxiemePortefeuille = new PorteFeuille();
        portefeuilleService.savePortefeuille(deuxiemePortefeuille);
        LOGGER.info("> Portefeuille deuxieme-portefeuille créé *****");
        LOGGER.info("> Clé publique, {}", CryptoUtil.getStringFromKey(deuxiemePortefeuille.clePublique));

        LOGGER.info("> Création transaction genesis...");
        Transaction genesisTransaction = genererTransactionGenesis(portefeuilleBase, premierPortefeuille);
        LOGGER.info("> Création block genesis...");
        genererBlockGenesis(blockDomaineService, genesisTransaction);
        LOGGER.info("> Genesis block added!");

        int lastBlockNumber = blockChainRepository.getBlockChain().getLastBlock().blocknumber();
        Block block = new Block(blockChainRepository.getBlockChain().getLastHash(),lastBlockNumber+1);

        Transaction firstTransaction = portefeuilleDomaineService.sendFunds(premierPortefeuille,deuxiemePortefeuille.clePublique, 10f);
        blockDomaineService.addTransactionToBlock(firstTransaction,block);

        Transaction secTransaction = portefeuilleDomaineService.sendFunds(premierPortefeuille,deuxiemePortefeuille.clePublique, 15f);
        blockDomaineService.addTransactionToBlock(secTransaction,block);
        blockchainService.getBlockChain().addBlock(block);

    }

    private void genererBlockGenesis(BlockDomaineService blockDomaineService, Transaction genesisTransaction) {
        Block genesis = new Block("0",0);
        blockDomaineService.addTransactionToBlock(genesisTransaction,genesis);
        blockchainService.getBlockChain().addBlock(genesis);
    }

    private Transaction genererTransactionGenesis(PorteFeuille portefeuilleBase, PorteFeuille premierPortefeuille) {
        Transaction genesisTransaction = new Transaction(portefeuilleBase.clePublique, premierPortefeuille.clePublique, 100f);
        genesisTransaction.generateSignature(portefeuilleBase.clePrive);	 //manually sign the genesis transaction
        genesisTransaction.transactionId = "0"; //manually set the transaction id
        genesisTransaction.outputs.add(new TransactionOutput(genesisTransaction.destinataire, genesisTransaction.value, genesisTransaction.transactionId)); //manually add the Transactions Output
        blockchainService.getBlockChain().UTXOs.put(genesisTransaction.outputs.get(0).id, genesisTransaction.outputs.get(0)); //its important to store our first transaction in the UTXOs list.

        LOGGER.info("> Transaction genesis ok: {}",genesisTransaction.getJsonRepresentation());
        return genesisTransaction;
    }


}
