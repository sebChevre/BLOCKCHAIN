package ch.sebooom.blockchain.infrastructure.blockain.repository;

import ch.sebooom.blockchain.domain.blockchain.BlockChain;
import ch.sebooom.blockchain.domain.repository.BlockChainRepository;
import ch.sebooom.blockchain.infrastructure.momorydb.InMemoryDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */
@Repository
public class BlockChainInMemoryRepository implements BlockChainRepository {

    @Autowired
    InMemoryDataSource inMemoryDataSource;

    @Override
    public BlockChain getBlockChain() {
        return inMemoryDataSource.getBlockChain();
    }



}
