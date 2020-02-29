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
	 * 1 - �α���<br>
	 *   101 - �÷��̾� �̸� �ߺ� üũ<br>
	 *   102 - �÷��̾� �̸� �߰�<br>
	 * <br>
	 * 2 - �¶��� ��ġ ����/����<br>
	 *   201 - ��ġ �̸� �ߺ� üũ<br>
	 *   202 - �¶��� ��ġ ����<br>
	 *   203 - �¶��� ��ġ ����
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
