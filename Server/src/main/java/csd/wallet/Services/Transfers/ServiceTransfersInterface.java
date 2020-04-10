package csd.wallet.Services.Transfers;

import csd.wallet.Exceptions.TransfersExceptions.InvalidAmountException;
import csd.wallet.Exceptions.TransfersExceptions.TransferToSameWalletException;
import csd.wallet.Exceptions.WalletExceptions.WalletNotExistsException;
import csd.wallet.Models.AddRemoveForm;
import csd.wallet.Models.Transfer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ServiceTransfersInterface {

    void addMoney(AddRemoveForm idAmount) throws InvalidAmountException, WalletNotExistsException;

    void removeMoney(AddRemoveForm idAmount) throws InvalidAmountException, WalletNotExistsException;

    void transfer(Transfer transfer) throws InvalidAmountException, WalletNotExistsException, TransferToSameWalletException;

    List<Transfer> ledgerOfGlobalTransfers();

    List<Transfer> ledgerOfWalletTransfers(long id) throws WalletNotExistsException;

}
