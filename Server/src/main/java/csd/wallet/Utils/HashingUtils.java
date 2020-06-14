package csd.wallet.Utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class HashingUtils {

    private static final int IT = 10000;
    private static final int HASH_SIZE = 512;
    private static final String HASH_ALG = "PBKDF2WithHmacSHA512";
    private static final String SHA_256 = "SHA-256";

    public static String hashPwd(String password) {
        return hashPwd(password, null, 0);
    }

    public static String hashPwd(String password, byte[] salt, int it) {
        try {
            if (salt == null)
                salt = generateSalt();
            if (it == 0)
                it = IT;
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, it, HASH_SIZE);
            SecretKeyFactory skf = SecretKeyFactory.getInstance(HASH_ALG);
            byte[] hash = skf.generateSecret(spec).getEncoded();
            return it + ":" + encodeBase64(salt) + ":" + encodeBase64(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public static boolean validatePassword(String toValidate, String original) {
        String[] stored = original.split(":");
        int it = Integer.parseInt(stored[0]);
        byte[] salt = decodeBase64(stored[1]);
        String toValidateHashedPwd = hashPwd(toValidate, salt, it);
        return toValidateHashedPwd.equals(original);
    }

    private static String encodeBase64(byte[] toEncode) {
        return Base64.getEncoder().encodeToString(toEncode);
    }

    private static byte[] decodeBase64(String toDecode) {
        return Base64.getDecoder().decode(toDecode);
    }

    private static byte[] generateSalt() {
        SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }
}
