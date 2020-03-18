package game.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import game.cards.Beans;
import game.cards.Deck;
import game.players.Player;
import server.message.Message;
import server.process.OnlineMatch;

/**
 * 매치 하나에 대응되는 클래스<br>
 * 사용 방법:<br>
 * Game game=new Game(5);<br>
 * game.addPlayer("Name");<br>
 * game.play();
 * 
 * @author ycm
 * @version 1.0
 * 
 */
public class Game extends Thread {

	/**
	 * 입력 전달용 메시지
	 */
	private Message message;

	/**
	 * 매치에 참여한 플레이어 맵
	 */
	private Map<String, Player> players;

	/**
	 * 턴 진행 순서
	 */
	private List<String> orders;

	/**
	 * 매치에 사용할 덱
	 */
	private Deck deck;

	/**
	 * 현재 턴을 진행하는 플레이어
	 */
	private int currentPlayerIndex;

	/**
	 * 매치에 참여하는 플레이어 숫자
	 */
	private int capacity;

	/**
	 * 게임의 종료를 판단하는 플래그
	 */
	private volatile boolean gameEndFlag;

	/**
	 * 게임에 대응되는 온라인매치 객체
	 */
	OnlineMatch match;

	public Map<String, Player> getPlayers() {
		return players;
	}

	public List<String> getOrders() {
		return orders;
	}

	public Deck getDeck() {
		return deck;
	}

	public boolean getGameEndFlag() {
		return gameEndFlag;
	}

	public int getCurrentPlayerIndex() {
		return currentPlayerIndex;
	}

	public Player getCurrentPlayer() {
		return players.get(orders.get(currentPlayerIndex));
	}

	public boolean isOpenedBeansEmpty() {
		return !players.entrySet().stream().anyMatch(p -> !((Player) p).getOpenedBeans().isEmpty());
	}

	public boolean isTradePhaseEnded() {
		return players.entrySet().stream().allMatch(p -> ((Player) p).getEndTradeFlag());
	}

	public GameInfo getInfo() {
		return new GameInfo(this);
	}

	/**
	 * 생성자 메서드
	 * 
	 * @param playerNum 게임에 참여하는 플레이어 수
	 */
	public Game(OnlineMatch match) {
		this.match = match;
		this.capacity = match.getCapacity();
		this.gameEndFlag = false;
		this.deck = new Deck();
		players = new HashMap<>();
		orders = new ArrayList<>();
		message = null;
	}

	/**
	 * 매치 방의 정원을 반환
	 * 
	 * @return 방의 정원
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * 현재 접속한 인원 수를 반환
	 * 
	 * @return 현재 접속 인원 수
	 */
	public int getCurrentUsers() {
		return players.size();
	}

	/**
	 * 새로운 플레이어를 추가
	 * 
	 * @param name 새로운 플레이어의 이름
	 * @return 추가 성공 여부
	 */
	public boolean addPlayer(String name) {
		if (!isRoomFull()) {
			players.put(name, new Player(name, deck, capacity));
			orders.add(name);
			return true;
		}
		return false;
	}

	/**
	 * 방이 가득 찼는지 알려줌
	 * 
	 * @return 방이 가득 찼을 경우 true, 아니라면 false
	 */
	public boolean isRoomFull() {
		return capacity == getCurrentUsers();
	}

	/**
	 * 참가하고 있는 플레이어를 삭제
	 * 
	 * @param playerName 퇴장할 플레이어 이름
	 */
	public void deletePlayer(String playerName) {
		players.remove(playerName);
		orders.remove(playerName);
	}

	/**
	 * 플레이어들의 순서를 임의로 바꿈
	 */
	public void shuffleOrder() {
		Collections.shuffle(orders);
	}

