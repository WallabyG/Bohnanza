package server.message;

import java.io.Serializable;

/**
 * 
 * Ŭ���̾�Ʈ���� ������ �۽��ϴ� ��ü
 * 
 * @author YJH
 * @version 1.0
 *
 */
public class Message implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * �޽��� Ÿ��<br>
	 * <br>
	 * ���̽� �� Ÿ���� {@link server.process.Process#processMessage(Message) processMessage}
	 */
	int messageType;
	
	/**
	 * �÷��̾� �̸�
	 */
	String playerName;
	
	/**
	 * �޽��� ����
	 */
	Object contents;
	
	/**
	 * @param messageType �޽��� Ÿ��<br>
	 * ���̽� �� Ÿ���� {@link server.process.Process#processMessage(Message) processMessage}
	 * @param playerName �÷��̾� �̸�
	 * @param contents �޽��� ����
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
