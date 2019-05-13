package ch.sebooom.blockchain.domain;

import ch.sebooom.blockchain.domain.util.CryptoUtil;
import com.google.gson.GsonBuilder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Security;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class BlockChainTest {

    private final static Logger LOGGER = LoggerFactory.getLogger(BlockChainTest.class.getName());

    @Test
    public void testBlockChainBasic () {
        BlockChain blockchain = new BlockChain();

        Block genesis = new Block( "0");
        blockchain.addBlock(genesis);
        genesis.mine(1);

        Block deux = new Block(genesis.hash);
        blockchain.addBlock(deux);
        deux.mine(1);

        Block trois = new Block(deux.hash);
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

        Block genesis  = new Block( "0");
        blockchain.addBlock(genesis);
        genesis.mine(1);

        Block deux = new Block(blockchain.getLastHash());
        blockchain.addBlock(deux);
        deux.mine(1);

        Block trois = new Block(blockchain.getLastHash());
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

        Block genesis  = new Block( "0");
        blockchain.addBlock(genesis);
        genesis.mine(1);

        Block deux = new Block(blockchain.getLastHash());
        blockchain.addBlock(deux);
        deux.mine(1);

        Block trois = new Block(CryptoUtil.sha256Hash("123"));
        blockchain.addBlock(trois);
        trois.mine(1);

        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
        LOGGER.info(blockchainJson);
        LOGGER.info("Validity: " + blockchain.isChainValid());
        assertFalse(blockchain.isChainValid());
    }

    @Test
    public void testGlobal () {

        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider()); //Setup Bouncey castle as a Security Provider

        BlockChain bc = new BlockChain();
        LOGGER.info("New Blockchain created: " + bc.getJsonRepresentation());

        //Create wallets:
        PorteFeuille walletA = new PorteFeuille();
        PorteFeuille walletB = new PorteFeuille();
        PorteFeuille coinbase = new PorteFeuille();

        //create genesis transaction, which sends 100 NoobCoin to walletA:
        Transaction genesisTransaction = new Transaction(coinbase.clePublique, walletA.clePublique, 100f, null);
        genesisTransaction.generateSignature(coinbase.clePrive);	 //manually sign the genesis transaction
        genesisTransaction.transactionId = "0"; //manually set the transaction id
        genesisTransaction.outputs.add(new TransactionOutput(genesisTransaction.destinataire, genesisTransaction.value, genesisTransaction.transactionId)); //manually add the Transactions Output
        bc.UTXOs.put(genesisTransaction.outputs.get(0).id, genesisTransaction.outputs.get(0)); //its important to store our first transaction in the UTXOs list.
        LOGGER.info("new Transaction : "  + genesisTransaction.getJsonRepresentation());

        System.out.println("Creating and Mining Genesis block... ");
        Block genesis = new Block("0");
        genesis.addTransaction(genesisTransaction);
        bc.addBlock(genesis);
        LOGGER.info("Genesis block added: " + bc.getJsonRepresentation());
        //testing
        Block block1 = new Block(genesis.hash);
        LOGGER.info("\nWalletA's balance is: " + walletA.getBalance());
        LOGGER.info("\nWalletA is Attempting to send funds (40) to WalletB...");
        block1.addTransaction(walletA.sendFunds(walletB.clePublique, 40f));
        bc.addBlock(block1);
        LOGGER.info("Block1 added block added: " + bc.getJsonRepresentation());

        LOGGER.info("\nWalletA's balance is: " + walletA.getBalance());
        LOGGER.info("WalletB's balance is: " + walletB.getBalance());

        Block block2 = new Block(block1.hash);
        LOGGER.info("\nWalletA Attempting to send more funds (1000) than it has...");
        block2.addTransaction(walletA.sendFunds(walletB.clePublique, 1000f));
        bc.addBlock(block2);
        LOGGER.info("Block2 added block added: " + bc.getJsonRepresentation());


        LOGGER.info("\nWalletA's balance is: " + walletA.getBalance());
        LOGGER.info("WalletB's balance is: " + walletB.getBalance());

        Block block3 = new Block(block2.hash);
        LOGGER.info("\nWalletB is Attempting to send funds (20) to WalletA...");
        block3.addTransaction(walletB.sendFunds( walletA.clePublique, 20));
        LOGGER.info("\nWalletA's balance is: " + walletA.getBalance());
        LOGGER.info("WalletB's balance is: " + walletB.getBalance());

        bc.isChainValid();


        System.out.println("BlockChain:" + bc.getJsonRepresentation());
    }
}