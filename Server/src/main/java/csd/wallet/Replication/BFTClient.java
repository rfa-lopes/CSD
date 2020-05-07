package csd.wallet.Replication;

import bftsmart.tom.AsynchServiceProxy;
import bftsmart.tom.ServiceProxy;

import static csd.wallet.Replication.Result.*;
import static csd.wallet.Replication.Result.ErrorCode.INTERNAL_ERROR;

import csd.wallet.Enums.InvokesTypes;
import csd.wallet.Enums.RequestType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class BFTClient {

    @Autowired
    ServiceProxy serviceProxy;

    @Autowired
    AsynchServiceProxy asynchServiceProxy;

    public <T> Result getInvoke(RequestType req, InvokesTypes type, T... inputs) {
        return this.getInvoke(false, req, type, inputs);
    }

    public <T> Result getInvoke(boolean isAsync, RequestType req, InvokesTypes type, T... inputs) {
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutput objOut = new ObjectOutputStream(byteOut);

            objOut.writeObject(req);

            for (T input : inputs)
                objOut.writeObject(input);

            objOut.flush();
            byteOut.flush();

            byte[] reply = null;

            switch (type) {
                case ORDERED:
                    reply = (isAsync ? asynchServiceProxy : serviceProxy ).invokeOrdered(byteOut.toByteArray());
                    break;
                case UNORDERED:
                    reply = (isAsync ? asynchServiceProxy : serviceProxy ).invokeUnordered(byteOut.toByteArray());
                    break;
                case UNORDERED_HASHED:
                    reply = (isAsync ? asynchServiceProxy : serviceProxy ).invokeUnorderedHashed(byteOut.toByteArray());
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
