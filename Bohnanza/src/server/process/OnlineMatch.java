package server.process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import game.game.Game;

/**
 * 
 * 온라인 매치 관련 기능
 * 
 * @author YJH
 * @version 1.0
 *
 */
public class OnlineMatch {

	/**
	 * 온라인 매치 이름 리스트
	 */
	private static final ArrayList<String> onlineMatchNameList = new ArrayList<>();
	
	/**
	 * 온라인 매치 이름 - 비밀번호 맵<br>
	 * key  : 온라인 매치 이름<br>
	 * value: 비밀번호<br>
	 */
	private static final Map<String, String> onlineMatchPWMap = new HashMap<>();
	
	/**
	 * 온라인 매치 이름 - 게임 객체 맵<br>
	 * key  : 온라인 매치 이름<br>
	 * value: 게임 객체
	 */
	private static final Map<String, Game> onlineMatchMap = new HashMap<>();
	
	/**
	 * 매치 이름의 중복 여부 체크
	 * 
	 * @param matchName 매치 이름
	 * @return 매치 이름의 중복 여부 반환
	 */
	public static boolean checkMatchNameDuplicate(String matchName) {
		return onlineMatchNameList.contains(matchName);
	}
	
	/**
	 * 온라인 매치 생성
	 * 
	 * @param playerName 플레이어 이름
	 * @param matchName 매치 이름
	 * @param matchPW 매치 비밀번호
	 * @param playerNumber 플레이어 수
	 */
	public static void createOnlineMatch(String playerName, String matchName, String matchPW, int playerNumber) {
		Game game = new Game(playerNumber);
		
		onlineMatchNameList.add(matchName);
		onlineMatchPWMap.put(matchName, matchPW);
		onlineMatchMap.put(matchName, game);
	}
	
	/**
	 * 온라인 매치 참여
	 * 
	 * @param playerName 플레이어 이름
	 * @param matchName 매치 이름
	 * @param matchPW 매치 비밀번호
	 * @return 온라인 매치 참여 결과<br>
	 * <br>
	 *  0 - 매치 접속 성공<br>
	 *  1 - 매치 이름 불일치<br>
	 *  2 - 매치 가득 참<br>
	 *  3 - 매치 비밀번호 불일치<br>
	 * -1 - 오류
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
	 * 온라인 매치 나가기
	 * 
	 * @param playerName 플레이어 이름
	 * @param matchName 매치 이름
	 */
	public static void exitOnlineMatch(String playerName, String matchName) {
		
	}
	
}
