package game.players;

import java.util.LinkedList;
import java.util.List;

import game.cards.Beans;

/**
 * 거래 내용 클래스
 * @author ycm
 * @version 1.0
 */
public class Transaction {
	/**
	 * 거래 상대에게 제공하는 콩 리스트<br>
	 * 자신이 보유한 콩만 선택 가능
	 */
	private List<Beans> offer;
	
	/**
	 * 거래 상대로부터 원하는 콩 리스트<br>
	 * 자신이 보유하지 않은 콩도 선택 가능
	 */
	private List<Integer> demand;
	
	/**
	 * 생성자 메서드
	 */
	public Transaction() {
		offer=new LinkedList<Beans>();
		demand=new LinkedList<Integer>();
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

	/**
	 * 선택한 콩을 제공할 콩 리스트에 추가
	 * @param b 선택한 콩
	 * @return 콩의 추가 여부
	 */
	public boolean addToOffer(Beans b) {
		if(offer.contains(b)) return false;
		else {
			offer.add(b);
			return true;
		}
	}
}
