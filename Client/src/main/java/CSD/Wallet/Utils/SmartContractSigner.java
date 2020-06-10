package CSD.Wallet.Utils;

import CSD.Wallet.Models.SmartContract;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;

public class SmartContractSigner {

    public static SignedObject sign(SmartContract smc) {
        String keyAlg = "SHA256withRSA";
        KeyStore ks = loadKeyStore();
        PrivateKey privateKey = null;
        SignedObject signedObject = null;
        try {
            privateKey = (PrivateKey) ks.getKey("client", "password".toCharArray());
            Signature signature = Signature.getInstance(keyAlg);
            signature.initSign(privateKey);
            signedObject = new SignedObject(smc, privateKey, signature);
        } catch (KeyStoreException | NoSuchAlgorithmException |
                UnrecoverableKeyException | InvalidKeyException | IOException | SignatureException e) {
            e.printStackTrace();
        }
        return signedObject;
    }

    private static KeyStore loadKeyStore(){
        KeyStore keyStore = null;
        try {
            keyStore = KeyStore.getInstance("JKS");
            keyStore.load(new FileInputStream("clientKeystore.jks"), "password".toCharArray());
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException e) {
            e.printStackTrace();
        }
        return keyStore;
    }
}
