package server.server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import game.game.Updatable;
import server.message.Message;

/**
 * 정보 업데이트 송신용 스레드
 * 
 * @author ycm
 * @version 1.0
 */
public class UpdateSender {
	/**
	 * 소켓
	 */
	Socket socket;

	/**
	 * 오브젝트 반환용 스트림
	 */
	ObjectOutputStream out;

	/**
	 * 업데이트할 정보
	 */
	Updatable information;

	/**
	 * 메시지 타입
	 */
	int messageType;

	public UpdateSender(Socket socket, int messageType, Updatable information) {
		this.socket = socket;
		this.messageType = messageType;
		this.information = information;
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
		}
	}

	public void run() {
		try {
			if (out != null)
				out.writeObject(new Message(messageType, "UPDATE", information));
		} catch (IOException e) {
		}
	}
}
