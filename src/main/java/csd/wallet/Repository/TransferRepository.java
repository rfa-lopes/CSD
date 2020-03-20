package csd.wallet.Repository;

import org.springframework.data.repository.CrudRepository;

import csd.wallet.Models.Transfer;

import java.util.List;

public interface TransferRepository extends CrudRepository<Transfer,Long>{
                        //TODO: JpaRepository ? diferenças?
}
