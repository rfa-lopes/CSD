package csd.wallet.Replication.ServiceProxy;

import bftsmart.reconfiguration.util.RSAKeyLoader;
import bftsmart.tom.util.TOMUtil;
import csd.wallet.Replication.Result;

import java.security.PrivateKey;
import java.util.Base64;

public class SignedResult {

    private Result result;
    private String signature;
    private RSAKeyLoader keyLoader;


    private SignedResult(Result result){
        this.result = result;
        PrivateKey privKey;
        try {
            privKey = keyLoader.loadPrivateKey();
            signature = Base64.getEncoder().encodeToString(TOMUtil.signMessage(privKey,result.toString().getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static SignedResult createSignedResult(Result result){
        return new SignedResult(result);
    }

    public Result getResult() {
        return result;
    }

    public String getSignature(){
        return signature;
    }

}
