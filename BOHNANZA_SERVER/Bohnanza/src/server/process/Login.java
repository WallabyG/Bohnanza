package server.process;

import java.util.ArrayList;

public class Login {
	
	private static final ArrayList<String> playerNameList = new ArrayList<>();
	
	public static boolean checkPlayerNameDuplicate(String playerName) {
		return playerNameList.contains(playerName);
	}
	
	public static void addPlayerName(String playerName) {
		playerNameList.add(playerName);
	}
	
}
