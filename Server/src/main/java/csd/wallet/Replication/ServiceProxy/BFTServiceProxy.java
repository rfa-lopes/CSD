package csd.wallet.Replication.ServiceProxy;

import bftsmart.communication.client.ReplyListener;
import bftsmart.reconfiguration.util.ECDSAKeyLoader;
import bftsmart.tom.AsynchServiceProxy;
import bftsmart.tom.RequestContext;
import bftsmart.tom.core.messages.TOMMessage;
import bftsmart.tom.core.messages.TOMMessageType;
import bftsmart.tom.util.TOMUtil;
import csd.wallet.Replication.Result;
import csd.wallet.Replication.SignedResults;
import csd.wallet.Utils.Convert;
import csd.wallet.Utils.JSON;
import csd.wallet.Utils.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;

import static csd.wallet.Replication.Result.ErrorCode.TIME_OUT;
import static csd.wallet.Replication.Result.getError;
import static csd.wallet.Replication.Result.ok;

@Service
public class BFTServiceProxy implements ReplyListener {

    @Autowired
    AsynchServiceProxy asynchServiceProxy;

    Map<Integer, byte[]> signatureReceive;
    Map<Integer, Result> replies;
    SignedResults result;
    int numValidReplicas;
    int minReplicas;

    int TIME_OUT_SIGNED_REPLICAS_RESPONSES_IN_SECONDS = 10;

    @Value("${bftsmart.id}")
    int this_id;

    @Override
    public void reset() {
    }

    @Override
    public void replyReceived(RequestContext requestContext, TOMMessage tomMessage) {

        try {
            SignedResult signedResult = (SignedResult) Convert.toObject(tomMessage.getContent());
            byte[] reply = JSON.toJson(signedResult.getResult()).getBytes();
            byte[] signature = signedResult.getSignature();
            int id = signedResult.getId();

            ECDSAKeyLoader keyloader = new ECDSAKeyLoader(id, "", false, "EC");
            PublicKey pubKey = null;

            try {
                pubKey = keyloader.loadPublicKey();
            } catch (NoSuchAlgorithmException | InvalidKeySpecException | CertificateException e) {
                e.printStackTrace();
            }


            //PublicKey pubKey = asynchServiceProxy.getViewManager().getStaticConf().getPublicKey(id);

            boolean isValid = TOMUtil.verifySignature(pubKey, reply, signature);
            if (isValid) {
                signatureReceive.put(id, signature);
                numValidReplicas++;
                replies.put(id, signedResult.getResult());
                if (numValidReplicas >= minReplicas) {
                    result = new SignedResults(signatureReceive, replies.get(this_id));
                    //asynchServiceProxy.cleanAsynchRequest(requestContext.getOperationId());
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            Logger.error("<<BFTServiceProxy Error>>");
        }
    }

    public byte[] invoke(byte[] toByteArray) throws IOException {

        signatureReceive = new HashMap<>();
        replies = new HashMap<>();
        result = new SignedResults();
        numValidReplicas = 0;
        minReplicas = asynchServiceProxy.getViewManager().getCurrentViewF() * 3 + 1;

        System.out.println("minimini: "+minReplicas);

        asynchServiceProxy.invokeAsynchRequest(toByteArray, this, TOMMessageType.ORDERED_REQUEST);
        long timeout = System.currentTimeMillis() + TIME_OUT_SIGNED_REPLICAS_RESPONSES_IN_SECONDS * 1000;
        byte[] res = null;
        while (numValidReplicas < minReplicas)
            if (System.currentTimeMillis() >= timeout) {
                res = Convert.toBytes(getError(TIME_OUT));
                break;
            }
        if (res == null)
            res = Convert.toBytes(ok(result));
        return res;
    }
}
