package server.message;

import java.io.Serializable;

/**
 * 
 * 클라이언트에서 서버로 송신하는 객체
 * 
 * @author YJH
 * @version 1.0
 *
 */
public class Message implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 메시지 타입<br>
	 * <br>
	 * 케이스 별 타입은 {@link server.process.Process#processMessage(Message) processMessage}
	 */
	int messageType;
	
	/**
	 * 플레이어 이름
	 */
	String playerName;
	
	/**
	 * 메시지 내용
	 */
	Object contents;
	
	/**
	 * @param messageType 메시지 타입<br>
	 * 케이스 별 타입은 {@link server.server.ServerReceiver#processMessage(Message) processMessage}
	 * @param playerName 플레이어 이름
	 * @param contents 메시지 내용
	 */
	public Message(int messageType, String playerName, Object contents) {
		this.messageType = messageType;
		this.playerName = playerName;
		this.contents = contents;
	}

	public int getMessageType() {
		return messageType;
	}

	public String getPlayerName() {
		return playerName;
	}

	public Object getContents() {
		return contents;
	}

	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public void setContents(Object contents) {
		this.contents = contents;
	}
	
}
