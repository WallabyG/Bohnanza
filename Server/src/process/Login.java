package process;

import java.util.ArrayList;

public class Login {
	
	private static final ArrayList<String> playerNameList = new ArrayList<String>();
	
	public static boolean checkPlayerNameDuplicate(String playerName) {
		return playerNameList.contains(playerName);
	}
	
}