	/**
	 * 선택한 두 플레이어의 거래를 수행
	 * 
	 * @param p1 거래에 참여하는 플레이어 1
	 * @param p2 거래에 참여하는 플레이어 2
	 */
	public void performTrade(Player p1, Player p2) {
		for (Beans b : p1.getTransaction().getOffer()) {
			p1.getHands().remove(b);
			p1.getOpenedBeans().remove(b);
			b.setTraded(true);
		}
		for (Beans b : p2.getTransaction().getOffer()) {
			p2.getHands().remove(b);
			p2.getOpenedBeans().remove(b);
			b.setTraded(true);
		}
		p1.getOpenedBeans().addAll(p2.getTransaction().getOffer());
		p2.getOpenedBeans().addAll(p1.getTransaction().getOffer());
		p1.getTransaction().getOffer().clear();
		p2.getTransaction().getOffer().clear();
	}

	public synchronized void setMessage(Message message) {
		this.message = message;
		notifyAll();
	}

	/**
	 * 입력 처리 메서드
	 * 
	 * @param message 입력 메시지
	 * @return 각 경우에 해당하는 업데이트 메시지 타입
	 */
	public int processInput(Message message) {
		String playerName = message.getPlayerName();
		if (players.containsKey(playerName)) {
			Player player = players.get(playerName);
			switch (message.getMessageType()) {
			case 401:
				return player.plantFirstBean();
			case 402:
				return player.plantAdditionalBean();
			case 403:
				return player.plantOpenedBeans((Integer) message.getContents());
			case 411:
			case 412:
			case 413:
				player.harvest((String) message.getContents());
				return message.getMessageType();
			case 421:
				Beans b;
				for (int i = 0; i < 2; i++) {
					b = deck.draw();
					if (b != null)
						player.getOpenedBeans().add(b);
					else
						gameEndFlag = true;
				}
				for (String name : orders)
					players.get(name).setEndTradeFlag(false);
				return 421;
			case 431:
				return player.addOffer((Integer) message.getContents());
			case 432:
				return player.removeOffer((Integer) message.getContents());
			case 433:
				player.addDemand((Integer) message.getContents());
				return 433;
			case 434:
				player.removeDemand((Integer) message.getContents());
				return 434;
			case 435:
				return 435;
			case 436:
				performTrade(player, players.get((String) message.getContents()));
				return 436;
			case 437:
				return 437;
			case 438:
				player.setEndTradeFlag(true);
				if (isTradePhaseEnded())
					return 438;
			case 441:
				if (isOpenedBeansEmpty()) {
					gameEndFlag = !getCurrentPlayer().draw(3);
					currentPlayerIndex = (currentPlayerIndex + 1) % capacity;
					return 441;
				}
				break;
			default:
				break;
			}
		}
		return 0;
	}

	/**
	 * 타입에 따른 업데이트 메서드
	 * 
	 * @param message     입력 메시지
	 * @param messageType 업데이트 메시지 타입
	 */
	public void updateByType(Message message, int messageType) {
		switch (messageType) {
		case 401:
		case 402:
		case 403:
		case 411:
		case 412:
		case 413:
			update(451);
			updateIndividual(message.getPlayerName(), messageType);
			break;
		case 421:
			update(421);
			break;
		case 431:
		case 432:
		case 433:
		case 434:
			update(451);
			break;
		case 435:
			updateIndividual((String) message.getContents(), messageType);
			break;
		case 436:
			updateIndividual(message.getPlayerName(), messageType);
			updateIndividual((String) message.getContents(), messageType);
			update(451);
			break;
		case 437:
			updateIndividual((String) message.getContents(), messageType);
			break;
		case 438:
			update(messageType);
			break;
		case 441:
			update(messageType);
			break;
		default:
			break;
		}
	}

	public void update(int messageType) {
		match.update(messageType);
	}

	public void updateIndividual(String name, int messageType) {
		match.updateIndividual(name, messageType);
	}

	public synchronized void run() {
		shuffleOrder();
		for (String name : orders)
			players.get(name).draw(5);
		while (!gameEndFlag) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
			updateByType(message, processInput(message));
		}
		for (String name : orders)
			players.get(name).gameSet();
	}

}
