package ch.sebooom.blockchain.application;

import ch.sebooom.blockchain.application.blockchain.websocket.client.WebSocketStompSessionHandler;
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import javax.annotation.PostConstruct;
import java.security.Security;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */
@SpringBootApplication
@ComponentScan(basePackages = "ch.sebooom.blockchain")
@EnableScheduling
public class Application {

    //@Autowired
    //public BlockChain blockChain;

    @Autowired
    BlockchainService blockchainService;

    @Autowired
    PortefeuilleService portefeuilleService;

    @Autowired
    BlockChainRepository blockChainRepository;

    @Autowired
    WebSocketStompSessionHandler webSocketStompSessionHandler;






    private final static Logger LOGGER = LoggerFactory.getLogger(Application.class.getName());

    @Bean
    public List<Node> nodesConnected(){
        return new ArrayList<>();
    }

    public List<String> getIpsToFindNodes () {
        return Arrays.asList("9090","9091","9092","9093","9094","9095",
                "9096","9097","9098","9099");
    }

    @Bean
    public Node node () {
        Node n = new Node();
        n.setNodeId(UUID.randomUUID().toString());
        return n;
    }

    public  static void main(String args[]) {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider()); //Setup Bouncey castle as a Security Provider

        SpringApplication.run(Application.class);
    }

    /**
    @Scheduled(fixedDelay = 1000)
    public void scheduleFixedDelayTask() {
        System.out.println(
                "Fixed delay task - " + System.currentTimeMillis() / 1000);
    }
    */

    @PostConstruct
    public void initApplication () {

        connectToNode();

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


    private void connectToNode() {

        WebSocketClient client = new StandardWebSocketClient();

        //iteration sur la liste des noeuds a trouver pour la connection initiale
        getIpsToFindNodes().stream().filter(nodePort -> {

            return connectionToNode(nodePort,client);

        }).findFirst();

    }

    private boolean connectionToNode(String nodePort, WebSocketClient client) {

        LOGGER.info("Trying to connect to node, port: {}",nodePort);
        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        ListenableFuture<StompSession> future
                = stompClient.connect("ws://localhost:" + nodePort + "/join", webSocketStompSessionHandler);

        try {
            future.get(1, TimeUnit.SECONDS);
            LOGGER.info("Connection to node, port: {}, successfull!",nodePort);
            return true;
        } catch (Exception e) {
            LOGGER.warn("Connection to node, port: {}, failed! - {}",nodePort,e.getMessage());
            return false;
        }
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
