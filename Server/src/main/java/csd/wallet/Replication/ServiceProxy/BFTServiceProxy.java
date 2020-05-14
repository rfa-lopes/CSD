package csd.wallet.Replication.ServiceProxy;

import bftsmart.communication.client.ReplyListener;
import bftsmart.tom.AsynchServiceProxy;
import bftsmart.tom.RequestContext;
import bftsmart.tom.core.messages.TOMMessage;
import bftsmart.tom.core.messages.TOMMessageType;
import bftsmart.tom.util.TOMUtil;
import csd.wallet.Replication.Result;
import csd.wallet.Replication.SignsResults;
import csd.wallet.Utils.Convert;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.security.PublicKey;
import java.util.*;

import static csd.wallet.Replication.Result.ok;

public class BFTServiceProxy implements ReplyListener {

    @Autowired
    AsynchServiceProxy asynchServiceProxy;

    Map<Integer, byte[]> signatureReceive = new HashMap<>();
    Map<Integer,byte[]> replies = new HashMap<>();

    int numValidReplicas;

    int minReplicas = asynchServiceProxy.getViewManager().getCurrentViewF() * 2 + 1;

    SignsResults result;

    @Override
    public void reset() { }

    @Override
    public void replyReceived(RequestContext requestContext, TOMMessage tomMessage) {

        try {
            SignedResult signedResult = (SignedResult) Convert.toObject(tomMessage.getContent());

            byte[] reply = Convert.toBytes(signedResult.getResult());
            byte[] signature = signedResult.getSignature();
            int id = signedResult.getId();

            PublicKey pubKey = asynchServiceProxy.getViewManager().getStaticConf().getPublicKey(id);

            boolean isValid = TOMUtil.verifySignature(pubKey, reply, signature);

            if(isValid){
                signatureReceive.put(id, signature);
                numValidReplicas++;

                replies.put(id, reply);

                if(numValidReplicas >= minReplicas){
                    int replyIndex = 0; //RANDOM ?
                    result = new SignsResults(signatureReceive, replies.get(replyIndex));
                    asynchServiceProxy.cleanAsynchRequest(requestContext.getOperationId());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public byte[] invoke(byte[] toByteArray) throws IOException {
        asynchServiceProxy.invokeAsynchRequest(toByteArray, this, TOMMessageType.ORDERED_REQUEST);
        while(numValidReplicas < minReplicas);
        return Convert.toBytes(ok(result));
    }
}
