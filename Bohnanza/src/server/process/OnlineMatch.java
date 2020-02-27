package server.process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import game.game.Game;

public class OnlineMatch {

	private static final ArrayList<String> onlineMatchNameList = new ArrayList<>();
	private static final Map<String, String> onlineMatchPWMap = new HashMap<>();
	private static final Map<String, Game> onlineMatchMap = new HashMap<>();
	
	public static boolean checkMatchNameDuplicate(String matchName) {
		return onlineMatchNameList.contains(matchName);
	}
	
	public static void createOnlineMatch(String playerName, String matchName, String matchPW, int playerNumber) {
		Game game = new Game(playerNumber);
		
		onlineMatchNameList.add(matchName);
		onlineMatchPWMap.put(matchName, matchPW);
		onlineMatchMap.put(matchName, game);
	}
	
	public static int joinOnlineMatch(String playerName, String matchName, String matchPW) {
		/*
		 * < Return Type >
		 * 
		 *  0 - Match join success
		 *  1 - Match name mismatch
		 *  2 - Match full
		 *  3 - Match PW mismatch
		 * -1 - Wrong
		 * 
		 */
		
		if (onlineMatchNameList.contains(matchName) && matchPW == onlineMatchPWMap.get(matchName)) {
			return 0;
		} else if (!onlineMatchNameList.contains(matchName)) {
			return 1;
		} else if (false) {
			return 2;
		} else if (onlineMatchNameList.contains(matchName) && matchPW != onlineMatchPWMap.get(matchName)) {
			return 3;
		}
		
		return -1;
	}
	
	public static void exitOnlineMatch(String playerName, String matchName) {
		
	}
	
}
