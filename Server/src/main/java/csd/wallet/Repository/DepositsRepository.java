package csd.wallet.Repository;

import csd.wallet.Models.Deposits;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositsRepository extends CrudRepository<Deposits, Long> {

}
