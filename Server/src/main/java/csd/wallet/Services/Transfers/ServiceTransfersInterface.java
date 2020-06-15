package csd.wallet.Services.Transfers;

import csd.wallet.Exceptions.AccountsExceptions.AuthenticationErrorException;
import csd.wallet.Exceptions.TransfersExceptions.InvalidAmountException;
import csd.wallet.Exceptions.TransfersExceptions.TransferToSameWalletException;
import csd.wallet.Exceptions.WalletExceptions.WalletNotExistsException;
import csd.wallet.Models.AddRemoveForm;
import csd.wallet.Models.Transfer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ServiceTransfersInterface {

    void addMoney(long accId, AddRemoveForm idAmount, String addKey) throws InvalidAmountException, WalletNotExistsException, AuthenticationErrorException;

    void removeMoney(long accId, AddRemoveForm idAmount, String addKey) throws InvalidAmountException, WalletNotExistsException, AuthenticationErrorException;

    void transfer(long accId, Transfer transferr, String addKey) throws InvalidAmountException, WalletNotExistsException, TransferToSameWalletException, AuthenticationErrorException;

    List<Transfer> ledgerOfGlobalTransfers(long accId) throws AuthenticationErrorException;

    List<Transfer> ledgerOfWalletTransfers(long accId, long id) throws WalletNotExistsException, AuthenticationErrorException;

    List<Transfer> ledgerOfDateTransfers(long accId, String date) throws WalletNotExistsException, AuthenticationErrorException;
}
