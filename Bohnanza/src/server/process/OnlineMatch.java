package server.process;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import game.game.Game;
import game.game.Updatable;
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
	 * 방의 정원
	 */
	private int capacity;

	/**
	 * 현재 접속 인원 수
	 */
	private int currentPlayers;

	public MatchInfo getInfo() {
		return new MatchInfo(currentPlayers, capacity);
	}

	OnlineMatch(int capacity) {
		this.capacity = capacity;
		this.game = new Game(this.capacity);
		players = new HashMap<>();
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
		Updatable information;
		switch (messageType) {
		case 211:
			information = this.getInfo();
			break;
		case 301:
			information = this.game.getInfo();
			break;
		default:
			return;
		}
		for (String name : players.keySet())
			(new UpdateSender(players.get(name), messageType, information)).start();
	}
}
