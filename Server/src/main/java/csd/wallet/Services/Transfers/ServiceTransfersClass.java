package csd.wallet.Services.Transfers;

import csd.wallet.Crypto.Sum.HomoAdd;
import csd.wallet.Exceptions.AccountsExceptions.AuthenticationErrorException;
import csd.wallet.Exceptions.TransfersExceptions.TransferToSameWalletException;
import csd.wallet.Exceptions.WalletExceptions.WalletNotExistsException;
import csd.wallet.Exceptions.TransfersExceptions.InvalidAmountException;
import csd.wallet.Models.*;
import csd.wallet.Repository.AccountWalletsAssociationRepository;
import csd.wallet.Repository.DepositsRepository;
import csd.wallet.Repository.WalletRepository;
import csd.wallet.WebFilters.AuthenticatorFilter;
import org.springframework.beans.factory.annotation.Autowired;

import csd.wallet.Repository.TransferRepository;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static csd.wallet.Models.Deposits.Operation.*;

@Service
public class ServiceTransfersClass implements ServiceTransfersInterface {

    @Autowired
    private TransferRepository transfers;

    @Autowired
    private WalletRepository wallets;

    @Autowired
    AccountWalletsAssociationRepository accountWalletsAssociation;

    @Autowired
    DepositsRepository depositsRepository;

    public static long MIN_AMOUNT = 0;
    public static long MAX_AMOUNT = 999999999;

    @Override
    public void addMoney(long accId, AddRemoveForm idAmount, String addKey) throws InvalidAmountException, WalletNotExistsException, AuthenticationErrorException {

        if(accId == AuthenticatorFilter.FAIL_AUTH)
            throw new AuthenticationErrorException();

        if (!isWalletOwner(accId, idAmount.getId()))
            throw new AuthenticationErrorException();

        long walletId = idAmount.getId();

        Wallet w = wallets.findById(walletId).orElseThrow(() -> new WalletNotExistsException(walletId));

        AmountRestrictions(idAmount.getAmount_ope());

        BigInteger amount_add = w.getAmount_add();
        BigInteger toAdd_add = idAmount.getAmount_add();

        if(amount_add.equals(BigInteger.ZERO))
            w.setAmount_add(toAdd_add);
        else{
            BigInteger nSquare = new BigInteger(addKey);
            BigInteger result_add = HomoAdd.sum(amount_add, toAdd_add, nSquare);
            w.setAmount_add(result_add);
        }

        depositsRepository.save(new Deposits(walletId, toAdd_add, idAmount.getAmount_ope(), ADD));
        wallets.save(w);
    }

    @Override
    public void removeMoney(long accId, AddRemoveForm idAmount, String addKey) throws InvalidAmountException, WalletNotExistsException, AuthenticationErrorException {

        if(accId == AuthenticatorFilter.FAIL_AUTH)
            throw new AuthenticationErrorException();

        if (!isWalletOwner(accId, idAmount.getId()))
            throw new AuthenticationErrorException();

        long walletId = idAmount.getId();
        Wallet w = wallets.findById(walletId).orElseThrow(() -> new WalletNotExistsException(walletId));
        AmountRestrictions(idAmount.getAmount_ope());

        BigInteger amount_add = w.getAmount_add();

        BigInteger toRemove_add = idAmount.getAmount_add();

        if(amount_add.equals(BigInteger.ZERO))
            throw new InvalidAmountException(0);

        BigInteger nSquare = new BigInteger(addKey);
        BigInteger result_add = HomoAdd.dif(amount_add, toRemove_add, nSquare);
        w.setAmount_add(result_add);

        depositsRepository.save(new Deposits(walletId, toRemove_add, idAmount.getAmount_ope(), REMOVE));
        wallets.save(w);
    }

    @Override
    public void transfer(long accId, Transfer transfer, String addKey) throws InvalidAmountException, WalletNotExistsException, TransferToSameWalletException, AuthenticationErrorException {

        if(accId == AuthenticatorFilter.FAIL_AUTH)
            throw new AuthenticationErrorException();

        if (!isWalletOwner(accId, transfer.getFromId()))
            throw new AuthenticationErrorException();

        BigInteger amount_add = transfer.getAmount_add();
        long amount_ope = transfer.getAmount_ope();

        long fromId = transfer.getFromId();
        long toId = transfer.getToId();

        AmountRestrictions(amount_ope);

        Wallet fromW = wallets.findById(fromId).orElseThrow(() -> new WalletNotExistsException(fromId));
        Wallet toW = wallets.findById(toId).orElseThrow(() -> new WalletNotExistsException(toId));

        if(fromW == toW)
            throw new TransferToSameWalletException(fromId);

        BigInteger nSquare = new BigInteger(addKey);
        BigInteger fromResult_add = HomoAdd.dif(fromW.getAmount_add(), amount_add, nSquare);
        BigInteger toResult_add = HomoAdd.sum(toW.getAmount_add(), amount_add, nSquare);

        fromW.setAmount_add(fromResult_add);
        toW.setAmount_add(toResult_add);

        transfers.save(new Transfer(fromId, toId, amount_add, amount_ope));

        wallets.save(fromW);
        wallets.save(toW);
    }

    @Override
    public List<Transfer> ledgerOfGlobalTransfers(long accId) throws AuthenticationErrorException {

        if(accId == AuthenticatorFilter.FAIL_AUTH)
            throw new AuthenticationErrorException();

        List<Transfer> globalTransfers = new ArrayList<>();
        transfers.findAll().forEach(globalTransfers::add);
        return globalTransfers;
    }

    @Override
    public List<Transfer> ledgerOfWalletTransfers(long accId, long id) throws WalletNotExistsException, AuthenticationErrorException {

        if(accId == AuthenticatorFilter.FAIL_AUTH)
            throw new AuthenticationErrorException();

        wallets.findById(id).orElseThrow(() -> new WalletNotExistsException(id));
        List<Transfer> walletTransfers = new LinkedList<>();
        walletTransfers.addAll(transfers.findAllByFromId(id));
        walletTransfers.addAll(transfers.findAllByToId(id));
        return walletTransfers;
    }

    private void AmountRestrictions(long amount) throws InvalidAmountException {
        if(amount < MIN_AMOUNT || amount > MAX_AMOUNT)
            throw new InvalidAmountException();
    }

    private boolean isWalletOwner(long accId, long walletId) {
        List<AccountWalletsAssociation> accWallets = accountWalletsAssociation.findAllByUserId(accId);
        for (AccountWalletsAssociation a : accWallets)
            if (a.getWalletId() == walletId)
                return true;
        return false;
    }
}
