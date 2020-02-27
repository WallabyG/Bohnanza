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
 * ��ġ �ϳ��� �����Ǵ� Ŭ����<br>
 * ��� ���:<br>
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
	 * ��ġ�� ������ �÷��̾� ��
	 */
	private Map<String, Player> players;

	/**
	 * �� ���� ����
	 */
	private List<String> orders;

	/**
	 * ��ġ�� ����� ��
	 */
	private Deck deck;

	/**
	 * ��ġ�� �����ϴ� �÷��̾� ����
	 */
	private int playerNum;

	/**
	 * ������ ���Ḧ �Ǵ��ϴ� �÷���
	 */
	private boolean gameEndFlag;

	/**
	 * ��ġ �̸�
	 */
	private String matchName;

	/**
	 * ��ġ ��й�ȣ
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
	 * ������ �޼���(�׽�Ʈ�� ���� �÷��̾� �̸��� ���Ƿ� �ۼ�)
	 * 
	 * @param playerNum ���ӿ� �����ϴ� �÷��̾� ��
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
	 * �� �� �ɱ� �ܰ�
	 * 
	 * @param p ���� �����ϴ� �÷��̾�
	 */
	public void plantPhase(Player p) {
		p.plantPhase();
	}

	/**
	 * �ŷ��ϱ� �ܰ�
	 * 
	 * @param p ���� �����ϴ� �÷��̾�
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
	 * ������ �� �÷��̾��� �ŷ��� ����
	 * 
	 * @param p1 �ŷ��� �����ϴ� �÷��̾� 1
	 * @param p2 �ŷ��� �����ϴ� �÷��̾� 2
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
	 * ������ �� �ɱ� �ܰ�
	 * 
	 * @param p ���� �ɴ� �÷��̾�
	 */
	public void plantOpenedBeansPhase(Player p) {
		for (String name : players.keySet())
			players.get(name).plantOpenedBeans();
	}

	/**
	 * �� �����ϱ� �ܰ�
	 * 
	 * @param p ���� �����ϴ� �÷��̾�
	 */
	public void drawPhase(Player p) {
		gameEndFlag = !p.draw(3);
	}

	/**
	 * ��ġ ���� ������ ��ȯ
	 * 
	 * @return ���� ����
	 */
	public int getPlayerNum() {
		return playerNum;
	}

	/**
	 * ���� ������ �ο� ���� ��ȯ
	 * 
	 * @return ���� ���� �ο� ��
	 */
	public int getCurrentUsers() {
		return players.size();
	}

	/**
	 * ���ο� �÷��̾ �߰�
	 * 
	 * @param name ���ο� �÷��̾��� �̸�
	 * @return �߰� ���� ����
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
	 * ���� ���� á���� �˷���
	 * 
	 * @return ���� ���� á�� ��� true, �ƴ϶�� false
	 */
	public boolean isRoomFull() {
		return playerNum == getCurrentUsers();
	}

	/**
	 * �÷��̾���� ������ ���Ƿ� �ٲ�
	 */
	public void shuffleOrder() {
		Collections.shuffle(orders);
	}

	public boolean getGameEndFlag() {
		return gameEndFlag;
	}

	/**
	 * ���� ����
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
