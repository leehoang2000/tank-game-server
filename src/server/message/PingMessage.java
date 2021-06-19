package server.message;

import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class PingMessage extends Message {
	
	public PingMessage(DatagramSocket senderSocket, InetSocketAddress destination) 
			throws SocketException, UnknownHostException
	{
		super(senderSocket, destination);
		this.data = PING + DELIMITER;
	}

}
