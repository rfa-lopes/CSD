package csd.wallet.Services.Transfers;

import csd.wallet.Crypto.Random.HomoRand;
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

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Base64;
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

    /*
    key = 2053700769052212928
    HomoOPE{0}= 2305825490042093576
    HomoOPE{999999999}= 3379523840383123456
     */
    public static Long MIN_AMOUNT = Long.parseLong("2305825490042093576");
    public static Long MAX_AMOUNT = Long.parseLong("3379523840383123456");

    //public static long MIN_AMOUNT = 0;
    //public static long MAX_AMOUNT = 999999999;


    @Override
    public void addMoney(long accId, AddRemoveForm idAmount, String addKey, String rndKey, String rndKeyIV) throws InvalidAmountException, WalletNotExistsException, AuthenticationErrorException {

        if (accId == AuthenticatorFilter.FAIL_AUTH)
            throw new AuthenticationErrorException();

        if (!isWalletOwner(accId, idAmount.getId()))
            throw new AuthenticationErrorException();

        long walletId = idAmount.getId();

        Wallet w = wallets.findById(walletId).orElseThrow(() -> new WalletNotExistsException(walletId));

        String amount_ope_rnd = idAmount.getAmount_ope_rnd();
        byte[] RNDKeySecretBytes = Base64.getDecoder().decode(rndKey);
        SecretKey RNDKeySecret = new SecretKeySpec(RNDKeySecretBytes, 0, RNDKeySecretBytes.length, "AES");
        Long amount_ope = Long.parseLong(HomoRand.decrypt(RNDKeySecret, Base64.getDecoder().decode(rndKeyIV), amount_ope_rnd));

        AmountRestrictions(amount_ope);

        BigInteger amount_add = new BigInteger(w.getAmount_add());
        BigInteger toAdd_add = new BigInteger(idAmount.getAmount_add());

        if (amount_add.equals(BigInteger.ZERO))
            w.setAmount_add(toAdd_add.toString());
        else {
            BigInteger nSquare = new BigInteger(addKey);
            BigInteger result_add = HomoAdd.sum(amount_add, toAdd_add, nSquare);
            w.setAmount_add(result_add.toString());
        }

        depositsRepository.save(new Deposits(walletId, idAmount.getAmount_add(), idAmount.getAmount_ope_rnd(), ADD));
        wallets.save(w);
    }

    @Override
    public void removeMoney(long accId, AddRemoveForm idAmount, String addKey, String rndKey, String rndKeyIV) throws InvalidAmountException, WalletNotExistsException, AuthenticationErrorException {

        if (accId == AuthenticatorFilter.FAIL_AUTH)
            throw new AuthenticationErrorException();

        if (!isWalletOwner(accId, idAmount.getId()))
            throw new AuthenticationErrorException();

        long walletId = idAmount.getId();
        Wallet w = wallets.findById(walletId).orElseThrow(() -> new WalletNotExistsException(walletId));

        String amount_ope_rnd = idAmount.getAmount_ope_rnd();
        byte[] RNDKeySecretBytes = Base64.getDecoder().decode(rndKey);
        SecretKey RNDKeySecret = new SecretKeySpec(RNDKeySecretBytes, 0, RNDKeySecretBytes.length, "AES");
        Long amount_ope = Long.parseLong(HomoRand.decrypt(RNDKeySecret, Base64.getDecoder().decode(rndKeyIV), amount_ope_rnd));

        AmountRestrictions(amount_ope);

        BigInteger amount_add = new BigInteger(w.getAmount_add());
        BigInteger toRemove_add = new BigInteger(idAmount.getAmount_add());

        if (amount_add.equals(BigInteger.ZERO))
            throw new InvalidAmountException(0);

        BigInteger nSquare = new BigInteger(addKey);
        BigInteger result_add = HomoAdd.dif(amount_add, toRemove_add, nSquare);
        w.setAmount_add(result_add.toString());

        depositsRepository.save(new Deposits(walletId, idAmount.getAmount_add(), idAmount.getAmount_ope_rnd(), REMOVE));
        wallets.save(w);
    }

    @Override
    public void transfer(long accId, Transfer transfer, String addKey) throws InvalidAmountException, WalletNotExistsException, TransferToSameWalletException, AuthenticationErrorException {

        if (accId == AuthenticatorFilter.FAIL_AUTH)
            throw new AuthenticationErrorException();

        if (!isWalletOwner(accId, transfer.getFromId()))
            throw new AuthenticationErrorException();

        BigInteger amount_add = new BigInteger(transfer.getAmount_add());
        long amount_ope = Long.parseLong(transfer.getAmount_ope());

        long fromId = transfer.getFromId();
        long toId = transfer.getToId();

        AmountRestrictions(amount_ope);

        Wallet fromW = wallets.findById(fromId).orElseThrow(() -> new WalletNotExistsException(fromId));
        Wallet toW = wallets.findById(toId).orElseThrow(() -> new WalletNotExistsException(toId));

        if (fromW == toW)
            throw new TransferToSameWalletException(fromId);

        BigInteger nSquare = new BigInteger(addKey);
        BigInteger fromResult_add = HomoAdd.dif(new BigInteger(fromW.getAmount_add()), amount_add, nSquare);
        BigInteger toResult_add = HomoAdd.sum(new BigInteger(toW.getAmount_add()), amount_add, nSquare);

        fromW.setAmount_add(fromResult_add.toString());
        toW.setAmount_add(toResult_add.toString());

        transfers.save(new Transfer(fromId, toId, amount_add.toString(), transfer.getAmount_ope()));
        wallets.save(fromW);
        wallets.save(toW);
    }

    @Override
    public List<Transfer> ledgerOfGlobalTransfers(long accId) throws AuthenticationErrorException {

        if (accId == AuthenticatorFilter.FAIL_AUTH)
            throw new AuthenticationErrorException();

        List<Transfer> globalTransfers = new ArrayList<>();
        transfers.findAll().forEach(globalTransfers::add);
        return globalTransfers;
    }

    @Override
    public List<Transfer> ledgerOfWalletTransfers(long accId, long id) throws WalletNotExistsException, AuthenticationErrorException {

        if (accId == AuthenticatorFilter.FAIL_AUTH)
            throw new AuthenticationErrorException();

        wallets.findById(id).orElseThrow(() -> new WalletNotExistsException(id));
        List<Transfer> walletTransfers = new LinkedList<>();
        walletTransfers.addAll(transfers.findAllByFromId(id));
        walletTransfers.addAll(transfers.findAllByToId(id));
        return walletTransfers;
    }

    private void AmountRestrictions(long amount) throws InvalidAmountException {
        if (amount < MIN_AMOUNT || amount > MAX_AMOUNT)
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
