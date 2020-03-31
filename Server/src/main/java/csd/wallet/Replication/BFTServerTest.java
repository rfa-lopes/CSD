package csd.wallet.Replication;

import bftsmart.tom.MessageContext;
import bftsmart.tom.server.defaultservices.DefaultSingleRecoverable;
import csd.wallet.Services.Tests.ServiceTestsClass;
import csd.wallet.Utils.RequestType;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;

public class BFTServerTest extends DefaultSingleRecoverable {

    @Autowired
    ServiceTestsClass tests;

    @Override
    public void installSnapshot(byte[] bytes) {

    }

    @Override
    public byte[] getSnapshot() {
        return new byte[0];
    }

    @Override
    public byte[] appExecuteOrdered(byte[] command, MessageContext messageContext) {
        byte[] reply = null;
        boolean hasReply = false;
        try (ByteArrayInputStream byteIn = new ByteArrayInputStream(command);
             ObjectInput objIn = new ObjectInputStream(byteIn);
             ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
             ObjectOutput objOut = new ObjectOutputStream(byteOut);) {
            RequestType reqType = (RequestType) objIn.readObject();
            switch (reqType) {
                case TEST_1:
                    String test = tests.test1();
                    if (test != null) {
                        objOut.writeObject(test);
                        hasReply = true;
                    }
                    break;
            }
            if (hasReply) {
                objOut.flush();
                byteOut.flush();
                reply = byteOut.toByteArray();
            } else {
                reply = new byte[0];
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            //logger.log(Level.SEVERE, "Ocurred during map operation execution", e);
        }
        return reply;
    }

    @Override
    public byte[] appExecuteUnordered(byte[] bytes, MessageContext messageContext) {
        return new byte[0];
    }
}
