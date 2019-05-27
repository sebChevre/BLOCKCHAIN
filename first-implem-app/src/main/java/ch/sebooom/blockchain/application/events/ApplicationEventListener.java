package ch.sebooom.blockchain.application.events;

import ch.sebooom.blockchain.application.blockchain.web.api.resources.NoeudsConnectesStatusRessource;
import ch.sebooom.blockchain.domain.StatusNoeud;
import ch.sebooom.blockchain.domain.Noeud;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.EventSource;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class ApplicationEventListener {


    @Autowired
    StatusNoeud statusNoeud;
    @Autowired
    Environment environment;


    @Autowired
    ObjectMapper mapper;

    private final static Logger LOGGER = LoggerFactory.getLogger(ApplicationEventListener.class.getName());


    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationready(){

        finaliserConfigurationNoeud();

        connectToNode();

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
    private void connectToNode() {

        LOGGER.info("Noeud actif: {}",statusNoeud.noeud);

        //iteration sur la liste des noeuds a trouver pour la connection initiale
        getIpsToFindNodes().stream()
                .filter(noeudPort ->{
                            LOGGER.info("noeudPort:{}",noeudPort);
                            LOGGER.info("noeud.getPort:{}",statusNoeud.noeud);

                            return !noeudPort.equals(statusNoeud.noeud.getPort());
                        }

                    ).forEach(noeudPort -> {
                        //connectionToNode(noeudPort,eventHandler);
            connectToNoeudsConnecteEndpoint(noeudPort);
        });


    }



    public List<String> getIpsToFindNodes () {
        return Arrays.asList("9090","9091","9092","9093","9094","9095",
                "9096","9097","9098","9099");
    }

    private void connectToNoeudsConnecteEndpoint (String nodePort) {
        OkHttpClient client = new OkHttpClient();
        String url = String.format("http://localhost:%s/join",nodePort);

        try {
            LOGGER.info("Connect to port: {}, with node: {}",statusNoeud.noeud);
            String noeudJson =  mapper.writeValueAsString(statusNoeud.noeud);
            LOGGER.info("noeud json: {}",noeudJson);
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), noeudJson);

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

            }catch(Exception e){
                LOGGER.info("Connection failed to port: {}, {}",nodePort,e.getMessage());
                e.printStackTrace();

            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }





    }

    /**
     * Tentative de connection au serveur ws sur le port passé en paramètre
     * @param nodePort le port
     * @param eventHandler le gestionnaire d'écévnements
     * @return un boolean corresponsant à l'état de la connection
     */
    private void connectionToNode(String nodePort, EventHandler eventHandler) {

        LOGGER.info("Trying to connect to noeud, port: {}",nodePort);

        String url = String.format("http://localhost:%s/stream-sse",nodePort);
        EventSource.Builder builder = new EventSource.Builder(eventHandler, URI.create(url));
        builder.connectTimeoutMs(10000);


        try (EventSource eventSource = builder.build()) {
            eventSource.setReconnectionTimeMs(3000);
            //eventSource.
            eventSource.start();

            TimeUnit.MINUTES.sleep(1);
            //TimeUnit.MINUTES.sleep(1);
        } catch (InterruptedException e) {
            LOGGER.info("Connection failed...");
            e.printStackTrace();
        }
    }

}
