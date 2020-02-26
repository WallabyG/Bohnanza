package game.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import game.cards.Beans;
import game.cards.Deck;
import game.players.Player;

public class Game {
	private static Scanner sc=new Scanner(System.in);
	
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
		for(Player player: players)
			System.out.println("\n"+player+"\n"+player.getTransaction());
		
		while(true) {
			System.out.print("You want to trade with : ");
			int tempTrade=sc.nextInt();
			if(0<=tempTrade && tempTrade<playerNum) {
				if(!(players.get(tempTrade).getId() == p.getId())) {
					performTrade(p,players.get(tempTrade));
					break;
				}
			}
			else if(tempTrade<0) break;
			System.err.println("Invalid Trade Index");
		}
	}
	
	public void performTrade(Player p1, Player p2) {
		for(Beans b: p1.getTransaction().getOffer()) {
			p1.getHands().remove(b);
			p1.getOpenedBeans().remove(b);
			b.setTraded(true);
		}
		for(Beans b: p2.getTransaction().getOffer()) {
			p2.getHands().remove(b);
			p2.getOpenedBeans().remove(b);
			b.setTraded(true);
		}
		p1.getOpenedBeans().addAll(p2.getTransaction().getOffer());
		p2.getOpenedBeans().addAll(p1.getTransaction().getOffer());
		p1.getTransaction().getOffer().clear();
		p2.getTransaction().getOffer().clear();
	}
	
	public void plantOpenedBeansPhase(Player p) {
		for(Player player:players)
			player.plantOpenedBeans();
	}
	
	public int getPlayerNum() {
		return playerNum;
	}

	public void drawPhase(Player p) {
		gameEndFlag=!p.draw(3);
	}

	public List<Player> getPlayers() {
		return players;
	}
	
	public boolean getGameEndFlag() {
		return gameEndFlag;
	}
	
	public void play() {
		while(!gameEndFlag) {
			for(Player p: players) {
				System.out.println("\n=======================================================");
				System.out.println("Refilled : "+deck.getRefillNum()+" | Card Left : "+deck.getLeftCardNumber()+" | Card Discarded : "+deck.getDiscardedNumber());
				plantPhase(p);
				tradePhase(p);
				plantOpenedBeansPhase(p);
				drawPhase(p);
				if(gameEndFlag) break;
			}
		}
		System.out.println("\n=======================================================");
		System.out.println("Game Over");
		for(Player p: players) {
			p.gameSet();
			System.out.println(p);
		}
	}
	
	public static void main(String[] args) {
		Game game=new Game(4);
		game.play();
	}
}
