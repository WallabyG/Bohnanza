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
		
		onlineMatchPWMap.put(matchName, matchPW);
		onlineMatchMap.put(matchName, game);
	}
	
	public static boolean joinOnlineMatch(String playerName, String matchName, String matchPW) {
		return true;
	}
	
	public static void exitOnlineMatch(String playerName, String matchName) {
		
	}
	
}
