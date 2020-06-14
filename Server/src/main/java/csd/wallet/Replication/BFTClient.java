package csd.wallet.Replication;

import bftsmart.tom.ServiceProxy;

import static csd.wallet.Replication.Operations.Result.*;
import static csd.wallet.Replication.Operations.Result.ErrorCode.INTERNAL_ERROR;

import csd.wallet.Enums.RequestType;
import csd.wallet.Replication.Operations.Result;
import csd.wallet.Replication.ServiceProxy.BFTServiceProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class BFTClient {

    @Autowired
    ServiceProxy serviceProxy;

    @Autowired
    BFTServiceProxy bftServiceProxy;

    private String keys;

    public void setKeys(String keys) {
        this.keys = keys;
    }

    public <T> Result getInvoke(RequestType req, MessageType type, long accId, T... inputs) {
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutput objOut = new ObjectOutputStream(byteOut);

            objOut.writeObject(req);

            if (accId != -2)
                objOut.writeObject(accId);

            for (T input : inputs)
                objOut.writeObject(input);

            if (keys != null)
                objOut.writeObject(keys);

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
                case ASYNC_REQUEST:
                    reply = bftServiceProxy.invoke(byteOut.toByteArray());
                    break;
                default:
                    return getError(INTERNAL_ERROR);
            }

            ByteArrayInputStream byteIn = new ByteArrayInputStream(reply);
            ObjectInput objIn = new ObjectInputStream(byteIn);

            keys = null;

            return (Result) objIn.readObject();
        } catch (IOException | ClassNotFoundException e) {
            keys = null;
            return getError(INTERNAL_ERROR);
        }
    }

}
