package server;

public class Server {
	public static void main(String[] args) throws Exception 
	{
		int server_port = 55000;
		String server_ip = "localhost";
		new TankGameServer(server_ip,server_port);
	}
}
