package CSD.Wallet.Crypto;

import CSD.Wallet.Crypto.Deterministic.HomoDet;
import CSD.Wallet.Crypto.Random.HomoRand;
import CSD.Wallet.Crypto.Random.HomoRandAESPkcs7;

import javax.crypto.SecretKey;
import java.util.Base64;

public class OnionBuilder {

    private byte[] value;

    public OnionBuilder(byte[] value) {
        this.value = value;
    }

    public OnionBuilder DET(SecretKey key){
        value = HomoDet.encrypt(key, value);
        return this;
    }

    public OnionBuilder RND(SecretKey key, byte[] iv){
        value = HomoRand.encrypt(key, iv, Base64.getEncoder().encode(value));
        return this;
    }

    public OnionBuilder RNDAESPKCS7(SecretKey key){
        value = HomoRandAESPkcs7.encrypt(HomoRand.stringFromKey(key), new String(value)).getBytes();
        return this;
    }

    public String getB64Result(){
        return Base64.getEncoder().encodeToString(value);
    }

}
