package server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import message.Message;
import process.Process;

public class Server {
	
//	Test port number
//	public static final int portNumber = 55555;
	
	public static int portNumber;

	public static void main(String[] args) {
		
		portNumber = Integer.parseInt(args[0]);

		System.out.println("[ BOHNANZA SERVER ]");
		System.out.println();
		
		try {
			@SuppressWarnings("resource")
			ServerSocket serverSocket = new ServerSocket(portNumber);
			
			while(true) {
				System.out.println("Listening at port " + portNumber + " ...");
				
				Socket socket = serverSocket.accept();
				
				ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());
				Message message = (Message)inStream.readObject();
				System.out.println("player name: " + message.getPlayerName() + " - message type: " + message.getMessageType());
				System.out.println();
				
				Object returnObj = Process.processMessage(message);
				
				ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
				outStream.writeObject(returnObj);
				outStream.flush();
				socket.close();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
