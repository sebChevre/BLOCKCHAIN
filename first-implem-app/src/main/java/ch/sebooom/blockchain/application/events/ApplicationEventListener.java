package ch.sebooom.blockchain.application.events;

import ch.sebooom.blockchain.application.blockchain.web.api.command.JoinNoeudDistantCommand;
import ch.sebooom.blockchain.application.blockchain.web.api.resources.JoinNoeudDistantReponseRessource;
import ch.sebooom.blockchain.domain.blockchain.Block;
import ch.sebooom.blockchain.domain.noeuds.Noeud;
import ch.sebooom.blockchain.domain.noeuds.NoeudDistant;
import ch.sebooom.blockchain.domain.noeuds.PorteFeuille;
import ch.sebooom.blockchain.domain.service.BlockChainDomainService;
import ch.sebooom.blockchain.domain.service.NoeudDomaineService;
import ch.sebooom.blockchain.domain.service.PortefeuilleDomaineService;
import ch.sebooom.blockchain.domain.transaction.Transaction;
import ch.sebooom.blockchain.domain.transaction.TransactionOutput;
import ch.sebooom.blockchain.domain.util.CryptoUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

import static ch.sebooom.blockchain.application.configuration.ApplicationConfiguration.URL_BASE;

@Component
public class ApplicationEventListener {

    private final static Logger LOGGER = LoggerFactory.getLogger(ApplicationEventListener.class.getName());


    @Autowired
    NoeudDomaineService noeudDomaineService;
    @Autowired
    BlockChainDomainService blockChainDomainService;
    @Autowired
    PortefeuilleDomaineService portefeuilleDomaineService;
    @Autowired
    Environment environment;
    @Autowired
    List<String> portsToScan;
    @Autowired
    ObjectMapper mapper;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationready(){

        finaliserConfigurationNoeud();

        initPortefeuille();

        explorerNoeudsPourConnectionInitiale();
    }

    /**
     * Finalisation de la configuration du noeud
     */
    private void finaliserConfigurationNoeud() {
        int port = Integer.valueOf(environment.getProperty("local.server.port"));
        Noeud noeud = Noeud.initNoeud(port);
        noeudDomaineService.creerNoeud(noeud);

        LOGGER.info("> Noeud configuration done. {}", noeudDomaineService.getNoeud());
    }

    /**
     * Démarrage de la tentative de connection aux port définis
     */
    private void explorerNoeudsPourConnectionInitiale() {

        Noeud noeud = noeudDomaineService.getNoeud();

        LOGGER.info("> Status noeud: {}", noeud);
        OkHttpClient client = new OkHttpClient();

        //iteration sur la liste des noeuds a trouver pour la connection initiale
        portsToScan.stream()
                .filter(noeudPort -> {

                    LOGGER.info("> Port a connecter:{}",noeudPort);
                    LOGGER.info("> Port actuel :{}", noeud);

                    return !noeudPort.equals(noeud);
                }).forEach(noeudPort -> {

                    connectToNoeudsConnecteEndpoint(noeudPort,client);
                });

    }

    private void initPortefeuille(){

        Noeud noeud = noeudDomaineService.getNoeud();

        LOGGER.info("> Starting application init...");
        LOGGER.info("> Blockchain generated auto with datasource!");

        LOGGER.info("> Creating portefeuille-base....");
        PorteFeuille portefeuilleBase = PorteFeuille.creerPortefeuilleBase();
        portefeuilleDomaineService.savePortefeuille(portefeuilleBase);
        LOGGER.info("> Portefeuille portefeuille-base créé *****");
        LOGGER.info("> Clé publique, {}", CryptoUtil.getStringFromKey(portefeuilleBase.clePublique));

        LOGGER.info("> Creating premier portefeuille....");
        PorteFeuille premierPortefeuille = PorteFeuille.creerPortefeuille("Portefeuille pour noeud: " + noeud.identifiant());
        portefeuilleDomaineService.savePortefeuille(premierPortefeuille);
        LOGGER.info("> Portefeuille premier-portefeuille créé *****");
        LOGGER.info("> Clé publique, {}", CryptoUtil.getStringFromKey(premierPortefeuille.clePublique));

        LOGGER.info("> Création transaction genesis...");
        Transaction genesisTransaction = genererTransactionGenesis(portefeuilleBase, premierPortefeuille);
        LOGGER.info("> Création block genesis...");
        genererBlockGenesis(blockChainDomainService, genesisTransaction);
        LOGGER.info("> Genesis block added!");

        noeudDomaineService.addPortefeuilleToNoeud(premierPortefeuille);
    }

    private void genererBlockGenesis(BlockChainDomainService blockChainDomainService, Transaction genesisTransaction) {
        Block genesis = new Block("0",0);
        blockChainDomainService.addTransactionToBlock(genesisTransaction,genesis);
        blockChainDomainService.getBlockChain().addBlock(genesis);
    }

    private Transaction genererTransactionGenesis(PorteFeuille portefeuilleBase, PorteFeuille premierPortefeuille) {
        Transaction genesisTransaction = new Transaction(portefeuilleBase.clePublique, premierPortefeuille.clePublique, 150f);
        genesisTransaction.generateSignature(portefeuilleBase.clePrive);	 //manually sign the genesis transaction
        genesisTransaction.transactionId = "0"; //manually set the transaction id
        genesisTransaction.outputs.add(new TransactionOutput(genesisTransaction.destinataire, genesisTransaction.value, genesisTransaction.transactionId)); //manually add the Transactions Output
        blockChainDomainService.getBlockChain().UTXOs.put(genesisTransaction.outputs.get(0).id, genesisTransaction.outputs.get(0)); //its important to store our first transaction in the UTXOs list.

        LOGGER.info("> Transaction genesis ok: {}",genesisTransaction.getJsonRepresentation());
        return genesisTransaction;
    }



    //Connection REST sur le noeud client
    private void connectToNoeudsConnecteEndpoint (String nodePort, OkHttpClient client) {

        String url = String.format(URL_BASE,nodePort);
        Noeud noeud = noeudDomaineService.getNoeud();
        LOGGER.info("> Connection sur port: {}, noeud actif node: {}", noeud);

        JoinNoeudDistantCommand joinNoeudDistantCommand = new JoinNoeudDistantCommand();
        joinNoeudDistantCommand.fromNoeud(noeud);

        RequestBody body
                = RequestBody.create(joinNoeudDistantCommand.json(),MediaType.parse("application/json; charset=utf-8"));


        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        //Envoi de la commande au poont d'entrée /join et traietement de la réponse
        try (Response response = client.newCall(request).execute()) {
            //récupération corps réponse
            String jsonBodyResponse = response.body().string();
            LOGGER.info("> Response body:{}",jsonBodyResponse);
            //Déserialisation
            JoinNoeudDistantReponseRessource joinNoeudDistantReponseRessource = mapper.readValue(jsonBodyResponse, JoinNoeudDistantReponseRessource.class);
            LOGGER.info("> Nodes from peer: {}",joinNoeudDistantReponseRessource);

            NoeudDistant noeudOrigine = joinNoeudDistantReponseRessource.toNoeudDistant();
            //ajout du noeud origine
            noeud.ajouNoeudDistant(noeudOrigine);

        }catch(IOException e){
            LOGGER.info("> Connection failed to port: {}, {}",nodePort,e.getMessage());

        }

    }

}
