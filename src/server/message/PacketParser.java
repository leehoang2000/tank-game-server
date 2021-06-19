package server.message;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

import server.TankGameServer;

public class PacketParser {
	protected TankGameServer tankGameServer;

	public PacketParser(TankGameServer tankGameServer, DatagramSocket serverSocket) {
		super();
		this.tankGameServer = tankGameServer;
		new ServerSideListener(this, serverSocket).start();
	}

	public void parse(DatagramPacket packet) throws IOException {
		String message = new String(packet.getData()).trim();
		String[] messageComponents = message.split(Message.DELIMITER);
		int opcode = Integer.valueOf(messageComponents[0]);

		InetSocketAddress client = (InetSocketAddress) packet.getSocketAddress();
		switch (opcode) {
			case Message.REQUEST_CONNECT: {
				new HandleNewClientThread(client).start();
				break;
			}
			case Message.PING: {
				int roomID = Integer.valueOf(messageComponents[1]);
				new HandlePingThread(client, roomID).start();
				break;
			}
			case Message.TANKPOS: {
				int roomID = Integer.valueOf(messageComponents[1]);
				int clientID = Integer.valueOf(messageComponents[2]);
				new HandleTankPosThread(client, roomID, clientID, message).start();
				break;
			}
			case Message.SHOOT: {
				int roomID = Integer.valueOf(messageComponents[1]);
				int clientID = Integer.valueOf(messageComponents[2]);
				new HandleShootThread(client, roomID, clientID, message).start();
				break;
			}
			case Message.DEATH: {
				break;
			}
			case Message.HEALTH_VALUE: {
				int roomID = Integer.valueOf(messageComponents[1]);
				int clientID = Integer.valueOf(messageComponents[2]);
				int newHealthValue = Integer.valueOf(messageComponents[3]);
				new HandleHealthValueThread(roomID, clientID, message, newHealthValue).start();

			}
		}
	}

	private class HandleNewClientThread extends Thread {
		private InetSocketAddress client;

		public HandleNewClientThread(InetSocketAddress client) {
			super();
			this.client = client;
		}

		@Override
		public void run() {
			try {
				tankGameServer.handleNewClient(client);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private class HandlePingThread extends Thread {
		private InetSocketAddress client;
		private int roomID;

		public HandlePingThread(InetSocketAddress client, int roomID) {
			super();
			this.client = client;
			this.roomID = roomID;
		}

		@Override
		public void run() {
			tankGameServer.getRoomByID(roomID).resetTimeout(client);
		}
	}

	private class HandleTankPosThread extends Thread {
		private int clientID;
		private int roomID;
		private String message;

		public HandleTankPosThread(InetSocketAddress client, int roomID, int clientID, String message) {
			super();
			this.clientID = clientID;
			this.roomID = roomID;
			this.message = message;
		}

		@Override
		public void run() {
			try {
				tankGameServer.getRoomByID(roomID).broadcast(message, clientID);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private class HandleShootThread extends Thread {
		private String message;
		private int clientID;
		private int roomID;

		public HandleShootThread(InetSocketAddress client, int roomID, int clientID, String message) {
			super();
			this.clientID = clientID;
			this.roomID = roomID;
			this.message = message;
		}

		@Override
		public void run() {
			try {
				tankGameServer.getRoomByID(roomID).broadcast(message, clientID);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private class HandleHealthValueThread extends Thread {
		private String message;
		private int newHealthValue;
		private int clientID;
		private int roomID;

		public HandleHealthValueThread(int roomID, int clientID, String message, int newHealthValue) {
			super();
			this.clientID = clientID;
			this.roomID = roomID;
			this.message = message;
			this.newHealthValue = newHealthValue;
		}

		@Override
		public void run() {
			try {
				tankGameServer.getRoomByID(roomID).broadcast(message, clientID);
				tankGameServer.getRoomByID(roomID).updatePlayerHealth(clientID, newHealthValue);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
