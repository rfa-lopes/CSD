package csd.wallet.Repository;

import org.springframework.data.repository.CrudRepository;
import csd.wallet.Models.Transfer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransferRepository extends CrudRepository<Transfer,Long>{

    List<Transfer> findAllByFromId(long id);
    List<Transfer> findAllByToId(long id);

}
