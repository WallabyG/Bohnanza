package server.process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import game.game.Game;

/**
 * 
 * �¶��� ��ġ ���� ���
 * 
 * @author YJH
 * @version 1.0
 *
 */
public class OnlineMatch {

	/**
	 * �¶��� ��ġ �̸� ����Ʈ
	 */
	private static final ArrayList<String> onlineMatchNameList = new ArrayList<>();
	
	/**
	 * �¶��� ��ġ �̸� - ��й�ȣ ��<br>
	 * key  : �¶��� ��ġ �̸�<br>
	 * value: ��й�ȣ<br>
	 */
	private static final Map<String, String> onlineMatchPWMap = new HashMap<>();
	
	/**
	 * �¶��� ��ġ �̸� - ���� ��ü ��<br>
	 * key  : �¶��� ��ġ �̸�<br>
	 * value: ���� ��ü
	 */
	private static final Map<String, Game> onlineMatchMap = new HashMap<>();
	
	/**
	 * ��ġ �̸��� �ߺ� ���� üũ
	 * 
	 * @param matchName ��ġ �̸�
	 * @return ��ġ �̸��� �ߺ� ���� ��ȯ
	 */
	public static boolean checkMatchNameDuplicate(String matchName) {
		return onlineMatchNameList.contains(matchName);
	}
	
	/**
	 * �¶��� ��ġ ����
	 * 
	 * @param playerName �÷��̾� �̸�
	 * @param matchName ��ġ �̸�
	 * @param matchPW ��ġ ��й�ȣ
	 * @param playerNumber �÷��̾� ��
	 */
	public static void createOnlineMatch(String playerName, String matchName, String matchPW, int playerNumber) {
		Game game = new Game(playerNumber);
		
		onlineMatchNameList.add(matchName);
		onlineMatchPWMap.put(matchName, matchPW);
		onlineMatchMap.put(matchName, game);
	}
	
	/**
	 * �¶��� ��ġ ����
	 * 
	 * @param playerName �÷��̾� �̸�
	 * @param matchName ��ġ �̸�
	 * @param matchPW ��ġ ��й�ȣ
	 * @return �¶��� ��ġ ���� ���<br>
	 *  0 - Match join success
	 *  1 - Match name mismatch
	 *  2 - Match full
	 *  3 - Match PW mismatch
	 * -1 - Wrong
	 * 
	 */
	public static int joinOnlineMatch(String playerName, String matchName, String matchPW) {
		if (onlineMatchNameList.contains(matchName) && matchPW == onlineMatchPWMap.get(matchName)) {
			Game game = onlineMatchMap.get(matchName);
			
			if (!game.addPlayer(playerName)) {
				return 2;
			}
			
			return 0;
		} else if (!onlineMatchNameList.contains(matchName)) {
			return 1;
		} else if (onlineMatchNameList.contains(matchName) && matchPW != onlineMatchPWMap.get(matchName)) {
			return 3;
		}
		
		return -1;
	}
	
	/**
	 * �¶��� ��ġ ������
	 * 
	 * @param playerName �÷��̾� �̸�
	 * @param matchName ��ġ �̸�
	 */
	public static void exitOnlineMatch(String playerName, String matchName) {
		
	}
	
}
