package csd.wallet.Replication.ServiceProxy;

import bftsmart.tom.ServiceProxy;
import bftsmart.tom.core.messages.TOMMessage;
import csd.wallet.Replication.Result;
import csd.wallet.Utils.Convert;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class BFTServiceProxy extends ServiceProxy {


    List<String> signatureReceived;

    public BFTServiceProxy(int processId) {
        super(processId);
        signatureReceived = new LinkedList<String>();
    }


    @Override
    public void replyReceived(TOMMessage message){
        try {
           SignedResult signedResult = (SignedResult) Convert.toObject(message.getContent());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
