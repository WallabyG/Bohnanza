package game.cards;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * ���ӿ� ����ϴ� �� Ŭ����
 * @author ycm
 * @version 1.0
 */
public class Deck {
	/**
	 * �ִ� ���� Ƚ�� == 2
	 */
	private static final int MAX_REFILL_NUM = 2;

	/**
	 * ȹ�� ����
	 */
	private List<Beans> drawDeck;
	
	/**
	 * ���� ����
	 */
	private List<Beans> discardPile;

	/**
	 * ���� ���� Ƚ��
	 */
	private int refillNum;

	/**
	 * ����Ʈ ������
	 */
	public Deck() {
		drawDeck = new LinkedList<>();
		discardPile = new LinkedList<>();
		for (int i = 6; i <= 14; i += 2) {
			for (int j = 0; j < i; j++)
				drawDeck.add(Beans.selectBeans(i));
		}
		refillNum = 0;
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

	/**
	 * ������ ī�� 1���� ����
	 * @return ���� ī�尡 �ִ� ��� �� ī�带 ���� Optional ��ü��, ���� ��� �� ��ü�� ��ȯ
	 */
	public Optional<Beans> draw() {
		if (isDeckEmpty()) {
			if (refillNum < MAX_REFILL_NUM)
				refill();
			else
				return Optional.empty();
		}
		return Optional.ofNullable(drawDeck.remove(0));
	}

	/**
	 * ī�带 ���� ���̷� ����
	 */
	public void discard(Beans b) {
		discardPile.add(b);
	}

	public void shuffle() {
		Collections.shuffle(drawDeck);
	}

	/**
	 * ȹ�� ���̸� ����
	 */
	public void refill() {
		if (isDeckEmpty()) {
			drawDeck = new LinkedList<>(discardPile);
			refillNum++;
			shuffle();
			discardPile.clear();
		} else {
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
