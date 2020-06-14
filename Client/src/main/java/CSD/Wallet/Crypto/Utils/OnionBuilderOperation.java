package CSD.Wallet.Crypto.Utils;

import CSD.Wallet.Crypto.Deterministic.HomoDet;
import CSD.Wallet.Crypto.OnionBuilder;
import CSD.Wallet.Crypto.Order.HomoOpeInt;
import CSD.Wallet.Crypto.Random.HomoRand;
import CSD.Wallet.Crypto.Searchable.HomoSearch;
import CSD.Wallet.Crypto.Sum.HomoAdd;
import CSD.Wallet.Services.LocalRepo.LocalRepo;

import javax.crypto.SecretKey;
import java.math.BigInteger;
import java.util.Base64;
import java.util.Locale;

import static CSD.Wallet.Services.LocalRepo.KeyType.*;


public class OnionBuilderOperation {

    public enum Operation {
        ONION_EQUALITY,ONION_ORDER, ONION_ADD, ONION_SEARCH
    }



    public static String generateOnion(Operation op, byte[] value) {
        LocalRepo repo = LocalRepo.getInstance();
        OnionBuilder onionBuilder = new OnionBuilder(value);
        String RNDKey = null;
        SecretKey RNDKeySecret;
        String iv = null;
        switch (op) {
            case ONION_EQUALITY:
                RNDKey = repo.getKey(RND);
                RNDKeySecret = HomoRand.keyFromString(RNDKey);
                iv = repo.getKey(IV);
                String DETKey = repo.getKey(DET);
                SecretKey DETKeySecret = HomoDet.keyFromString(DETKey);

                String encr = "RkEzMjNrRFN2OVRnMXVidDRBQi84WUwvTFZBTEpqTlJRNCtYazE4Uk5BVHNXMWxZTDJJV2g0TFpTVFZ4ZXJHSGxlN2VBVTI4RHFBV1pSS0VKdnRlZEE9PQ==";
                String resultRND = HomoRand.decrypt(RNDKeySecret,Base64.getDecoder().decode(iv), encr);
                String resultDet =  HomoDet.decrypt(DETKeySecret, resultRND);
                System.out.println("Resultado: " + resultDet);


                return onionBuilder.DET(DETKeySecret).RND(RNDKeySecret, Base64.getDecoder().decode(iv)).getB64Result();
            case ONION_ORDER:
                RNDKey = repo.getKey(RND);
                RNDKeySecret = HomoRand.keyFromString(RNDKey);
                String OPEKey = repo.getKey(OPE);
                HomoOpeInt ope = new HomoOpeInt(OPEKey);
                return HomoRand.encrypt(RNDKeySecret,Base64.getDecoder().decode(iv),String.valueOf(ope.encrypt(new BigInteger(value).intValue())));
            case ONION_ADD:
                String PLKey = repo.getKey(PL);
                return HomoAdd.encrypt(PLKey, new String(value));
            case ONION_SEARCH:
                String SRKey = repo.getKey(SR);
                return HomoSearch.encrypt(SRKey, new String(value));
        }
        return null;
    }
}


