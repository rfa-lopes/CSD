package CSD.Wallet.Utils;

import org.apache.tomcat.util.codec.binary.Base64;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class VerifySignatures {

    static Map<Integer, PublicKey> pubKeys;

    static {
        pubKeys = new HashMap<>();
        for (int i = 0; i < 4; i++)
            getPublicKey(i);
    }

    public static boolean verify(Map<Integer, byte[]> signatureReceive, Result result) {
        Set<Integer> keySet = signatureReceive.keySet();
        try {
            Signature signature = Signature.getInstance("SHA256withECDSA", "SunEC");

            String res = result.isOk() ?
                    "{\"result\":" + JSON.toJson(result.getResult()) + "}" :
                    "{\"error\":\"" + JSON.toJson(result.getError()) + "\"}";

            for (Integer i : keySet) {
                signature.initVerify(pubKeys.get(i));
                signature.update(res.getBytes());
                if (!signature.verify(signatureReceive.get(i)))
                    return true;
            }
        } catch (NoSuchAlgorithmException | SignatureException | NoSuchProviderException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static void getPublicKey(int id) {
        try {
            FileReader f = new FileReader("server-public-keys/publickey" + id);
            BufferedReader r = new BufferedReader(f);
            String tmp = "";
            String key;
            for (key = ""; (tmp = r.readLine()) != null; key = key + tmp) {
            }
            f.close();
            r.close();
            PublicKey ret = getPublicKeyFromString(key);
            pubKeys.put(id, ret);
        } catch (NoSuchProviderException | NoSuchAlgorithmException | InvalidKeySpecException | IOException e) {
            e.printStackTrace();
        }
    }

    private static PublicKey getPublicKeyFromString(String key) throws NoSuchProviderException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException {
        KeyFactory keyFactory = KeyFactory.getInstance("EC", "SunEC");
        X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(key));
        PublicKey publicKey = keyFactory.generatePublic(pubKeySpec);
        return publicKey;
    }

}
