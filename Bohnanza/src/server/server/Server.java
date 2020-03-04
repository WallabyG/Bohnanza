package server.server;

import java.net.ServerSocket;
import java.net.Socket;

import server.process.MatchSystem;

/**
 * 
 * 서버 클래스
 * 
 * @author YJH
 * @version 1.0
 *
 */
public class Server {

	public static int portNumber;

	MatchSystem matchSystem;

	Server() {
		matchSystem = new MatchSystem();
	}

	public void start() {
		ServerSocket serverSocket = null;
		Socket socket = null;

		try {
			serverSocket = new ServerSocket(portNumber);
			System.out.println("[ BOHNANZA SERVER ]");
			System.out.println(ServerTime.getTime() + " Server Started");
			System.out.println(ServerTime.getTime() + "Listening at port " + portNumber);

			while (true) {
				socket = serverSocket.accept();
				System.out.println(ServerTime.getTime() + " Connected from [" + socket.getInetAddress() + ":"
						+ socket.getPort() + "]");
				ServerReceiver thread = new ServerReceiver(matchSystem, socket);
				thread.start();
			}
		} catch (Exception e) {
		}
	}

	public static void main(String[] args) {
		try {
			portNumber = Integer.parseInt(args[0]);
		} catch (Exception e) {
			System.out.println("USAGE : java server.server.Server [PortNumber]");
			System.exit(0);
		}

		(new Server()).start();
	}
}
