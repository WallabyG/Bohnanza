package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import cards.Beans;
import cards.Deck;
import players.Player;

public class Game {
	private List<Player> players;
	private Deck deck;
	
	private int playerNum;
	private boolean gameEndFlag;
	
	public Game(int playerNum) {
		this.playerNum=playerNum;
		this.gameEndFlag=false;
		this.deck=new Deck();
		players=new ArrayList<Player>();
		for(int i=0;i<this.playerNum;i++) {
			players.add(new Player(deck,this.playerNum));
			players.get(i).draw(5);
		}
	}
	
	public void plantPhase(Player p) {
		p.plantPhase();
	}
	
	public void tradePhase(Player p) {
		Optional<Beans> b;
		for(int i=0;i<2;i++) {
			b=deck.draw();
			if(b.isPresent())
				p.getOpenedBeans().add(b.get());
			else
				gameEndFlag=true;
		}
		
		System.out.println("\n=======================================================");
		System.out.print("Opened Beans : ");
		System.out.println(p.getOpenedBeans());
		for(int i=0;i<playerNum;i++)
			players.get(i).setTransaction();
		
		// TO-DO
		// Perform Actual Trade
		for(int i=0;i<playerNum;i++)
			System.out.println("\n"+players.get(i)+"\n"+players.get(i).getTransaction());
		
		
	}
	
	public void plantTradedPhase(Player p) {
		for(int i=0;i<playerNum;i++)
			p.plantTradedBeans();
	}
	
	public int getPlayerNum() {
		return playerNum;
	}

	public void drawPhase(Player p) {
		gameEndFlag=p.draw(3);
	}

	public List<Player> getPlayers() {
		return players;
	}
	
	public boolean getGameEndFlag() {
		return gameEndFlag;
	}
	
	public void play() {
		while(!gameEndFlag) {
			for(int i=0;i<this.getPlayerNum();i++) {
				plantPhase(players.get(i));
				tradePhase(players.get(i));
				plantTradedPhase(players.get(i));
				drawPhase(players.get(i));
			}
		}
	}
	
	public static void main(String[] args) {
		Game game=new Game(2);
		game.play();
	}
}