package CSD.Wallet.Crypto.Utils;

import CSD.Wallet.Crypto.OnionBuilder;
import CSD.Wallet.Crypto.Order.HomoOpeInt;
import CSD.Wallet.Crypto.Random.HomoRand;
import CSD.Wallet.Crypto.Searchable.HomoSearch;
import CSD.Wallet.Crypto.Sum.HomoAdd;
import CSD.Wallet.Services.LocalRepo.LocalRepo;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.util.Base64;

import static CSD.Wallet.Services.LocalRepo.KeyType.*;

public class OnionBuilderOperation {

    public enum Operation {
        ONION_EQUALITY, ONION_ORDER, ONION_ADD, ONION_SEARCH
    }

    static LocalRepo repo = LocalRepo.getInstance();

    public static String generateOnion(Operation op, byte[] value) {
        OnionBuilder onionBuilder = new OnionBuilder(value);
        String RNDKey = null;
        SecretKey RNDKeySecret;
        String iv = null;
        switch (op) {
            case ONION_EQUALITY:
                RNDKey = repo.getKey(RND);
                byte[] RNDKeySecretBytes = Base64.getDecoder().decode(RNDKey);
                RNDKeySecret = new SecretKeySpec(RNDKeySecretBytes, 0, RNDKeySecretBytes.length, "AES");
                iv = repo.getKey(IV);
                String DETKey = repo.getKey(DET);
                byte[] DETKeySecretBytes = Base64.getDecoder().decode(DETKey);
                SecretKey DETKeySecret = new SecretKeySpec(DETKeySecretBytes, 0, DETKeySecretBytes.length, "AES");
                return onionBuilder.DET(DETKeySecret).RND(RNDKeySecret, Base64.getDecoder().decode(iv)).getB64Result();
            case ONION_ORDER:
                RNDKey = repo.getKey(RND);
                RNDKeySecretBytes = Base64.getDecoder().decode(RNDKey);
                RNDKeySecret = new SecretKeySpec(RNDKeySecretBytes, 0, RNDKeySecretBytes.length, "AES");
                iv = repo.getKey(IV);
                String OPEKey = repo.getKey(OPE);
                HomoOpeInt ope = new HomoOpeInt(OPEKey);
                return HomoRand.encrypt(RNDKeySecret, Base64.getDecoder().decode(iv), String.valueOf(ope.encrypt(new BigInteger(value).intValue())));
            case ONION_SEARCH:
                String SRKey = repo.getKey(SR);
                return HomoSearch.encrypt(SRKey, new String(value));
        }
        return null;
    }

    public static String parseOnion(Operation op, byte[] value) {
        OnionBuilder onionBuilder = new OnionBuilder(value);
        String RNDKey = null;
        SecretKey RNDKeySecret;
        String iv = null;
        switch (op) {
            case ONION_EQUALITY:
                return null; //TODO
            case ONION_ORDER:
                return null; //TODO
            case ONION_SEARCH:
                return null; //TODO
        }
        return null;
    }

    public static BigInteger encryptHomoAdd(BigInteger bi) {
        PaillierKey PLKey = repo.getPk();
        try {
            return HomoAdd.encrypt(bi, PLKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BigInteger decryptHomoAdd(BigInteger bi) {
        PaillierKey PLKey = repo.getPk();
        try {
            return HomoAdd.decrypt(bi, PLKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}


