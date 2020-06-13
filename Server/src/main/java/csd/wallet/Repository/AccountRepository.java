package csd.wallet.Repository;

import org.springframework.data.repository.CrudRepository;
import csd.wallet.Models.Account;

import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

    Account findById(long id);

    Account findByUsername(String username);

}
