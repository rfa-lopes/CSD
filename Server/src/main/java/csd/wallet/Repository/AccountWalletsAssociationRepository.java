package csd.wallet.Repository;

import csd.wallet.Models.AccountWalletsAssociation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountWalletsAssociationRepository extends CrudRepository<AccountWalletsAssociation,Long> {

    List<AccountWalletsAssociation> findAllByUserId(long userId);

}
