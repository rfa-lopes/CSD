package csd.wallet.Repository;

import org.springframework.data.repository.CrudRepository;
import csd.wallet.Models.Wallet;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends CrudRepository<Wallet,Long> { }
