package server.message;

import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class GameLostMessage extends Message{

	public GameLostMessage(DatagramSocket senderSocket, InetSocketAddress destination) 
			throws SocketException, UnknownHostException {
		super(senderSocket, destination);
		this.data = GAME_LOST + DELIMITER;
	}
}
	