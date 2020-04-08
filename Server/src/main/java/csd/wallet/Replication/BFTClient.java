package csd.wallet.Replication;

import bftsmart.tom.ServiceProxy;
import csd.wallet.Utils.RequestType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.*;

@Service
public class BFTClient {

    @Autowired
    ServiceProxy serviceProxy;

    public <E, T> E getInvoke(RequestType req, InvokesTypes type, T... inputs) throws IOException, ClassNotFoundException {
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
                reply = serviceProxy.invokeOrdered(byteOut.toByteArray());
                break;
            case UNORDERED:
                reply = serviceProxy.invokeUnordered(byteOut.toByteArray());
                break;
            case UNORDERED_HASHED:
                reply = serviceProxy.invokeUnorderedHashed(byteOut.toByteArray());
                break;
        }
        ByteArrayInputStream byteIn = new ByteArrayInputStream(reply);
        ObjectInput objIn = new ObjectInputStream(byteIn);
        return (E) objIn.readObject();
    }
}
