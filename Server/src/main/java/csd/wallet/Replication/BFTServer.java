package csd.wallet.Replication;

import bftsmart.reconfiguration.util.ECDSAKeyLoader;
import bftsmart.tom.MessageContext;

import bftsmart.tom.ServiceReplica;
import bftsmart.tom.server.defaultservices.DefaultSingleRecoverable;
import csd.wallet.Enums.RequestType;
import csd.wallet.Models.*;
import csd.wallet.Replication.Operations.Accounts.ResultAccountsClass;
import csd.wallet.Replication.Operations.Login.ResultLoginClass;
import csd.wallet.Replication.Operations.Result;
import csd.wallet.Replication.ServiceProxy.SignedResult;
import csd.wallet.Replication.Operations.SmartContracts.ResultSmartContractClass;
import csd.wallet.Replication.Operations.Tests.ResultTestsClass;
import csd.wallet.Replication.Operations.Transfers.ResultTransfersClass;
import csd.wallet.Replication.Operations.Wallets.ResultWalletsClass;
import csd.wallet.Utils.JSON;
import csd.wallet.Utils.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import bftsmart.tom.util.*;

import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;

@Component
public class BFTServer extends DefaultSingleRecoverable implements Serializable {

    @Autowired
    ResultTestsClass tests;

    @Autowired
    ResultTransfersClass transfers;

    @Autowired
    ResultWalletsClass wallets;

    @Autowired
    ResultSmartContractClass smartcontract;

    @Autowired
    ResultAccountsClass accounts;

    @Autowired
    ResultLoginClass login;

    PrivateKey privKey;

    int id;

    ServiceReplica a;

    public BFTServer(@Value("${bftsmart.id}") int id) {
        a = new ServiceReplica(id, this, this);
        this.id = id;
    }

    @Override
    public void installSnapshot(byte[] bytes) {
        // TODO:
    }

    @Override
    public byte[] getSnapshot() {
        // TODO:
        return new byte[0];
    }

    @Override
    public byte[] appExecuteOrdered(byte[] command, MessageContext messageContext) {
        byte[] reply = null;
        Result result = null;

        try {
            ByteArrayInputStream byteIn = new ByteArrayInputStream(command);
            ObjectInput objIn = new ObjectInputStream(byteIn);
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutput objOut = new ObjectOutputStream(byteOut);

            RequestType reqType = (RequestType) objIn.readObject();

            switch (reqType) {
                case TEST_1:
                    result = tests.test1();
                    break;

                case TEST_2:
                    result = tests.test2();
                    break;

                case TEST_3: // "Otherwise, an ordered request is issued internally using the invokeOrdered method".
                    result = tests.test3();
                    break;

                case TEST_4:
                    result = tests.test4();
                    break;

                case TRANSFERS_ADD:
                    result = transfers.addMoney((long) objIn.readObject(), (AddRemoveForm) objIn.readObject(), (String) objIn.readObject());
                    break;

                case TRANSFERS_REMOVE:
                    result = transfers.removeMoney((long) objIn.readObject(), (AddRemoveForm) objIn.readObject(), (String) objIn.readObject());
                    break;

                case TRANSFERS_TRANSFER:
                    result = transfers.transfer((long) objIn.readObject(), (Transfer) objIn.readObject(), (String) objIn.readObject());
                    break;

                case WALLET_CREATE:
                    result = wallets.createWallet((long) objIn.readObject(), (Wallet) objIn.readObject());
                    break;

                case WALLET_DELETE:
                    result = wallets.deleteWallet((long) objIn.readObject(), (long) objIn.readObject());
                    break;

                case TRANSFERS_GLOBALTRANSFERS:
                    result = transfers.ledgerOfGlobalTransfers((long) objIn.readObject());
                    break;

                case TRANSFERS_WALLETTRANSFERS:
                    result = transfers.ledgerOfWalletTransfers((long) objIn.readObject(), (long) objIn.readObject());
                    break;

                case TRANSFERS_DATETRANSFERS:
                    result = transfers.ledgerOfDateTransfers((long) objIn.readObject(), (String) objIn.readObject());
                    break;

                case WALLET_INFO:
                    result = wallets.getWalletInfo((long) objIn.readObject(), (long) objIn.readObject());
                    break;

                case WALLET_AMOUNT:
                    result = wallets.getCurrentAmount((long) objIn.readObject(), (long) objIn.readObject());
                    break;

                case SMART_CONTRACT_EXECUTE:
                    result = smartcontract.executeSmartContract((long) objIn.readObject(), (SmartContract) objIn.readObject());
                    break;
                case ACCOUNT_CREATE:
                    result = accounts.createAccount((Account) objIn.readObject());
                    break;

                case LOGIN:
                    result = login.login((Account) objIn.readObject());
                    break;
            }
            SignedResult sigResult = new SignedResult(result, signReply(result), id);

            objOut.writeObject(sigResult);
            objOut.flush();
            byteOut.flush();
            reply = byteOut.toByteArray();

            Logger.replication("Replication - " + reqType);
        } catch (IOException | ClassNotFoundException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            Logger.error("<<<BFT Server error>>>");
        }
        return reply;
    }

    @Override
    public byte[] appExecuteUnordered(byte[] command, MessageContext messageContext) {
        byte[] reply = null;
        Result result = null;
        try {
            ByteArrayInputStream byteIn = new ByteArrayInputStream(command);
            ObjectInput objIn = new ObjectInputStream(byteIn);
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutput objOut = new ObjectOutputStream(byteOut);

            RequestType reqType = (RequestType) objIn.readObject();

            switch (reqType) {
                case TEST_2:
                    result = tests.test2();
                    break;

                case TEST_4:
                    result = tests.test4();
                    break;

                case TRANSFERS_GLOBALTRANSFERS:
                    result = transfers.ledgerOfGlobalTransfers((long) objIn.readObject());
                    break;

                case TRANSFERS_WALLETTRANSFERS:
                    result = transfers.ledgerOfWalletTransfers((long) objIn.readObject(), (long) objIn.readObject());
                    break;

                case TRANSFERS_DATETRANSFERS:
                    result = transfers.ledgerOfDateTransfers((long) objIn.readObject(), (String) objIn.readObject());
                    break;

                case WALLET_INFO:
                    result = wallets.getWalletInfo((long) objIn.readObject(), (long) objIn.readObject());
                    break;

                case WALLET_AMOUNT:
                    result = wallets.getCurrentAmount((long) objIn.readObject(), (long) objIn.readObject());
                    break;

                case SMART_CONTRACT_EXECUTE:
                    result = smartcontract.executeSmartContract((long) objIn.readObject(), (SmartContract) objIn.readObject());
                    break;
            }

            SignedResult sigResult = new SignedResult(result, signReply(result), id);

            objOut.writeObject(sigResult);
            objOut.flush();
            byteOut.flush();
            reply = byteOut.toByteArray();
            Logger.replication("Replication - " + reqType);
        } catch (IOException | ClassNotFoundException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            Logger.error("<<<BFT Server error>>>");
        }
        return reply;
    }

    private byte[] signReply(Result result) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        ECDSAKeyLoader keyloader = new ECDSAKeyLoader(id, "", false, "EC");
        privKey = keyloader.loadPrivateKey();
        String json = JSON.toJson(result);
        return TOMUtil.signMessage(privKey, json.getBytes());
    }

}
