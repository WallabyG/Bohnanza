package game.game;

import java.util.List;
import java.util.Map;

import game.cards.Deck;
import game.players.Player;

/**
 * Game 객체의 필수적인 정보들만 담은 클래스
 * @author ycm
 * @version 1.0
 */
public class GameInfo implements Updatable, java.io.Serializable{
	private static final long serialVersionUID=1L;
	
	private Map<String, Player> players;
	private List<String> orders;
	private Deck deck;
	private Player currentPlayer;
	private int playerNum;

	GameInfo(Game game){
		this.players=game.getPlayers();
		this.orders=game.getOrders();
		this.deck=game.getDeck();
		this.currentPlayer=game.getCurrentPlayer();
		this.playerNum=game.getPlayerNum();
	}
}
