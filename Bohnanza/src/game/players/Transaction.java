package game.players;

import java.util.LinkedList;
import java.util.List;

import game.cards.Beans;

/**
 * �ŷ� ���� Ŭ����
 * @author ycm
 * @version 1.0
 */
public class Transaction {
	/**
	 * �ŷ� ��뿡�� �����ϴ� �� ����Ʈ<br>
	 * �ڽ��� ������ �Ḹ ���� ����
	 */
	private List<Beans> offer;
	
	/**
	 * �ŷ� ���κ��� ���ϴ� �� ����Ʈ<br>
	 * �ڽ��� �������� ���� �ᵵ ���� ����
	 */
	private List<Integer> demand;
	
	/**
	 * ������ �޼���
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
	 * ������ ���� ������ �� ����Ʈ�� �߰�
	 * @param b ������ ��
	 * @return ���� �߰� ����
	 */
	public boolean addToOffer(Beans b) {
		if(offer.contains(b)) return false;
		else {
			offer.add(b);
			return true;
		}
	}
}
