package csd.wallet.Crypto;

import csd.wallet.Crypto.Deterministic.HomoDet;
import csd.wallet.Crypto.Random.HomoRand;
import csd.wallet.Crypto.Random.HomoRandAESPkcs7;
import csd.wallet.Crypto.Utils.HelpSerial;

import javax.crypto.SecretKey;
import java.util.Base64;

public class OnionBuilder {

    private byte[] value;

    public OnionBuilder(byte[] value) {
        this.value = value;
    }

    public OnionBuilder encryptDET(String key){
        value = HomoDet.encrypt(HomoDet.keyFromString(key), value);
        return this;
    }

    public OnionBuilder encryptRND(String key){
        value = HomoRand.encrypt(key, new String(value)).getBytes();
        return this;
    }

    public OnionBuilder encryptRNDAESPKCS7(String key){
        value = HomoRandAESPkcs7.encrypt(key, new String(value)).getBytes();
        return this;
    }

    //------------------------------------------------------------------

    public OnionBuilder decryptDET(String key){
        value = HomoDet.decrypt(HomoDet.keyFromString(key), value);
        return this;
    }

    public OnionBuilder decryptRND(String key){
        value = HomoRand.decrypt(key, new String(value)).getBytes();
        return this;
    }

    public OnionBuilder decryptRNDAESPKCS7(String key){
        value = HomoRandAESPkcs7.decrypt(key, new String(value)).getBytes();
        return this;
    }

    //------------------------------------------------------------------

    public String getB64Result(){
        return Base64.getEncoder().encodeToString(value);
    }

}
