package csd.wallet.Repository;

import org.springframework.data.repository.CrudRepository;

import csd.wallet.Models.Transfer;

public interface TransferRepository extends CrudRepository<Transfer,Long>{

}
