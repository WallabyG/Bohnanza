package game.cards;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class Deck {
	private static final int MAX_REFILL_NUM=2;
	
	private List<Beans> drawDeck;
	private List<Beans> discardPile;
	
	private int refillNum;
	
	public Deck(){
		drawDeck=new LinkedList<>();
		discardPile=new LinkedList<>();
		for(int i=6;i<=14;i+=2) {
			for(int j=0;j<i;j++)
				drawDeck.add(Beans.selectBeans(i));
		}
		refillNum=0;
		shuffle();
	}
	
	public boolean isDeckEmpty() {
		return drawDeck.isEmpty();
	}
	
	public int getRefillNum() {
		return refillNum;
	}

	public void setRefillNum(int refillNum) {
		this.refillNum = refillNum;
	}

	public Optional<Beans> draw() {
		if(isDeckEmpty()) {
			if(refillNum<MAX_REFILL_NUM)
				refill();
			else
				return Optional.empty();
		}
		return Optional.ofNullable(drawDeck.remove(0));
	}
	
	public void discard(Beans b) {
		discardPile.add(b);
	}
	
	public void shuffle() {
		Collections.shuffle(drawDeck);
	}
	
	public void refill() {
		if(isDeckEmpty()) {
			drawDeck=new LinkedList<>(discardPile);
			refillNum++;
			shuffle();
			discardPile.clear();
		}
		else {
			System.out.println("Deck is not empty!");
		}
	}
	
	public int getLeftCardNumber() {
		return drawDeck.size();
	}
	
	public int getDiscardedNumber() {
		return discardPile.size();
	}
}
