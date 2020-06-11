package csd.wallet.Utils;

import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignedObject;

public class VerifySmartContractSignature {

    public static boolean verifies(SignedObject smartContract) throws NoSuchAlgorithmException {
        String keyAlg = "SHA256withRSA";
        Signature sig = Signature.getInstance(keyAlg);
        return false;
        //  return smartContract.verify(publicKey, sig);
    }
}
