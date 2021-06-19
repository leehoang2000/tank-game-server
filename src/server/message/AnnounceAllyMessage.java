package server.message;

import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

public class AnnounceAllyMessage extends Message{

	public AnnounceAllyMessage(DatagramSocket senderSocket, InetSocketAddress destination, int allyID) throws UnknownHostException {
		super(senderSocket, destination);
		this.data = ANNOUNCE_ALLY + DELIMITER + allyID;
	}
}
