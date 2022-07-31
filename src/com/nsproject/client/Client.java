package com.nsproject.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	private Socket connect(String address, int port) throws UnknownHostException, IOException {
		Socket socket = new Socket(address, port);
		System.out.println("Client Connected...");
		return socket;
	}
	
	public void sendData(String message) {
		Socket socket = null;
		try {
			socket = connect("127.0.0.1", 5000);
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			out.writeUTF(message);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args){
		Client client = new Client();
		client.sendData("Hi brooo....");
	}
}
