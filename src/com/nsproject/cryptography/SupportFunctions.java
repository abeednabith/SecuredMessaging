package com.nsproject.cryptography;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class SupportFunctions {
	/**
	 * method for Key generation(secret or private key) using AES Algorithm, its using random key
	 */
	public SecretKey generateKey() throws NoSuchAlgorithmException {
		//to get the instance of Key generation using AES Algorithm
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		
		//Initializing keygenerator with 128 bits
		keyGenerator.init(128);
		
		return keyGenerator.generateKey();
	}
	
	/**
	 * the AES secret key can be derived from a given password using a
	 * password-based key derivation function like PBKDF2
	 * @param password, salt(the salt is also a random number)
	 * @return the secret key
	 */
	public SecretKey getKeyFromPassword(String password, String salt)
		    throws NoSuchAlgorithmException, InvalidKeySpecException {
		    
	    SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
	    KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
	    SecretKey secret = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
	    return secret;
	}
	
	/**
	 * Initialization Vector is a pseudo-random value and has the same size as the block that is encrypted. 
	 * We can use the SecureRandom class to generate a random IV.
	 * @param salt 
	 */
	public IvParameterSpec generateIv(byte[] salt) {
	    return new IvParameterSpec(salt);
	}
	
	public String hashing(String input) throws NoSuchAlgorithmException {
		// getInstance method return an object of MessageDigest
		MessageDigest md = MessageDigest.getInstance("MD5");
		// digest() method is called to calculate message digest of an input and return array of byte
		byte[] messageDigest = md.digest(input.getBytes());
		// Convert byte array into signum representation
		BigInteger bi = new BigInteger(1, messageDigest);
        // Convert message digest into hex value
        String hashtext = bi.toString(16);
        
        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }
        return hashtext;
	}
}
