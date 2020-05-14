package csd.wallet.Replication.ServiceProxy;

import bftsmart.communication.client.ReplyListener;
import bftsmart.tom.AsynchServiceProxy;
import bftsmart.tom.RequestContext;
import bftsmart.tom.core.messages.TOMMessage;
import bftsmart.tom.core.messages.TOMMessageType;
import bftsmart.tom.util.TOMUtil;
import csd.wallet.Replication.SignedResults;
import csd.wallet.Utils.Convert;
import csd.wallet.Utils.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.PublicKey;
import java.util.*;

import static csd.wallet.Replication.Result.ErrorCode.TIME_OUT;
import static csd.wallet.Replication.Result.error;
import static csd.wallet.Replication.Result.ok;

@Service
public class BFTServiceProxy implements ReplyListener {

    @Autowired
    AsynchServiceProxy asynchServiceProxy;

    Map<Integer, byte[]> signatureReceive = new HashMap<>();
    Map<Integer,byte[]> replies = new HashMap<>();

    int numValidReplicas;

    SignedResults result ;

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

                Logger.warn("CENAS: " + Base64.getEncoder().encodeToString(reply));

                replies.put(id, reply);
                int minReplicas = asynchServiceProxy.getViewManager().getCurrentViewF() * 2 + 1;
                if(numValidReplicas >= minReplicas){
                    result = new SignedResults(signatureReceive, replies.get(id));
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
        int minReplicas = asynchServiceProxy.getViewManager().getCurrentViewF() * 2 + 1;
        long timeout = System.currentTimeMillis() + 5 * 1000; //5 segundos
        while(numValidReplicas < minReplicas)
            if(System.currentTimeMillis() >= timeout)
                return Convert.toBytes(error(TIME_OUT));
        return Convert.toBytes(ok(result));
    }
}
