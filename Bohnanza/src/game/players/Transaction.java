package game.players;

import java.util.LinkedList;
import java.util.List;

import game.cards.Beans;

public class Transaction {
	private List<Beans> offer;
	private List<Integer> demand;
	
	public Transaction() {
		offer=new LinkedList<Beans>();
		demand=new LinkedList<Integer>();
	}

	public boolean addToOffer(Beans b) {
		if(offer.contains(b)) return false;
		else {
			offer.add(b);
			return true;
		}
	}
	
	public List<Beans> getOffer() {
		return offer;
	}

	public void setOffer(List<Beans> offer) {
		this.offer = offer;
	}

	public List<Integer> getDemand() {
		return demand;
	}

	public void setDemand(List<Integer> demand) {
		this.demand = demand;
	}
	
	public String toString() {
		return "Offer | "+offer+"\nDemand | "+demand;
	}
}
