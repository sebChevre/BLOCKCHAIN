package ch.sebooom.blockchain.domain;

import ch.sebooom.blockchain.domain.blockchain.Block;
import ch.sebooom.blockchain.domain.blockchain.BlockChain;
import ch.sebooom.blockchain.domain.noeuds.PorteFeuille;
import ch.sebooom.blockchain.domain.repository.BlockChainRepository;
import ch.sebooom.blockchain.domain.service.BlockDomaineService;
import ch.sebooom.blockchain.domain.service.PortefeuilleDomaineService;
import ch.sebooom.blockchain.domain.service.TransactionDomaineService;
import ch.sebooom.blockchain.domain.transaction.Transaction;
import ch.sebooom.blockchain.domain.transaction.TransactionOutput;
import ch.sebooom.blockchain.domain.util.CryptoUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Type;
import java.security.Security;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class BlockChainTest {

    private final static Logger LOGGER = LoggerFactory.getLogger(BlockChainTest.class.getName());

    private final static ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    BlockChainRepository blockChainRepository;

    private String blockChainAsJson(BlockChain bc){

        String json = null;

        JsonSerializer<BlockChain> serializer = new JsonSerializer<BlockChain>() {

            @Override
            public JsonElement serialize(BlockChain blockChain, Type type, JsonSerializationContext jsonSerializationContext) {
                JsonObject blockChainJson = new JsonObject();

                JsonArray blocksList = new JsonArray();
                bc.blockChain.forEach(block -> {

                    //Block
                    JsonObject blockObject = new JsonObject();
                    blockObject.addProperty("hash",block.hash);
                    blockObject.addProperty("hashPrecedent",block.hashPrecedent);

                    JsonArray transactionsList = new JsonArray();
                    //Transactions
                    block.transactions.forEach(transaction -> {
                        JsonObject transactionObject = new JsonObject();
                        transactionObject.addProperty("input value",transaction.getInputsValue());
                        transactionObject.addProperty("output value",transaction.getOutputsValue());
                        transactionObject.addProperty("from",CryptoUtil.getStringFromKey(transaction.expediteur));
                        transactionObject.addProperty("to",CryptoUtil.getStringFromKey(transaction.destinataire));
                        transactionObject.addProperty("valeur",transaction.value);

                        JsonArray inputsList = new JsonArray();
                        //inputs
                        transaction.inputs.forEach(input -> {
                            JsonObject inputObject = new JsonObject();
                            inputObject.addProperty("transactionOutputId",input.transactionOutputId);

                            JsonObject uxtoObject = new JsonObject();
                            uxtoObject.addProperty("valeur",input.UTXO.value);
                            uxtoObject.addProperty("id",input.UTXO.id);
                            uxtoObject.addProperty("parentTransactionId",input.UTXO.parentTransactionId);
                            uxtoObject.addProperty("destinatire",CryptoUtil.getStringFromKey(input.UTXO.destinataire));

                            inputObject.addProperty("transactionOutputId",input.transactionOutputId);
                            inputObject.add("uxto",uxtoObject);

                            inputsList.add(inputObject);
                        });

                        transactionObject.add("inputs",inputsList);

                        JsonArray outputsList = new JsonArray();

                        //output
                        transaction.outputs.forEach(output -> {
                            JsonObject outputObject = new JsonObject();
                            outputObject.addProperty("destinataire",CryptoUtil.getStringFromKey(output.destinataire));
                            outputObject.addProperty("valeur",output.value);
                            outputObject.addProperty("identifiant",output.id);
                            outputObject.addProperty("parentTransactionId",output.parentTransactionId);





                            outputsList.add(outputObject);
                        });

                        transactionObject.add("outputs",outputsList);
                        transactionsList.add(transactionObject);
                    });

                    blockObject.add("transaction",transactionsList);
                    blocksList.add(blockObject);

                    blockChainJson.add("blockChain", blocksList);

                });

                return blockChainJson;
            }
        };

        json =  new GsonBuilder()
                .registerTypeAdapter(BlockChain.class,serializer)
                .setPrettyPrinting()
                .create().toJson(bc);


        return json;
    }



    @Test
    public void testBlockChainBasic () {
        BlockChain blockchain = new BlockChain();

        Block genesis = new Block( "0",0);
        blockchain.addBlock(genesis);
        genesis.mine(1);

        Block deux = new Block(genesis.hash,1);
        blockchain.addBlock(deux);
        deux.mine(1);

        Block trois = new Block(deux.hash,2);
        blockchain.addBlock(trois);
        trois.mine(1);

        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
        LOGGER.info(blockchainJson);
        LOGGER.info("Validaty: " + blockchain.isChainValid());
        assertTrue(blockchain.isChainValid());
    }

    @Test
    public void testBlockChainValidWithBlockChainObject () {
        BlockChain blockchain = new BlockChain();

        Block genesis  = new Block( "0",0);
        blockchain.addBlock(genesis);
        genesis.mine(1);

        Block deux = new Block(blockchain.getLastHash(),1);
        blockchain.addBlock(deux);
        deux.mine(1);

        Block trois = new Block(blockchain.getLastHash(),2);
        blockchain.addBlock(trois);
        trois.mine(1);

        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
        LOGGER.info(blockchainJson);
        LOGGER.info("Validity: " + blockchain.isChainValid());
        assertTrue(blockchain.isChainValid());
    }

    @Test
    public void testBlockChainInValid () {
        BlockChain blockchain = new BlockChain();

        Block genesis  = new Block( "0",0);
        blockchain.addBlock(genesis);
        genesis.mine(1);

        Block deux = new Block(blockchain.getLastHash(),1);
        blockchain.addBlock(deux);
        deux.mine(1);

        Block trois = new Block(CryptoUtil.sha256Hash("123"),2);
        blockchain.addBlock(trois);
        trois.mine(1);

        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
        LOGGER.info(blockchainJson);
        LOGGER.info("Validity: " + blockchain.isChainValid());
        assertFalse(blockchain.isChainValid());
    }

    /**
    @Ignore
    @Test
    public void testGlobal () {

        PortefeuilleDomaineService portefeuilleDomaineService = new PortefeuilleDomaineService(blockChainRepository);
        TransactionDomaineService transactionDomaineService = new TransactionDomaineService(blockChainRepository);
        BlockDomaineService blockDomaineService = new BlockDomaineService(transactionDomaineService);

        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider()); //Setup Bouncey castle as a Security Provider

        BlockChain bc = new BlockChain();
        LOGGER.info("***** Nouvelle blockchain crée ***** ");
        LOGGER.info(blockChainAsJson(bc));

        //Création des portefeuilles
        PorteFeuille walletA = new PorteFeuille();
        LOGGER.info("***** Portefeuille créé *****");
        LOGGER.info("Clé publique, {}",CryptoUtil.getStringFromKey(walletA.clePubliquePortefuille));

        PorteFeuille walletB = new PorteFeuille();
        LOGGER.info("***** Portefeuille créé *****");
        LOGGER.info("Clé publique, {}",CryptoUtil.getStringFromKey(walletB.clePubliquePortefuille));

        PorteFeuille coinbase = new PorteFeuille();
        LOGGER.info("***** Portefeuille créé *****");
        LOGGER.info("Clé publique, {}",CryptoUtil.getStringFromKey(coinbase.clePubliquePortefuille));

        //Genesis transaction, on envoie 100 NoobCoin au walletA:
        Transaction genesisTransaction = new Transaction(coinbase.clePubliquePortefuille, walletA.clePubliquePortefuille, 100f);
        genesisTransaction.generateSignature(coinbase.clePrive);	 //manually sign the genesis transaction
        genesisTransaction.transactionId = "0"; //manually set the transaction id
        genesisTransaction.outputs.add(new TransactionOutput(genesisTransaction.destinataire, genesisTransaction.value, genesisTransaction.transactionId)); //manually add the Transactions Output
        bc.UTXOs.put(genesisTransaction.outputs.get(0).id, genesisTransaction.outputs.get(0)); //its important to store our first transaction in the UTXOs list.
        LOGGER.info("new Transaction : "  + genesisTransaction.getJsonRepresentation());

        LOGGER.info("\nWalletA's balance is: " + portefeuilleDomaineService.getBalanceForPortefeuille(walletA));
        LOGGER.info("WalletB's balance is: " + portefeuilleDomaineService.getBalanceForPortefeuille(walletB));

        System.out.println("Creating and Mining Genesis block... ");
        Block genesis = new Block("0",0);
        blockDomaineService.addTransactionToBlock(genesisTransaction,genesis);
        //genesis.addTransaction(genesisTransaction);
        bc.addBlock(genesis);
        LOGGER.info("Genesis block added: " + blockChainAsJson(bc));
        //testing
        Block block1 = new Block(genesis.hash,1);
        LOGGER.info("\nWalletA's balance is: " + portefeuilleDomaineService.getBalanceForPortefeuille(walletA));
        LOGGER.info("\nWalletA is Attempting to send funds (40) to WalletB...");
        blockDomaineService.addTransactionToBlock(portefeuilleDomaineService.sendFunds(walletA,walletB.clePubliquePortefuille, 40f),block1);
        //block1.addTransaction(portefeuilleDomaineService.sendFunds(walletA,walletB.clePubliquePortefuille, 40f));
        bc.addBlock(block1);
        LOGGER.info("Block1 added block added: " + blockChainAsJson(bc));

        LOGGER.info("\nWalletA's balance is: " + portefeuilleDomaineService.getBalanceForPortefeuille(walletA));
        LOGGER.info("WalletB's balance is: " + portefeuilleDomaineService.getBalanceForPortefeuille(walletB));

        Block block2 = new Block(block1.hash,2);
        LOGGER.info("\nWalletA Attempting to send more funds (1000) than it has...");
        //block2.addTransaction(walletA.sendFunds(walletA,walletB.clePubliquePortefuille, 1000f));
        blockDomaineService.addTransactionToBlock(portefeuilleDomaineService.sendFunds(walletA,walletB.clePubliquePortefuille, 1000f),block2);

        bc.addBlock(block2);
        LOGGER.info("Block2 added block added: " + blockChainAsJson(bc));


        LOGGER.info("\nWalletA's balance is: " + portefeuilleDomaineService.getBalanceForPortefeuille(walletA));
        LOGGER.info("WalletB's balance is: " + portefeuilleDomaineService.getBalanceForPortefeuille(walletB));

        Block block3 = new Block(block2.hash,3);
        LOGGER.info("\nWalletB is Attempting to send funds (20) to WalletA...");
        //block3.addTransaction(walletB.sendFunds( walletA.clePubliquePortefuille, 20));
        blockDomaineService.addTransactionToBlock(portefeuilleDomaineService.sendFunds(walletB,walletA.clePubliquePortefuille, 20),block3);

        LOGGER.info("\nWalletA's balance is: " + portefeuilleDomaineService.getBalanceForPortefeuille(walletA));
        LOGGER.info("WalletB's balance is: " + portefeuilleDomaineService.getBalanceForPortefeuille(walletB));

        bc.isChainValid();


        System.out.println("BlockChain:" + blockChainAsJson(bc));
    }
*/

}