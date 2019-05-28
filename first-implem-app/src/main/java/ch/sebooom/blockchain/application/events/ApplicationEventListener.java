package ch.sebooom.blockchain.application.events;

import ch.sebooom.blockchain.application.blockchain.web.api.resources.NoeudRessource;
import ch.sebooom.blockchain.application.blockchain.web.api.resources.NoeudsConnectesStatusRessource;
import ch.sebooom.blockchain.domain.Noeud;
import ch.sebooom.blockchain.domain.StatusNoeud;
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
import java.util.UUID;

@Component
public class ApplicationEventListener {


    @Autowired
    StatusNoeud statusNoeud;
    @Autowired
    Environment environment;
    @Autowired
    List<String> portsToScan;
    @Autowired
    ObjectMapper mapper;

    private final static String URL_BASE = "http://localhost:%s/join";
    private final static Logger LOGGER = LoggerFactory.getLogger(ApplicationEventListener.class.getName());


    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationready(){

        finaliserConfigurationNoeud();

        explorerNoeudsPourConnectionInitiale();
    }

    /**
     * Finalisation de la configuration du noeud
     */
    private void finaliserConfigurationNoeud() {
        statusNoeud.noeud.setNodeId(UUID.randomUUID().toString());
        statusNoeud.noeud.setPort(environment.getProperty("local.server.port"));
        LOGGER.info("Noeud configuration done. {}",statusNoeud.noeud);
    }

    /**
     * Démarrage de la tentative de connection aux port définis
     */
    private void explorerNoeudsPourConnectionInitiale() {

        LOGGER.info("> Status noeud: {}",statusNoeud.noeud);
        OkHttpClient client = new OkHttpClient();

        //iteration sur la liste des noeuds a trouver pour la connection initiale
        portsToScan.stream()
                .filter(noeudPort ->{

                    LOGGER.info("> Port a connecter:{}",noeudPort);
                    LOGGER.info("> Port actuel :{}",statusNoeud.noeud);

                    return !noeudPort.equals(statusNoeud.noeud.getPort());
                }).forEach(noeudPort -> {

                    connectToNoeudsConnecteEndpoint(noeudPort,client);
        });

    }



    //Connection REST sur le noeud client
    private void connectToNoeudsConnecteEndpoint (String nodePort, OkHttpClient client) {

        String url = String.format(URL_BASE,nodePort);

        LOGGER.info("> Connection sur port: {}, noeud actif node: {}",statusNoeud.noeud);
        //on passe le noeud
        NoeudRessource noeudRessource = new NoeudRessource(statusNoeud.noeud);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), noeudRessource.json());

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {

            String jsonBodyResponse = response.body().string();

            LOGGER.info("Response body:{}",jsonBodyResponse);

            NoeudsConnectesStatusRessource nodesFromPeer = mapper.readValue(jsonBodyResponse, NoeudsConnectesStatusRessource.class);

            LOGGER.info("Nodes from peer: {}",nodesFromPeer);

            nodesFromPeer.getNoeudRessources().forEach(noeud -> {
                statusNoeud.addNode(new Noeud(noeud.getNodeId(),noeud.getPort()));
            });

            Noeud noeudOrigine = new Noeud(nodesFromPeer.getNoeudOrigine().getNodeId(),nodesFromPeer.getNoeudOrigine().getPort());
            statusNoeud.addNode(noeudOrigine);

        }catch(IOException e){
            LOGGER.info("> Connection failed to port: {}, {}",nodePort,e.getMessage());

        }

    }

}
