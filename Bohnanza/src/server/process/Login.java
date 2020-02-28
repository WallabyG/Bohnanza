package server.process;

import java.util.ArrayList;

/**
 * 
 * �α��� ���� ���
 * 
 * @author YJH
 * @version 1.0
 *
 */
public class Login {
	
	/**
	 * �÷��̾� �̸� ����Ʈ
	 */
	private static final ArrayList<String> playerNameList = new ArrayList<>();
	
	/**
	 * �÷��̾� �̸��� �ߺ� ���� üũ
	 * 
	 * @param playerName �÷��̾� �̸�
	 * @return �÷��̾� �̸��� �ߺ� ���� ��ȯ
	 */
	public static boolean checkPlayerNameDuplicate(String playerName) {
		return playerNameList.contains(playerName);
	}
	
	/**
	 * �÷��̾� �̸� �߰�
	 * 
	 * @param playerName �÷��̾� �̸�
	 */
	public static void addPlayerName(String playerName) {
		playerNameList.add(playerName);
	}
	
}
