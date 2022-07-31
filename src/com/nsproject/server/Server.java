package com.nsproject.server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server {
	private ServerSocket connect(int port) throws UnknownHostException, IOException {
		ServerSocket server = new ServerSocket(port);
		System.out.println("Server Connected...");
		return server;
	}
	
	public void recieveData(int port) {
		try {
			ServerSocket server = connect(port);
			System.out.println("Waiting for a client ...");
			
			Socket socket = server.accept();
			System.out.println("Client accepted");
			
			DataInputStream input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			
			System.out.println(input.readUTF(input));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    public static void main(String args[])
    {
        Server server = new Server();
        server.recieveData(5000);
    }
}
