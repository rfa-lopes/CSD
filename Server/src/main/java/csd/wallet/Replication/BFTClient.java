package csd.wallet.Replication;

import bftsmart.tom.ServiceProxy;



import static csd.wallet.Replication.Result.*;
import static csd.wallet.Replication.Result.ErrorCode.INTERNAL_ERROR;

import bftsmart.tom.core.messages.TOMMessageType;
import csd.wallet.Enums.RequestType;
import csd.wallet.Replication.ServiceProxy.BFTServiceProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;

@Service
public class BFTClient {

    @Autowired
    BFTServiceProxy serviceProxy;



    public <T> Result getInvoke(RequestType req, TOMMessageType type, T... inputs) {
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutput objOut = new ObjectOutputStream(byteOut);

            objOut.writeObject(req);

            for (T input : inputs)
                objOut.writeObject(input);

            objOut.flush();
            byteOut.flush();

            byte[] reply;

            switch (type) {
                case ORDERED_REQUEST:
                    reply = serviceProxy.invokeOrdered(byteOut.toByteArray());
                    break;
                case UNORDERED_REQUEST:
                    reply = serviceProxy.invokeUnordered(byteOut.toByteArray());
                    break;
                case UNORDERED_HASHED_REQUEST:
                    reply = serviceProxy.invokeUnorderedHashed(byteOut.toByteArray());
                    break;
                default:
                    return error(INTERNAL_ERROR);
            }

            ByteArrayInputStream byteIn = new ByteArrayInputStream(reply);
            ObjectInput objIn = new ObjectInputStream(byteIn);
            return (Result) objIn.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return error(INTERNAL_ERROR);
        }
    }



}
