package server.message;

import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class RoomMemberUpdateMessage extends Message {
	//Sample Message: 5~0~1~2~3
	public RoomMemberUpdateMessage (DatagramSocket senderSocket, InetSocketAddress destination, ArrayList<Integer> roomPlayerIDs) 
			throws SocketException, UnknownHostException {
		super(senderSocket, destination);
		
		data = String.valueOf(ROOM_MEMBER_UPDATE)+ DELIMITER; 
		for(int id : roomPlayerIDs) {
			data = data + id + DELIMITER ;
		}
	}
}
