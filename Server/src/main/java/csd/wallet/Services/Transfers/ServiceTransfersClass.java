package csd.wallet.Services.Transfers;

import csd.wallet.Exceptions.TransfersExceptions.TransferToSameWalletException;
import csd.wallet.Exceptions.WalletExceptions.WalletNotExistsException;
import csd.wallet.Exceptions.TransfersExceptions.InvalidAmountException;
import csd.wallet.Models.AddRemoveForm;
import csd.wallet.Models.ListWrapper;
import csd.wallet.Models.Transfer;
import csd.wallet.Models.Wallet;
import csd.wallet.Repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;

import csd.wallet.Repository.TransferRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class ServiceTransfersClass implements ServiceTransfersInterface {

    @Autowired
    private TransferRepository transfers;

    @Autowired
    private WalletRepository wallets;

    public static long MIN_AMOUNT = 0;
    public static long MAX_AMOUNT = 999999999;

    @Override
    public void addMoney(AddRemoveForm idAmount) throws InvalidAmountException, WalletNotExistsException {
        long id = idAmount.getId();
        long amount = idAmount.getAmount();
        AmountRestrictions(amount);
        Wallet w = wallets.findById(id).orElseThrow(() -> new WalletNotExistsException(id));
        w.addAmount(amount);
        wallets.save(w);
    }

    @Override
    public void removeMoney(AddRemoveForm idAmount) throws InvalidAmountException, WalletNotExistsException {
        long id = idAmount.getId();
        long amount = idAmount.getAmount();
        AmountRestrictions(amount);
        Wallet w = wallets.findById(id).orElseThrow(() -> new WalletNotExistsException(id));
        long availableAmount = w.getAmount();
        if(availableAmount - amount < 0)
            throw new InvalidAmountException(availableAmount);
        w.removeAmount(amount);
        wallets.save(w);
    }

    @Override
    public void transfer(Transfer transfer) throws InvalidAmountException, WalletNotExistsException, TransferToSameWalletException {
        long amount = transfer.getAmount();
        long fromId = transfer.getFromId();
        long toId = transfer.getToId();
        AmountRestrictions(amount);
        Wallet fromW = wallets.findById(fromId).orElseThrow(() -> new WalletNotExistsException(fromId));
        Wallet toW = wallets.findById(toId).orElseThrow(() -> new WalletNotExistsException(toId));
        if(fromW == toW)
            throw new TransferToSameWalletException(fromId);
        long fromAmount = fromW.getAmount();
        if(fromAmount - amount < 0)
            throw new InvalidAmountException(fromAmount);
        fromW.removeAmount(amount);
        toW.addAmount(amount);
        transfers.save(new Transfer(fromId, toId, amount));
        wallets.save(fromW);
        wallets.save(toW);
    }

    @Override
    public ListWrapper ledgerOfGlobalTransfers() {
        List<Transfer> globalTransfers = new LinkedList<>();
        transfers.findAll().forEach(globalTransfers::add);
        return new ListWrapper(globalTransfers);
    }

    @Override
    public ListWrapper ledgerOfWalletTransfers(long id)  {
        List<Transfer> walletTransfers = new LinkedList<>();
        walletTransfers.addAll(transfers.findAllByFromId(id));
        walletTransfers.addAll(transfers.findAllByToId(id));
        return new ListWrapper(walletTransfers);
    }

    private void AmountRestrictions(long amount) throws InvalidAmountException {
        if(amount < MIN_AMOUNT || amount > MAX_AMOUNT)
            throw new InvalidAmountException();
    }
}
