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

public class SymmetricDecryption {
	/**
	 * Initialize our cipher using the DECRYPT_MODE to decrypt the message content
	 * @param secret key, cipherText, Iniatialization Vector
	 * @return encrypted message
	 */
	public String decrypt(String algorithm, String cipherText, SecretKey key, IvParameterSpec iv) 
			throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
			InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
		    
		    Cipher cipher = Cipher.getInstance(algorithm);
		    cipher.init(Cipher.DECRYPT_MODE, key, iv);
		    byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText));
		    
		    return new String(plainText);
		}

}
