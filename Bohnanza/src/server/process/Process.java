package server.process;

import server.message.Message;

/**
 * 
 * �޽��� ó�� ���� ���
 * 
 * @author YJH
 * @version 1.0
 *
 */
public class Process {

	/**
	 * �޽��� ó��<br>
	 * <br>
	 * �޽��� Ÿ��<br>
	 * <br>
	 * 1 - Login<br>
	 *   101 - player name duplication check<br>
	 *   102 - add player name<br>
	 * <br>
	 * 2 - Create/Join online match<br>
	 *   201 - match name duplication check<br>
	 *   202 - create online match<br>
	 *   203 - join online match
	 * 
	 * @param message ���۵� �޽���
	 * @return ��ȯ�� �޽���
	 */
	public static Object processMessage(Message message) {
		
		switch (message.getMessageType()) {
		case 101:
			return Login.checkPlayerNameDuplicate((String) message.getContents());
			
		case 102:
			Login.addPlayerName(message.getPlayerName());
			break;
		
		case 201:
			return OnlineMatch.checkMatchNameDuplicate((String) message.getContents());
		
		case 202:
			break;
			
		case 203:
			break;
		}
		
		return null;
	}
	
}
