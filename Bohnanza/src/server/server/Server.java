package server.server;

import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;

/**
 * 
 * 서버 클래스
 * 
 * @author YJH
 * @version 1.0
 *
 */
public class Server {

	public static final int testPortNumber = 55555;

	public static int portNumber;

	/**
	 * 클라이언트 스트림 저장용 해시맵
	 */
	HashMap<String, ObjectOutputStream> clients;

	Server() {
		clients = new HashMap<String, ObjectOutputStream>();
		Collections.synchronizedMap(clients);
	}

	public void start() {
		ServerSocket serverSocket = null;
		Socket socket = null;

		try {
			serverSocket = new ServerSocket(portNumber);
			System.out.println("[ BOHNANZA SERVER ]");
			System.out.println();

			while (true) {
				System.out.println("Listening at port " + portNumber + " ...");
				socket = serverSocket.accept();
				System.out.println("Connected from [" + socket.getInetAddress() + ":" + socket.getPort() + "]");
				ServerReceiver thread = new ServerReceiver(socket);
				thread.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			portNumber = Integer.parseInt(args[0]);
		} catch (Exception e) {
			portNumber = testPortNumber;
		}

		(new Server()).start();
	}
}
