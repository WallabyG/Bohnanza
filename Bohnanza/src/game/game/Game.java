package game.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

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
	private static Scanner sc = new Scanner(System.in);

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
	private int playerNum;

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
		this.playerNum = match.getCapacity();
		this.gameEndFlag = false;
		this.deck = new Deck();
		players = new HashMap<>();
		orders = new ArrayList<>();
		message = null;
	}

	/**
	 * 내 콩 심기 단계
	 * 
	 * @param p 턴을 진행하는 플레이어
	 */
	public void plantPhase(Player p) {
		p.plantPhase();
	}

	/**
	 * 거래하기 단계
	 * 
	 * @param p 턴을 진행하는 플레이어
	 */
	public void tradePhase(Player p) {
		Optional<Beans> b;
		for (int i = 0; i < 2; i++) {
			b = deck.draw();
			if (b.isPresent())
				p.getOpenedBeans().add(b.get());
			else
				gameEndFlag = true;
		}

		System.out.println("\n=======================================================");
		System.out.print("Opened Beans : ");
		System.out.println(p.getOpenedBeans());
		for (String name : orders)
			players.get(name).setTransaction();

		for (String name : players.keySet())
			System.out.println("\n" + players.get(name) + "\n" + players.get(name).getTransaction());

		while (true) {
			System.out.print("You want to trade with : ");
			String tempTrade = sc.nextLine();
			if (players.containsKey(tempTrade)) {
				if (players.get(tempTrade).getId() != p.getId()) {
					performTrade(p, players.get(tempTrade));
					break;
				}
			} else if (tempTrade.toLowerCase().equals("q"))
				break;
			System.err.println("Invalid Trade Index");
		}
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

	/**
	 * 공개된 콩 심기 단계
	 * 
	 * @param p 콩을 심는 플레이어
	 */
	public void plantOpenedBeansPhase(Player p) {
		for (String name : players.keySet())
			players.get(name).plantOpenedBeans();
	}

	/**
	 * 콩 보충하기 단계
	 * 
	 * @param p 턴을 진행하는 플레이어
	 */
	public void drawPhase(Player p) {
		gameEndFlag = !p.draw(3);
	}

	/**
	 * 매치 방의 정원을 반환
	 * 
	 * @return 방의 정원
	 */
	public int getPlayerNum() {
		return playerNum;
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
			players.put(name, new Player(name, deck, playerNum));
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
		return playerNum == getCurrentUsers();
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

	public synchronized void setMessage(Message message) {
		this.message = message;
		notifyAll();
	}

	public int processInput(Message message) {
		String playerName = message.getPlayerName();
		if (players.containsKey(playerName)) {
			Player player = players.get(playerName);
			switch (message.getMessageType()) {
			case 401:
				if (player.plant((Integer) message.getContents())) {
					return 401;
				}
				break;
			case 402:
				if (player.harvest((String) message.getContents())) {
					return 402;
				}
				break;
			default:
				break;
			}
		}
		return 0;
	}

	public void updateByType(int messageType) {
		switch (messageType) {
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

	/**
	 * 게임 시작
	 */
//	public void play() {
//		shuffleOrder();
//		for (String name : orders)
//			players.get(name).draw(5);
//		Iterator<String> itr = orders.iterator();
//		while (!gameEndFlag) {
//			if (itr.hasNext()) {
//				String name = itr.next();
//				currentPlayer = players.get(name);
//				System.out.println("\n=======================================================");
//				System.out.println("Refilled : " + deck.getRefillNum() + " | Card Left : " + deck.getLeftCardNumber()
//						+ " | Card Discarded : " + deck.getDiscardedNumber());
//				plantPhase(currentPlayer);
//				tradePhase(currentPlayer);
//				plantOpenedBeansPhase(currentPlayer);
//				drawPhase(currentPlayer);
//			} else {
//				itr = orders.iterator();
//			}
//		}
//		System.out.println("\n=======================================================");
//		System.out.println("Game Over");
//		for (String name : orders) {
//			players.get(name).gameSet();
//			System.out.println(players.get(name));
//		}
//	}

	public synchronized void run() {
		shuffleOrder();
		for (String name : orders)
			players.get(name).draw(5);
		while (!gameEndFlag) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
			updateByType(processInput(message));
		}
		for (String name : orders)
			players.get(name).gameSet();
	}

}
