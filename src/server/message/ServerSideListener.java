package server.message;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ServerSideListener extends Thread {
	private PacketParser PacketParser;
	private DatagramSocket serverSocket;

	public ServerSideListener(PacketParser packetParser, DatagramSocket serverSocket) {
		super();
		this.PacketParser = packetParser;
		this.serverSocket = serverSocket;
	}

	@Override
	public void run() {
		System.out.println("The server is listening!");
		while (true) {
			byte[] receiveData = new byte[1024];

			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			try {
				serverSocket.receive(receivePacket);
				PacketParser.parse(receivePacket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}
}