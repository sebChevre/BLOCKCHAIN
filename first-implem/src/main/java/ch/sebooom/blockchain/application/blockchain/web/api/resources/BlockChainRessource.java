package ch.sebooom.blockchain.application.blockchain.web.api.resources;

import ch.sebooom.blockchain.domain.BlockChain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */
public class BlockChainRessource {

    List<BlockRessource> blocks;

    public BlockChainRessource(List<BlockRessource> blocks) {
        this.blocks = blocks;
    }


    public List<BlockRessource> getBlocks(){
        return blocks;
    }

    public static BlockChainRessource from(BlockChain blockChain) {

        //map des blocks
        List<BlockRessource> blocks = new ArrayList<>();

        blockChain.blockChain.forEach(block -> {

            BlockRessource blockRessource = new BlockRessource(block.hash,block.hashPrecedent,block.merkleRoot,block.blocknumber());
            //map des transactions
            List<TransactionRessource> transactions = new ArrayList<>();

            block.transactions.forEach(transaction -> {

                TransactionRessource transressource = new TransactionRessource(
                        transaction.getInputsValue(),
                        transaction.getOutputsValue(),
                        transaction.expediteur,
                        transaction.destinataire,
                        transaction.value);

                //map des outputs
                transaction.outputs.forEach(output -> {
                    transressource.add(new OutputRessource(output));
                });
                //map des inputs
                transaction.inputs.forEach(input -> {
                    transressource.addInput(new InputRessource(input));
                });
                blockRessource.addTransaction(transressource);

                //transactions.add(transressource);

            });

            blocks.add(blockRessource);


        });

        return new BlockChainRessource(blocks);
    }
}
