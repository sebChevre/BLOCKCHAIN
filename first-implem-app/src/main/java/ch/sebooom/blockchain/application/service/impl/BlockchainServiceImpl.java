package ch.sebooom.blockchain.application.service.impl;

import ch.sebooom.blockchain.application.service.BlockchainService;
import ch.sebooom.blockchain.domain.BlockChain;
import ch.sebooom.blockchain.domain.repository.BlockChainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */
@Service
public class BlockchainServiceImpl implements BlockchainService {

    @Autowired
    BlockChainRepository blockChainRepository;

    @Override
    public BlockChain getBlockChain() {

        return blockChainRepository.getBlockChain();
    }


}
