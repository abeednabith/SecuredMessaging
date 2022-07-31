package com.nsproject.cryptography;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class SymmetricEncryption {
	
	/**
	 * Encrypt the input message
	 * @param secret key, inout string, Iniatialization Vector
	 * @return encrypted message
	 */
	public String encrypt(String algorithm, String input, SecretKey key, IvParameterSpec iv) 
			throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
			InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
		
		    //create an instance from the Cipher class by using the getInstance() method.
		    Cipher cipher = Cipher.getInstance(algorithm);
		    
		    //configure a cipher instance using the init() method with a secret key, IV, and ENCRYPT_MODE
		    cipher.init(Cipher.ENCRYPT_MODE, key, iv);
		    
		    //we encrypt the input string by invoking the doFinal() method
		    byte[] cipherText = cipher.doFinal(input.getBytes());
		    
		    return Base64.getEncoder().encodeToString(cipherText);
		}

}
