package server.message;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

// TODO: Refactor

public class ServerSideSender {
	private DatagramSocket serverSocket;
	
	

	public DatagramSocket getServerSocket() {
		return serverSocket;
	}

	public ServerSideSender(String server_ip, int server_port) throws SocketException {
		try {
			this.serverSocket = new DatagramSocket(server_port,InetAddress.getByName(server_ip));
		} catch (SocketException | UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public void sendPingMessage(InetSocketAddress destination) throws IOException {
		PingMessage pm = new PingMessage(serverSocket, destination);
		pm.send();
	}
	
	public void sendAckMessage(InetSocketAddress destination, int newClientID, int newRoomID) throws IOException {
		AckMessage ackMessage = new AckMessage(serverSocket, destination, newClientID, newRoomID);
		ackMessage.send();
	}
	
	public void sendRoomMemberUpdateMessage(InetSocketAddress destination, ArrayList<Integer> roomMemberIDs) throws IOException {
		RoomMemberUpdateMessage rmum = new RoomMemberUpdateMessage(serverSocket, destination, roomMemberIDs);
		rmum.send();
	}

	public void sendRawMessage(InetSocketAddress destination, String message) throws IOException
	{
		RawMessage rm = new RawMessage(serverSocket, destination, message);
		rm.send();
	}
	
	public void sendGameLostMessage(InetSocketAddress destination) throws IOException {
		GameLostMessage glm = new GameLostMessage(serverSocket, destination);
		glm.send();
	}
	
	public void sendGameWonMessage(InetSocketAddress destination) throws IOException {
		GameWonMessage gwm = new GameWonMessage(serverSocket, destination);
		gwm.send();
	}
	
	public void sendAnnounceAllyMessage(InetSocketAddress destination, int allyID) throws IOException {
		AnnounceAllyMessage aam = new AnnounceAllyMessage(serverSocket, destination, allyID);
		aam.send();
	}
}
