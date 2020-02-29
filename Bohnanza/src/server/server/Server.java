package server.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import server.message.Message;
import server.process.Process;

/**
 * 
 * Ŭ���̾�Ʈ ����� ������
 * 
 * @author YJH
 * @version 1.0
 *
 */
class ResponseThread extends Thread {
	
	/**
	 * ����
	 */
	Socket socket;
	
	/**
	 * @param socket ����
	 */
	public ResponseThread(Socket socket) {
		super();
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());
			Message message = (Message)inStream.readObject();
			System.out.println("player name: " + message.getPlayerName() + " - message type: " + message.getMessageType());
			System.out.println();
			
			Object returnObj = Process.processMessage(this,message);
			
			ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
			outStream.writeObject(returnObj);
			outStream.flush();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

/**
 * 
 * ���� Ŭ����
 * 
 * @author YJH
 * @version 1.0
 *
 */
public class Server {
	
	public static final int testPortNumber = 55555;

	public static int portNumber;
	
	public static void main(String[] args) {
		
		try {
			portNumber = Integer.parseInt(args[0]);
		} catch (Exception e) {
			portNumber = testPortNumber;
		}

		System.out.println("[ BOHNANZA SERVER ]");
		System.out.println();
		
		try {
			@SuppressWarnings("resource")
			ServerSocket serverSocket = new ServerSocket(portNumber);
			
			while(true) {
				System.out.println("Listening at port " + portNumber + " ...");
				
				Socket socket = serverSocket.accept();
				
				(new ResponseThread(socket)).start();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
