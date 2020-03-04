package server.process;

import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import game.game.Game;
import server.server.UpdateSender;

/**
 * 온라인 매치 클래스
 * 
 * @author ycm
 * @version 1.0
 */
public class OnlineMatch {

	/**
	 * 온라인 매치에 사용할 게임 클래스
	 */
	private Game game;

	/**
	 * 온라인 매치에 참여하는 플레이어 이름 - 소켓 맵
	 */
	private Map<String, Socket> players;

	/**
	 * 매치 이름
	 */
	private String name;

	/**
	 * 방의 정원
	 */
	private int capacity;

	OnlineMatch(String name, int capacity) {
		this.name = name;
		this.capacity = capacity;
		this.game = new Game(this.capacity);
		players = new HashMap<>();
		Collections.synchronizedMap(players);
	}

	public String getName() {
		return name;
	}

	public Set<String> getPlayerSet() {
		return players.keySet();
	}

	public int getCurrentPlayers() {
		return players.size();
	}

	/**
	 * 플레이어를 추가
	 * 
	 * @param playerName 추가할 플레이어의 이름
	 * @return 플레이어 추가 성공 여부
	 */
	public synchronized boolean addPlayer(String playerName, Socket socket) {
		if (!players.containsKey(playerName)) {
			if (game.addPlayer(playerName)) {
				players.put(playerName, socket);
				return true;
			}
		}
		return false;
	}

	/**
	 * 플레이어를 삭제
	 * 
	 * @param playerName 삭제할 플레이어의 이름
	 */
	public synchronized void deletePlayer(String playerName) {
		game.deletePlayer(playerName);
		players.remove(playerName);
	}

	/**
	 * 클라이언트의 정보를 업데이트
	 */
	public synchronized void update(int messageType) {
		int sendMessageType = 0;
		Object information;
		switch (messageType) {
		case 203:
			information = 0;
			sendMessageType = 203;
			break;
		case 211:
		case 213:
		case 221:
			information = getCurrentPlayers();
			sendMessageType = 221;
			break;
		case 3:
			information = this.game.getInfo();
			sendMessageType = 301;
			break;
		default:
			return;
		}
		for (String name : players.keySet())
			(new UpdateSender(players.get(name), sendMessageType, information)).start();
	}
}
