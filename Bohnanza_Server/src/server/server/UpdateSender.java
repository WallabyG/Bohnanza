package server.server;

import java.io.IOException;
import java.io.ObjectOutputStream;

import server.message.Message;

/**
 * 정보 업데이트 송신용 스레드
 * 
 * @author ycm
 * @version 1.0
 */
public class UpdateSender extends Thread {

	/**
	 * 오브젝트 반환용 스트림
	 */
	ObjectOutputStream out;

	/**
	 * 업데이트할 정보
	 */
	Object information;

	/**
	 * 메시지 타입
	 */
	int messageType;

	/**
	 * 생성자 메서드
	 * 
	 * @param socket      소켓
	 * @param messageType 보낼 정보에 해당하는 메시지타입
	 * @param information 업데이트할 정보
	 */
	public UpdateSender(ObjectOutputStream out, int messageType, Object information) {
		this.out = out;
		this.messageType = messageType;
		this.information = information;
	}

	public void run() {
		try {
			if (out != null) {
				out.writeObject(new Message(messageType, "UPDATE", information));
			}
		} catch (IOException e) {
		}
	}
}
