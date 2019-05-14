package ch.sebooom.blockchain.application.blockchain.web.api;

import ch.sebooom.blockchain.application.blockchain.web.api.resources.BlockChainRessource;
import ch.sebooom.blockchain.application.service.BlockchainService;
import ch.sebooom.blockchain.domain.BlockChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */
@RestController
@RequestMapping("/chain")
public class BlockchainController {

    @Autowired
    BlockchainService blockchainService;

    @GetMapping
    public ResponseEntity<BlockChainRessource> getBlockchain () {

        BlockChain blockChain = blockchainService.getBlockChain();

        return ResponseEntity.ok(BlockChainRessource.from(blockChain));

    }
}
