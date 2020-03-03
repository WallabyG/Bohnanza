package server.process;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import game.game.Updatable;
import server.server.ServerTime;

/**
 * 
 * 온라인 매치 관리 시스템
 * 
 * @author YJH
 * @version 1.0
 *
 */
public class MatchSystem implements Updatable {

	/**
	 * 온라인 매치 이름 리스트
	 */
	private List<String> onlineMatchNameList;

	/**
	 * 온라인 매치 이름 - 비밀번호 맵<br>
	 * key : 온라인 매치 이름<br>
	 * value: 비밀번호<br>
	 */
	private Map<String, String> onlineMatchPWMap;

	/**
	 * 온라인 매치 이름 - 게임 객체 맵<br>
	 * key : 온라인 매치 이름<br>
	 * value: 게임 객체
	 */
	private Map<String, OnlineMatch> onlineMatchMap;

	/**
	 * 플레이어 이름 - 온라인 매치 이름 맵<br>
	 * key : 플레이어 이름<br>
	 * value : 온라인 매치 이름
	 */
	private Map<String, String> playerMatchMap;

	/**
	 * 생성자 메서드
	 */
	public MatchSystem() {
		onlineMatchNameList = new ArrayList<>();
		onlineMatchPWMap = new HashMap<>();
		onlineMatchMap = new HashMap<>();
		playerMatchMap = new HashMap<>();
	}

	/**
	 * 매치 이름의 중복 여부 체크
	 * 
	 * @param matchName 매치 이름
	 * @return 매치 이름의 중복 여부 반환
	 */
	public synchronized boolean checkMatchNameDuplicate(String matchName) {
		return onlineMatchNameList.contains(matchName);
	}

	/**
	 * 온라인 매치 생성
	 * 
	 * @param playerName   플레이어 이름
	 * @param matchName    매치 이름
	 * @param matchPW      매치 비밀번호
	 * @param playerNumber 플레이어 수
	 */
	public synchronized void createOnlineMatch(String playerName, ArrayList<Object> matchInfo) {
		String matchName = (String) matchInfo.get(0);
		String matchPW = (String) matchInfo.get(1);
		int capacity = (int) matchInfo.get(2);

		OnlineMatch match = new OnlineMatch(capacity);

		onlineMatchNameList.add(matchName);
		onlineMatchPWMap.put(matchName, matchPW);
		onlineMatchMap.put(matchName, match);

		System.out.println(ServerTime.getTime() + " Created Online Match [" + matchName + ":" + matchPW + "]");
	}

	public synchronized void deleteOnlineMatch(String matchName) {
		onlineMatchNameList.remove(matchName);
		onlineMatchPWMap.remove(matchName);
		onlineMatchMap.remove(matchName);
	}

	/**
	 * 온라인 매치 참여
	 * 
	 * @param playerName 플레이어 이름
	 * @param matchName  매치 이름
	 * @param matchPW    매치 비밀번호
	 * @return 온라인 매치 참여 결과<br>
	 *         <br>
	 *         0 - 매치 접속 성공<br>
	 *         1 - 매치 이름 불일치<br>
	 *         2 - 매치 가득 참<br>
	 *         3 - 매치 비밀번호 불일치<br>
	 *         -1 - 오류
	 * 
	 */
	public synchronized int joinOnlineMatch(String playerName, ArrayList<Object> matchInfo, Socket socket) {
		String matchName = (String) matchInfo.get(0);
		String matchPW = (String) matchInfo.get(1);
		if (onlineMatchNameList.contains(matchName) && matchPW == onlineMatchPWMap.get(matchName)) {
			OnlineMatch match = onlineMatchMap.get(matchName);

			if (!match.addPlayer(playerName, socket)) {
				return 2;
			}

			playerMatchMap.put(playerName, matchName);
			System.out.println(ServerTime.getTime() + " " + playerName + " Joined Match [" + matchName + "]");

			return 0;
		} else if (!onlineMatchNameList.contains(matchName)) {
			return 1;
		} else if (matchPW != onlineMatchPWMap.get(matchName)) {
			return 3;
		}

		return -1;
	}

	/**
	 * 온라인 매치 나가기
	 * 
	 * @param playerName 플레이어 이름
	 * @param matchName  매치 이름
	 */
	public synchronized void exitOnlineMatch(String playerName, String matchName) {
		if (onlineMatchNameList.contains(matchName)) {
			OnlineMatch match = onlineMatchMap.get(matchName);
			match.deletePlayer(playerName);
			playerMatchMap.remove(playerName);
			System.out.println(ServerTime.getTime() + " Deleted Online Match [" + matchName + "]");
		}
	}

	public synchronized OnlineMatch getMatchbyPlayer(String playerName) {
		return onlineMatchMap.get(playerMatchMap.get(playerName));
	}
}
