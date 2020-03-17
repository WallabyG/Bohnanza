package game.game;

import java.util.List;
import java.util.Map;
import java.util.Queue;

import game.cards.Beans;
import game.cards.Deck;
import game.players.Player;

/**
 * Game 객체의 필수적인 정보들만 담은 클래스
 * 
 * @author ycm
 * @version 1.0
 */
public class GameInfo implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private Map<String, Player> players;
	private List<String> orders;
	private Deck deck;
	private int currentPlayerIndex;
	private int capacity;

	GameInfo(Game game) {
		this.players = game.getPlayers();
		this.orders = game.getOrders();
		this.deck = game.getDeck();
		this.currentPlayerIndex = game.getCurrentPlayerIndex();
		this.capacity = game.getCapacity();
	}

	public Map<String, Player> getPlayers() {
		return players;
	}

	public Player getPlayer(String name) {
		if (players.containsKey(name)) {
			return players.get(name);
		}
		return null;
	}

	public Queue<Beans> getHands(String name) {
		if (players.containsKey(name)) {
			return players.get(name).getHands();
		}
		return null;
	}

	public List<String> getOrders() {
		return orders;
	}

	public Deck getDeck() {
		return deck;
	}

	public int getCurrentPlayerIndex() {
		return currentPlayerIndex;
	}

	public int getCapacity() {
		return capacity;
	}

}
