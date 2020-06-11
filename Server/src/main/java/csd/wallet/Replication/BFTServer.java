package csd.wallet.Replication;

import bftsmart.reconfiguration.util.ECDSAKeyLoader;
import bftsmart.tom.MessageContext;

import bftsmart.tom.ServiceReplica;
import bftsmart.tom.core.messages.TOMMessage;
import bftsmart.tom.server.defaultservices.DefaultSingleRecoverable;
import csd.wallet.Enums.RequestType;
import csd.wallet.Models.*;
import csd.wallet.Replication.ServiceProxy.SignedResult;
import csd.wallet.Replication.SmartContracts.ResultSmartContractClass;
import csd.wallet.Replication.Tests.ResultTestsClass;
import csd.wallet.Replication.Transfers.ResultTransfersClass;
import csd.wallet.Replication.Wallets.ResultWalletsClass;
import csd.wallet.Utils.Convert;
import csd.wallet.Utils.JSON;
import csd.wallet.Utils.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import bftsmart.reconfiguration.util.RSAKeyLoader;
import  bftsmart.tom.util.*;

import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

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

    PrivateKey privKey;

    int id;

    ServiceReplica a;

    public BFTServer(@Value("${bftsmart.id}") int id) {
        a = new ServiceReplica(id, this, this);
        this.id = id;
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

                case TEST_3: //"Otherwise, an ordered request is issued internally using the invokeOrdered method."
                    result = tests.test3();
                    break;

                case TEST_4:
                    result = tests.test4();
                    break;

                case TRANSFERS_ADD:
                    result = transfers.addMoney((AddRemoveForm) objIn.readObject());
                    break;

                case TRANSFERS_REMOVE:
                    result = transfers.removeMoney((AddRemoveForm) objIn.readObject());
                    break;

                case TRANSFERS_TRANSFER:
                    result = transfers.transfer((Transfer) objIn.readObject());
                    break;

                case WALLET_CREATE:
                    result = wallets.createWallet((Wallet) objIn.readObject());
                    break;

                case WALLET_DELETE:
                    result = wallets.deleteWallet((long) objIn.readObject());
                    break;

                case TRANSFERS_GLOBALTRANSFERS:
                    result = transfers.ledgerOfGlobalTransfers();
                    break;

                case TRANSFERS_WALLETTRANSFERS:
                    result = transfers.ledgerOfWalletTransfers((long) objIn.readObject());
                    break;

                case WALLET_INFO:
                    result = wallets.getWalletInfo((long) objIn.readObject());
                    break;

                case WALLET_AMOUNT:
                    result = wallets.getCurrentAmount((long) objIn.readObject());
                    break;

                case SMART_CONTRACT_EXECUTE:
                    result = smartcontract.executeSmartContract((SmartContract) objIn.readObject());
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
                    result = transfers.ledgerOfGlobalTransfers();
                    break;

                case TRANSFERS_WALLETTRANSFERS:
                    result = transfers.ledgerOfWalletTransfers((long) objIn.readObject());
                    break;

                case WALLET_INFO:
                    result = wallets.getWalletInfo((long) objIn.readObject());
                    break;

                case WALLET_AMOUNT:
                    result = wallets.getCurrentAmount((long) objIn.readObject());
                    break;

                case SMART_CONTRACT_EXECUTE:
                    result = smartcontract.executeSmartContract((SmartContract) objIn.readObject());
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
        byte[] signResult = TOMUtil.signMessage( privKey, json.getBytes());
        return signResult;
    }
   
}
