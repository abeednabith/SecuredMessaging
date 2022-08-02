package com.nsproject.server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
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
import com.nsproject.cryptography.SymmetricDecryption;

public class Server {
	private final static byte[] SALT = {(byte)129, (byte)187, (byte)151, (byte)73, (byte)225, (byte)239, (byte)49, (byte)24, 
			(byte)130, (byte)188, (byte)152, (byte)74, (byte)226, (byte)240, (byte)50, (byte)25};
	
	private ServerSocket connect(int port) throws UnknownHostException, IOException {
		ServerSocket server = new ServerSocket(port);
		System.out.println("Server Connected...");
		return server;
	}
	
	private void verifyHashFunctions(SupportFunctions helper, String plainText, String orginalHash) {
		try {
			String newHash = helper.hashing(plainText);
			if(!newHash.toUpperCase().equals(orginalHash.toUpperCase())) {
				System.out.println("\n\nhashes are not matching. messages are modified by eve.....");
			} else {
				System.out.println("\n\nHash values are equal and message is not modified by any intruders....");
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	private String decrypt(String cipherText) {
		SupportFunctions helper = new SupportFunctions();
		String plainText = null;
		SymmetricDecryption decryption = new SymmetricDecryption();
		try {
			String algorithm = "AES/CBC/PKCS5Padding";
			SecretKey secretKey = helper.getKeyFromPassword("12345abc", "12345");
			IvParameterSpec iv = helper.generateIv(SALT);
			String message = cipherText.substring(0, cipherText.indexOf("HASH"));
			plainText = decryption.decrypt(algorithm, message, secretKey, iv);			
			
			//verify hashes
			String orginalHash = cipherText.substring(cipherText.indexOf("HASH")+4);
			verifyHashFunctions(helper, message, orginalHash);
		} catch (InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch(BadPaddingException ex) {
			System.out.println("Wrong key...");
			ex.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return plainText;
	}
	
	public void recieveData(int port) {
		Socket socket = null;
		ServerSocket server = null;
		try {
			server = connect(port);
			System.out.println("Waiting for a client ...");
			
			socket = server.accept();
			System.out.println("\n\nClient accepted");
			
			DataInputStream input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			
			//decrypt the ciphertext to plaintext
			String plaintext = decrypt(input.readUTF(input));
			System.out.println("\n\nMessage from client: "+ plaintext);
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				server.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
    public static void main(String args[])
    {
        Server server = new Server();
        server.recieveData(5000);
    }
}
