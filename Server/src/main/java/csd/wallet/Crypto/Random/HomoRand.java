package csd.wallet.Crypto.Random;
/* 
 * Random-Based Encryption
 * formally using Symmetric Encryption w/ AES, 
 * ECB and padding PKCS#5
 */

import csd.wallet.Crypto.Utils.HelpSerial;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class HomoRand {
	
	public static SecretKey generateKey()  {
		KeyGenerator keyGen = null;
		try {
			keyGen = KeyGenerator.getInstance("AES");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		keyGen.init(128); // for example
		return keyGen.generateKey();	
	}
	
	public static SecretKey keyFromString(String str){
		return (SecretKey) HelpSerial.fromString(str);
	}
	
	public static String stringFromKey(SecretKey secretKey){
		return HelpSerial.toString(secretKey);
	}
	
	

	public static byte[] generateIV() {
		byte[] randomBytes = new byte[16];
		Random rand = new Random();
		rand.nextBytes(randomBytes);
		return randomBytes;
		//return SecureRandom.getSeed(16);
	}
	
	
	public static RandomKeyIv keyIvFromString(String str){
		return (RandomKeyIv) HelpSerial.fromString(str);
	}
	
	public static String stringFromKeyIv(RandomKeyIv secretKey){
		return HelpSerial.toString(secretKey);
	}
	
	public static RandomKeyIv generateKeyIv(){
		return new RandomKeyIv();
	}
	
    public static byte[] encrypt(SecretKey key, byte[] initVector, byte[] value) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            byte[] encrypted = cipher.doFinal(value);
            return encrypted;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
    
    public static String encrypt(SecretKey key, byte[] initVector, String value){
    	try {
			byte[] aux = encrypt(key, initVector, value.getBytes("UTF-8"));
			return Base64.getEncoder().encodeToString(aux);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}    	
    	return null; 	
    }
    
    public static String encrypt(String keyIv, String valor){
    	
    	return encrypt(HomoRand.keyIvFromString(keyIv).getKey(), HomoRand.keyIvFromString(keyIv).getiV(), valor);
    }
    
    public static String decrypt(String keyIv, String valor){
    	
    	return decrypt(HomoRand.keyIvFromString(keyIv).getKey(), HomoRand.keyIvFromString(keyIv).getiV(), valor);
    }
    
    public static String decrypt(SecretKey key, byte[] initVector, String value){ 

    	try {
        	byte[] newValue = Base64.getDecoder().decode(value.getBytes("US-ASCII"));
			return new String(decrypt(key, initVector, newValue), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
    }
    

    public static byte[] decrypt(SecretKey key, byte[] initVector, byte[] encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            byte[] original = cipher.doFinal(encrypted);
            return original;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
    
    
}
