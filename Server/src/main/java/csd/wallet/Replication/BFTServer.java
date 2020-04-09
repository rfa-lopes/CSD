package csd.wallet.Replication;

import bftsmart.tom.MessageContext;

import bftsmart.tom.ServiceReplica;
import bftsmart.tom.server.defaultservices.DefaultSingleRecoverable;
import csd.wallet.Exceptions.TransfersExceptions.InvalidAmountException;
import csd.wallet.Exceptions.TransfersExceptions.TransferToSameWalletException;
import csd.wallet.Exceptions.WalletExceptions.EmptyWalletNameException;
import csd.wallet.Exceptions.WalletExceptions.WalletNotExistsException;
import csd.wallet.Models.*;
import csd.wallet.Services.Tests.ServiceTestsClass;
import csd.wallet.Services.Transfers.ServiceTransfersClass;
import csd.wallet.Services.Wallets.ServiceWalletsClass;
import csd.wallet.Utils.Logger;
import csd.wallet.Utils.RequestType;
import csd.wallet.Utils.ResponseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class BFTServer extends DefaultSingleRecoverable implements Serializable {

    @Autowired
    ServiceTransfersClass transfers;

    @Autowired
    ServiceTestsClass tests;

    @Autowired
    ServiceWalletsClass wallets;

    public BFTServer(@Value("${server.port}") int serverport) {
        //TODO:
        new ServiceReplica(serverport % 4, this, this);
    }

    @Override
    public void installSnapshot(byte[] bytes) {
        //TODO:
    }

    @Override
    public byte[] getSnapshot() {
        //TODO:
        return new byte[0];
    }

    @Override
    public byte[] appExecuteOrdered(byte[] command, MessageContext messageContext) {
        byte[] reply = null;
        long id;
        try (ByteArrayInputStream byteIn = new ByteArrayInputStream(command);
             ObjectInput objIn = new ObjectInputStream(byteIn);
             ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
             ObjectOutput objOut = new ObjectOutputStream(byteOut);) {
            RequestType reqType = (RequestType) objIn.readObject();

            Logger.replication("Replication - " + reqType);

            switch (reqType) {
                case TEST_1:
                    String test1 = tests.test1();
                    objOut.writeObject(test1);
                    break;

                case TRANSFERS_ADD:
                    try {
                        AddRemoveForm form = (AddRemoveForm) objIn.readObject();
                        transfers.addMoney(form);
                        objOut.writeObject(ResponseType.OK);
                    } catch (WalletNotExistsException e) {
                        objOut.writeObject(ResponseType.NOT_FOUND);
                    } catch (InvalidAmountException e) {
                        objOut.writeObject(ResponseType.BAD_REQUEST);
                    }
                    break;

                case TRANSFERS_REMOVE:
                    try {
                        AddRemoveForm form = (AddRemoveForm) objIn.readObject();
                        transfers.removeMoney(form);
                        objOut.writeObject(ResponseType.OK);
                    } catch (WalletNotExistsException e) {
                        objOut.writeObject(ResponseType.NOT_FOUND);
                    } catch (InvalidAmountException e) {
                        objOut.writeObject(ResponseType.BAD_REQUEST);
                    }
                    break;

                case TRANSFERS_TRANSFER:
                    try {
                        Transfer transfer = (Transfer) objIn.readObject();
                        transfers.transfer(transfer);
                        objOut.writeObject(ResponseType.OK);
                    } catch (WalletNotExistsException e) {
                        objOut.writeObject(ResponseType.NOT_FOUND);
                    } catch (InvalidAmountException | TransferToSameWalletException e) {
                        objOut.writeObject(ResponseType.BAD_REQUEST);
                    }
                    break;

                case WALLET_CREATE:
                    ResponseWrapper responseWrapper;
                    try {
                        Wallet wallet = (Wallet) objIn.readObject();
                        id = wallets.createWallet(wallet);
                        responseWrapper = new ResponseWrapper(id, null);
                    } catch (EmptyWalletNameException e) {
                        responseWrapper = new ResponseWrapper(null,e);
                    }
                    objOut.writeObject(responseWrapper);
                    break;

                case WALLET_DELETE:
                    try {
                        id = (long) objIn.readObject();
                        wallets.deleteWallet(id);
                        responseWrapper = new ResponseWrapper(null, null);
                    } catch (WalletNotExistsException e) {
                        responseWrapper = new ResponseWrapper(null,e);
                    }
                    objOut.writeObject(responseWrapper);
                    break;

                case WALLET_INFO:
                    try {
                        id = (long) objIn.readObject();
                        Wallet wallet = wallets.getWalletInfo(id);
                        responseWrapper = new ResponseWrapper(wallet, null);
                    } catch (WalletNotExistsException e) {
                        responseWrapper = new ResponseWrapper(null, e);
                    }
                    objOut.writeObject(responseWrapper);
                    break;

                case WALLET_AMOUNT:
                    try {
                        id = (long) objIn.readObject();
                        long amount = wallets.getCurrentAmount(id);
                        responseWrapper = new ResponseWrapper(amount, null);
                    } catch (WalletNotExistsException e) {
                        responseWrapper = new ResponseWrapper(null, e);
                    }
                    objOut.writeObject(responseWrapper);
                    break;
            }
            objOut.flush();
            byteOut.flush();
            reply = byteOut.toByteArray();
        } catch (IOException | ClassNotFoundException e) {
            Logger.error("<<<BFT Server error>>>");
        }
        return reply;
    }

    @Override
    public byte[] appExecuteUnordered(byte[] command, MessageContext messageContext) {
        byte[] reply = null;
        long id;
        try (ByteArrayInputStream byteIn = new ByteArrayInputStream(command);
             ObjectInput objIn = new ObjectInputStream(byteIn);
             ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
             ObjectOutput objOut = new ObjectOutputStream(byteOut);) {
            RequestType reqType = (RequestType) objIn.readObject();

            Logger.replication("Replication - " + reqType);

            switch (reqType) {
                case TEST_2:
                    String test2 = tests.test2();
                    objOut.writeObject(test2);
                    break;

                case TRANSFERS_GLOBALTRANSFERS:
                    ListWrapper list1 = transfers.ledgerOfGlobalTransfers();
                    objOut.writeObject(list1);
                    break;

                case TRANSFERS_WALLETTRANSFERS:
                    id = (long) objIn.readObject();
                    ListWrapper list2 = transfers.ledgerOfWalletTransfers(id);
                    objOut.writeObject(list2);
                    break;

                case WALLET_INFO:
                    ResponseWrapper responseWrapper;
                    try {
                        id = (long) objIn.readObject();
                        Wallet wallet = wallets.getWalletInfo(id);
                        responseWrapper = new ResponseWrapper(wallet, null);
                    } catch (WalletNotExistsException e) {
                        responseWrapper = new ResponseWrapper(null, e);
                    }
                    objOut.writeObject(responseWrapper);
                    break;

                case WALLET_AMOUNT:
                    try {
                        id = (long) objIn.readObject();
                        long amount = wallets.getCurrentAmount(id);
                        responseWrapper = new ResponseWrapper(amount, null);
                    } catch (WalletNotExistsException e) {
                        responseWrapper = new ResponseWrapper(null, e);
                    }
                    objOut.writeObject(responseWrapper);
                    break;
            }
            objOut.flush();
            byteOut.flush();
            reply = byteOut.toByteArray();

        } catch (IOException | ClassNotFoundException e) {
            Logger.error("<<<BFT Server error>>>");
        }
        return reply;
    }
}
