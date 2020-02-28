package server.process;

import java.util.ArrayList;

/**
 * 
 * 로그인 관련 기능
 * 
 * @author YJH
 * @version 1.0
 *
 */
public class Login {
	
	/**
	 * 플레이어 이름 리스트
	 */
	private static final ArrayList<String> playerNameList = new ArrayList<>();
	
	/**
	 * 플레이어 이름의 중복 여부 체크
	 * 
	 * @param playerName 플레이어 이름
	 * @return 플레이어 이름의 중복 여부 반환
	 */
	public static boolean checkPlayerNameDuplicate(String playerName) {
		return playerNameList.contains(playerName);
	}
	
	/**
	 * 플레이어 이름 추가
	 * 
	 * @param playerName 플레이어 이름
	 */
	public static void addPlayerName(String playerName) {
		playerNameList.add(playerName);
	}
	
}
