package csd.wallet.Controllers.Wallets;

import bftsmart.tom.ServiceProxy;
import csd.wallet.Services.Wallets.ServiceWalletsClass;
import csd.wallet.Models.Wallet;
import csd.wallet.Utils.Logger;
import csd.wallet.Utils.RequestType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.io.*;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
public class ControllerWalletsClass implements ControllerWalletsInterface,Serializable {
    @Autowired
    ServiceWalletsClass wallets;

    @Autowired
    ServiceProxy serviceProxy;

    @Override
    public ResponseEntity<Long> createWallet(Wallet wallet) {
        Logger.info("Request: CREATEWALLET");
        try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
             ObjectOutput objOut = new ObjectOutputStream(byteOut)) {

            objOut.writeObject(RequestType.WALLET_CREATE);
            objOut.writeObject(wallet);

            objOut.flush();
            byteOut.flush();

            byte[] reply = serviceProxy.invokeOrdered(byteOut.toByteArray());

            try (ByteArrayInputStream byteIn = new ByteArrayInputStream(reply);
                 ObjectInput objIn = new ObjectInputStream(byteIn)) {
                long response = (long) objIn.readObject();
                if(response == -1)
                    return ResponseEntity.badRequest().build();
                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            Logger.error("Controller: CREATEWALLET");
            e.printStackTrace();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }
    @Override
    public ResponseEntity<Void> deleteWallet(long id) {
        Logger.info("Request: DELETEWALLET");
        try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
             ObjectOutput objOut = new ObjectOutputStream(byteOut)) {

            objOut.writeObject(RequestType.WALLET_DELETE);
            objOut.writeObject(id);

            objOut.flush();
            byteOut.flush();

            byte[] reply = serviceProxy.invokeOrdered(byteOut.toByteArray());

            try (ByteArrayInputStream byteIn = new ByteArrayInputStream(reply);
                 ObjectInput objIn = new ObjectInputStream(byteIn)) {
                long response = (long) objIn.readObject();
                if(response == -1)
                    return ResponseEntity.notFound().build();
                return ResponseEntity.ok().build();
            }
        } catch (Exception e) {
            Logger.error("Controller: DELETEWALLET");
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }
    @Override
    public ResponseEntity<Long> getCurrentAmount(long id) {
        Logger.info("Request: GETCURRENTAMOUNT");
        try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
             ObjectOutput objOut = new ObjectOutputStream(byteOut)) {

            objOut.writeObject(RequestType.WALLET_AMOUNT);
            objOut.writeObject(id);

            objOut.flush();
            byteOut.flush();

            byte[] reply = serviceProxy.invokeUnordered(byteOut.toByteArray());

            try (ByteArrayInputStream byteIn = new ByteArrayInputStream(reply);
                 ObjectInput objIn = new ObjectInputStream(byteIn)) {
                long response = (long) objIn.readObject();
                if(response == -1)
                    return ResponseEntity.notFound().build();
                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            Logger.error("Controller: GETCURRENTAMOUNT");
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }
    @Override
    public ResponseEntity<Wallet> getWalletInfo(long id) {
        Logger.info("Request: GETWALLETINFO");
        try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
             ObjectOutput objOut = new ObjectOutputStream(byteOut)) {

            objOut.writeObject(RequestType.WALLET_INFO);
            objOut.writeObject(id);

            objOut.flush();
            byteOut.flush();

            byte[] reply = serviceProxy.invokeUnordered(byteOut.toByteArray());

            try (ByteArrayInputStream byteIn = new ByteArrayInputStream(reply);
                 ObjectInput objIn = new ObjectInputStream(byteIn)) {
                Object[] response = (Object[]) objIn.readObject();
                if(response[0] != null)
                    return ResponseEntity.ok((Wallet)response[0]);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            Logger.error("Controller: GETWALLETINFO");
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }
}