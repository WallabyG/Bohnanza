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
	 *   211 - �¶��� ��ġ ����<br>
	 *   221 - �¶��� ��ġ ���� ������Ʈ
	 * 
	 * @param message ���۵� �޽���
	 * @return ��ȯ�� �޽���
	 */
	public static Object processMessage(Thread thread, Message message) {
		
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
			
		case 211:
			break;
			
		case 221:
			
		}
		
		return null;
	}
	
}
