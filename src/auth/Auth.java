package auth;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
 
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
 
/**
 * This class manages authorisation with regards to the login system.
 * It includes handling of both encryption and also decryption.
 * This class and its method are public, so are accessible anywhere.
 * @author Samuel
 *
 */
public class Auth {
 
    private static SecretKeySpec secretKey;
    private static byte[] key;
    
    /**
     * My 'hashPassword' method.
     * @param input
     * @return a string representation
     */
    public static String hashPassword(String input) {
        
    	try{
        
    		MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
            
            	String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            
            }

            return hexString.toString();
            
        } catch(Exception e){
           
        	throw new RuntimeException(e);
        }
    }
    
	/**
	 * 'setKey' method. This is a mutator method for the 'key' field in this
	 * class. It uses a parameter of type 'String'.
	 * 
	 * @param passedKey
	 */
    public static void setKey(String passedKey) {
    	
        MessageDigest hashed = null;
        
        try {
        
        	key = passedKey.getBytes("UTF-8");
        	hashed = MessageDigest.getInstance("SHA-1");
            key = hashed.digest(key);
            key = Arrays.copyOf(key, 16); 
            secretKey = new SecretKeySpec(key, "AES");
        
        } catch (NoSuchAlgorithmException e) {
        } catch (UnsupportedEncodingException e) { }
        
    }
    
    /**
     * Method for encryption.
     * @param plainText
     * @param string
     */
    public static void encrypt(File plainText, String string) {
    	
        try {
        	
            setKey(string);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            
            FileInputStream inputStream = new FileInputStream(plainText);
            byte[] inputBytes = new byte[(int) plainText.length()];
            inputStream.read(inputBytes);
             
            byte[] outputBytes = cipher.doFinal(inputBytes);
             
            FileOutputStream outputStream = new FileOutputStream(plainText);
            outputStream.write(outputBytes);
            inputStream.close();
            outputStream.close();
       
        } catch (Exception e) { }
        
    }
 
    /**
     * Method for decryption.
     * @param cipherText
     * @param string
     */
    public static void decrypt(File cipherText, String string) {
    	
        try {
        	
            setKey(string);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            FileInputStream inputStream = new FileInputStream(cipherText);
            byte[] inputBytes = new byte[(int) cipherText.length()];
            inputStream.read(inputBytes);
             
            byte[] outputBytes = cipher.doFinal(inputBytes);
             
            FileOutputStream outputStream = new FileOutputStream(cipherText);
            outputStream.write(outputBytes);
            inputStream.close();
            outputStream.close();
            
        } catch (Exception e) { }
                
    }
    
}