package server.message;

import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class AckMessage extends Message {
	
	public AckMessage(DatagramSocket senderSocket, InetSocketAddress destination, int clientID, int roomID) throws SocketException, UnknownHostException
	{
		super(senderSocket,destination);
		this.destination = destination;
		this.data = ACK + DELIMITER + roomID + DELIMITER + clientID;
		System.out.println("AckMessageContent: "+ this.data);
	}
}
