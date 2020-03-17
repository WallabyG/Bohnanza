package server.process;

import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import game.game.Game;
import game.game.GameInfo;
import server.message.Message;
import server.server.ServerTime;
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
	 * 온라인 매치에 참여하는 플레이어 이름 - OOP 맵
	 */
	private Map<String, ObjectOutputStream> players;

	/**
	 * 매치 이름
	 */
	private String name;

	/**
	 * 방의 정원
	 */
	private int capacity;

	public OnlineMatch(String name, int capacity) {
		this.name = name;
		this.capacity = capacity;
		this.game = new Game(this);
		players = new HashMap<>();
		Collections.synchronizedMap(players);
	}

	public String getName() {
		return name;
	}

	public int getCapacity() {
		return capacity;
	}

	public List<String> getPlayerList() {
		return new ArrayList<>(players.keySet());
	}

	public GameInfo getGameInfo() {
		return game.getInfo();
	}

	public int getCurrentPlayers() {
		return players.size();
	}

	public boolean isFull() {
		return getCurrentPlayers() == capacity;
	}

	public synchronized void processInput(Message message) {
		game.setMessage(message);
	}

	public void start() {
		game.start();
		System.out.println(ServerTime.getTime() + " Match [" + name + "] started");
	}

	/**
	 * 플레이어를 추가
	 * 
	 * @param playerName 추가할 플레이어의 이름
	 * @return 플레이어 추가 성공 여부
	 */
	public synchronized boolean addPlayer(String playerName, ObjectOutputStream out) {
		if (game.addPlayer(playerName)) {
			players.put(playerName, out);
			return true;
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
	 * 매치에 참여한 모든 클라이언트의 정보를 업데이트
	 * 
	 * @param messageType 업데이트 메시지 타입
	 */
	public synchronized void update(int messageType) {
		for (String name : players.keySet())
			updateIndividual(name, messageType);
		System.out.println(ServerTime.getTime() + " Update Information of Match [" + name + "]");
	}

	/**
	 * 클라이언트의 정보를 업데이트
	 * 
	 * @param name        업데이트를 전송할 클라이언트의 이름
	 * @param messageType 업데이트 메시지 타입
	 */
	public synchronized void updateIndividual(String name, int messageType) {
		int sendMessageType = 0;
		Object information;
		switch (messageType) {
		case 203:
			information = 0;
			sendMessageType = 203;
			break;
		case 212:
		case 213:
		case 221:
			information = getCurrentPlayers();
			sendMessageType = 221;
			break;
		case 301:
			information = game.getInfo();
			sendMessageType = 301;
			break;
		default:
			return;
		}
		(new UpdateSender(players.get(name), sendMessageType, information)).start();
	}
}
