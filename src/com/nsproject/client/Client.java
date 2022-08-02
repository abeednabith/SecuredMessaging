package com.nsproject.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import com.nsproject.cryptography.SupportFunctions;
import com.nsproject.cryptography.SymmetricEncryption;

public class Client {
	private final static byte[] SALT = {(byte)129, (byte)187, (byte)151, (byte)73, (byte)225, (byte)239, (byte)49, (byte)24, 
			(byte)130, (byte)188, (byte)152, (byte)74, (byte)226, (byte)240, (byte)50, (byte)25};
	
	private Socket connect(String address, int port) throws UnknownHostException, IOException {
		Socket socket = new Socket(address, port);
		System.out.println("Client Connected...");
		return socket;
	}
	
	private String encrypt(String message) {
		SupportFunctions helper = new SupportFunctions();
		String cipherText = null;
		String originalHash  = null;
		SymmetricEncryption encryption = new SymmetricEncryption();
		try {
			SecretKey secretKey = helper.getKeyFromPassword("12345abc", "12345");
			String algorithm = "AES/CBC/PKCS5Padding";
			IvParameterSpec iv = helper.generateIv(SALT);
			cipherText = encryption.encrypt(algorithm, message, secretKey, iv);
			//generate a MD5 hash
			originalHash = helper.hashing(cipherText);
		} catch (InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException
				| InvalidAlgorithmParameterException | BadPaddingException | IllegalBlockSizeException | InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return cipherText.concat("HASH"+originalHash);
	}
	
	public void sendData(String message) {
		Socket socket = null;
		try {
			socket = connect("127.0.0.1", 5000);
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			out.writeUTF(encrypt(message));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args){
		Client client = new Client();
		//client.sendData("Hi brooo....");
		
		client.sendData("I am sending you a 1 million dollar...");
		
		//client.sendData("Please keep this money secretely...");
	}
}
