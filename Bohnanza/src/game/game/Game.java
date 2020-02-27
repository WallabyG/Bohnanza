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
public class Game {
	private static Scanner sc = new Scanner(System.in);

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
	 * 매치에 참여하는 플레이어 숫자
	 */
	private int playerNum;

	/**
	 * 게임의 종료를 판단하는 플래그
	 */
	private boolean gameEndFlag;

	/**
	 * 매치 이름
	 */
	private String matchName;

	/**
	 * 매치 비밀번호
	 */
	private String matchPW;

	public String getMatchName() {
		return matchName;
	}

	public void setMatchName(String matchName) {
		this.matchName = matchName;
	}

	public String getMatchPW() {
		return matchPW;
	}

	public void setMatchPW(String matchPW) {
		this.matchPW = matchPW;
	}

	/**
	 * 생성자 메서드(테스트를 위해 플레이어 이름은 임의로 작성)
	 * 
	 * @param playerNum
	 */
	public Game(int playerNum) {
		this.playerNum = playerNum;
		this.gameEndFlag = false;
		this.deck = new Deck();
		players = new HashMap<String, Player>();
		for (int i = 0; i < this.playerNum; i++)
			players.put(i + "", new Player(i + "", deck, this.playerNum));
		orders = new ArrayList<String>(players.keySet());
		shuffleOrder();
		for (String name : players.keySet())
			players.get(name).draw(5);
	}

	/**
	 * 내 콩 심기 단계
	 * 
	 * @param p
	 */
	public void plantPhase(Player p) {
		p.plantPhase();
	}

	/**
	 * 거래하기 단계
	 * 
	 * @param p
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
	 * @param p1
	 * @param p2
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
	 * @param p
	 */
	public void plantOpenedBeansPhase(Player p) {
		for (String name : players.keySet())
			players.get(name).plantOpenedBeans();
	}

	/**
	 * 콩 보충하기 단계
	 * 
	 * @param p
	 */
	public void drawPhase(Player p) {
		gameEndFlag = !p.draw(3);
	}

	/**
	 * 매치 방의 정원을 반환
	 * 
	 * @return
	 */
	public int getPlayerNum() {
		return playerNum;
	}

	/**
	 * 현재 접속한 인원 수를 반환
	 * 
	 * @return
	 */
	public int getCurrentUsers() {
		return players.size();
	}

	/**
	 * 새로운 플레이어를 추가
	 * 
	 * @param name
	 * @return
	 */
	public boolean addPlayer(String name) {
		if (!isRoomFull()) {
			players.put(name, new Player(name, deck, playerNum));
			orders.add(name);
			shuffleOrder();
			return true;
		}
		return false;
	}

	/**
	 * 방이 가득 찼는지 알려줌
	 * 
	 * @return
	 */
	public boolean isRoomFull() {
		return playerNum == getCurrentUsers();
	}

	/**
	 * 플레이어들의 순서를 임의로 바꿈
	 */
	public void shuffleOrder() {
		Collections.shuffle(orders);
	}

	public boolean getGameEndFlag() {
		return gameEndFlag;
	}

	/**
	 * 게임 시작
	 */
	public void play() {
		while (!gameEndFlag) {
			for (String name : orders) {
				System.out.println("\n=======================================================");
				System.out.println("Refilled : " + deck.getRefillNum() + " | Card Left : " + deck.getLeftCardNumber()
						+ " | Card Discarded : " + deck.getDiscardedNumber());
				plantPhase(players.get(name));
				tradePhase(players.get(name));
				plantOpenedBeansPhase(players.get(name));
				drawPhase(players.get(name));
				if (gameEndFlag)
					break;
			}
		}
		System.out.println("\n=======================================================");
		System.out.println("Game Over");
		for (String name : orders) {
			players.get(name).gameSet();
			System.out.println(players.get(name));
		}
	}

	public static void main(String[] args) {
		Game game = new Game(4);
		game.play();
	}
}
