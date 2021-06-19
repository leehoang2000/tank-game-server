package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Deque;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

import server.message.PacketParser;
import server.message.ServerSideSender;
import server.room.Room;

public class TankGameServer {
	private Map<Integer, Room> Rooms;
	private Deque<Integer> RoomIDs;
	private int lastRoomID = -1;
	private ServerSideSender serverSideSender;

	public TankGameServer(String server_ip, int server_port) throws IOException {
		super();
		this.serverSideSender = new ServerSideSender(server_ip, server_port);
		new PacketParser(this, serverSideSender.getServerSocket());
		
		Rooms = new ConcurrentHashMap<Integer, Room>();
		RoomIDs = new ConcurrentLinkedDeque<Integer>();
		for (int i = 0; i < 100; i++) {
			RoomIDs.add(i);
		}
	}

	public synchronized void handleNewClient(InetSocketAddress newSocket) throws IOException {
		int newRoomID = -1;
		int newClientID = -1;
		if (Rooms.get(lastRoomID) == null || Rooms.get(lastRoomID).isFull()) {
			newRoomID = createRoom();
			newClientID = Rooms.get(newRoomID).addSocket(newSocket);
		} else {
			newRoomID = lastRoomID;
			newClientID = Rooms.get(lastRoomID).addSocket(newSocket);
		}
		if (newRoomID != -1) {
			serverSideSender.sendAckMessage(newSocket, newClientID, newRoomID);
			serverSideSender.sendAnnounceAllyMessage(newSocket, allyOf(newClientID));
//			System.out.println("Ally of: " + newClientID +"is: " + allyOf(newClientID));
		}
	}

	public Room getRoomByID(int ID) {
		return Rooms.get(ID);
	}

	private int createRoom() {
		int newID = getAvailableRoomID();
		if (newID > -1) {
			Room newRoom = new Room(newID, serverSideSender,this);
			Rooms.put(newID, newRoom);
			lastRoomID = newID;
			return newRoom.getRoomID();
		} else {
			System.out.println("TankGameServer.createRoom| Server can't hold more room");
			return -1;
		}
	}

	public void deleteRoom(int roomID) {
		Rooms.remove(roomID);
		RoomIDs.add(roomID);
	}

	private int getAvailableRoomID() {
		int newID = -1;
		try {
			newID = RoomIDs.pop();
		} catch (NoSuchElementException e) {
			return -1;
		}
		return newID;
	}
	
	private int allyOf(int playerID) {
		return (playerID+2)%4;
	}
}