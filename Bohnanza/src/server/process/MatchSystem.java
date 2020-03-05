package server.process;

import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import server.server.ServerTime;

/**
 * 
 * 온라인 매치 관리 시스템
 * 
 * @author YJH
 * @version 1.0
 *
 */
public class MatchSystem {

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
		onlineMatchPWMap = new HashMap<>();
		Collections.synchronizedMap(onlineMatchPWMap);
		onlineMatchMap = new HashMap<>();
		Collections.synchronizedMap(onlineMatchMap);
		playerMatchMap = new HashMap<>();
		Collections.synchronizedMap(playerMatchMap);
	}

	/**
	 * 매치 이름의 중복 여부 체크
	 * 
	 * @param matchName 매치 이름
	 * @return 매치 이름의 중복 여부 반환
	 */
	public synchronized boolean checkMatchNameDuplicate(String matchName) {
		return onlineMatchMap.containsKey(matchName);
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

		OnlineMatch match = new OnlineMatch(matchName, capacity);

		onlineMatchPWMap.put(matchName, matchPW);
		onlineMatchMap.put(matchName, match);

		System.out.println(ServerTime.getTime() + " Created Online Match [" + matchName + ":" + matchPW + "]");
	}

	/**
	 * 온라인 매치 삭제
	 * 
	 * @param matchName 삭제할 매치 이름
	 */
	public synchronized void deleteOnlineMatch(String matchName) {
		for(String name: onlineMatchMap.keySet())
			System.out.println(name);
		if (onlineMatchMap.containsKey(matchName)) {
			OnlineMatch match = onlineMatchMap.get(matchName);
			for (String playerName : match.getPlayerSet())
				exitOnlineMatch(playerName);
			onlineMatchPWMap.remove(matchName);
			onlineMatchMap.remove(matchName);
			System.out.println(ServerTime.getTime() + " Deleted Online Match [" + matchName + "]");
		}
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
	public synchronized int joinOnlineMatch(String playerName, ArrayList<Object> matchInfo, ObjectOutputStream out) {
		String matchName = (String) matchInfo.get(0);
		String matchPW = (String) matchInfo.get(1);
		if (onlineMatchMap.containsKey(matchName) && matchPW.equals(onlineMatchPWMap.get(matchName))) {
			OnlineMatch match = onlineMatchMap.get(matchName);

			if (!match.addPlayer(playerName, out)) {
				return 2;
			}

			playerMatchMap.put(playerName, matchName);
			System.out.println(ServerTime.getTime() + " " + playerName + " Joined Match [" + matchName + "]");

			return 0;
		} else if (!onlineMatchMap.containsKey(matchName)) {
			return 1;
		} else if (!matchPW.equals(onlineMatchPWMap.get(matchName))) {
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
	public synchronized void exitOnlineMatch(String playerName) {
		if (playerMatchMap.containsKey(playerName)) {
			OnlineMatch match = getMatchbyPlayer(playerName);
			match.deletePlayer(playerName);
			playerMatchMap.remove(playerName);
			System.out.println(
					ServerTime.getTime() + " " + playerName + " Exited Online Match [" + match.getName() + "]");
		}
	}

	public synchronized OnlineMatch getMatchbyPlayer(String playerName) {
		return onlineMatchMap.get(playerMatchMap.get(playerName));
	}
}
