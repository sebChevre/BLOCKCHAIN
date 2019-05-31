package ch.sebooom.blockchain.application.blockchain.web.api;

import ch.sebooom.blockchain.application.blockchain.web.api.resources.BlockChainRessource;
import ch.sebooom.blockchain.domain.blockchain.BlockChain;
import ch.sebooom.blockchain.domain.service.BlockChainDomainService;
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
    BlockChainDomainService blockChainDomainService;

    @GetMapping
    public ResponseEntity<BlockChainRessource> getBlockchain () {

        BlockChain blockChain = blockChainDomainService.getBlockChain();

        return ResponseEntity.ok(BlockChainRessource.from(blockChain));

    }
}
