package message;

import java.io.Serializable;

public class Message implements Serializable {

	private static final long serialVersionUID = 1L;
	
	int messageType;
	String playerName;
	Object contents;
	
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
