package ch.sebooom.blockchain.infrastructure.blockain.repository;

import ch.sebooom.blockchain.domain.BlockChain;
import ch.sebooom.blockchain.domain.repository.BlockChainRepository;
import ch.sebooom.blockchain.infrastructure.momorydb.DataSource;
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
    DataSource dataSource;

    @Override
    public BlockChain getBlockChain() {
        return dataSource.getBlockChain();
    }



}
