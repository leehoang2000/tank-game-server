package server.message;

import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class RawMessage extends Message {
	
	public RawMessage(DatagramSocket senderSocket, InetSocketAddress destination, String message) 
			throws SocketException, UnknownHostException {
		super(senderSocket,destination);
		this.data = message;
	}
	
}
