package game.cards;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * 게임에 사용하는 덱 클래스
 * @author ycm
 * @version 1.0
 */
public class Deck {
	/**
	 * 최대 리필 횟수 == 2
	 */
	private static final int MAX_REFILL_NUM = 2;

	/**
	 * 획득 더미
	 */
	private List<Beans> drawDeck;
	
	/**
	 * 버림 더미
	 */
	private List<Beans> discardPile;

	/**
	 * 현재 리필 횟수
	 */
	private int refillNum;

	/**
	 * 디폴트 생성자
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
	 * 덱에서 카드 1장을 뽑음
	 * @return 뽑을 카드가 있는 경우 그 카드를 가진 Optional 객체를, 없을 경우 빈 객체를 반환
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
	 * 카드를 버림 더미로 버림
	 */
	public void discard(Beans b) {
		discardPile.add(b);
	}

	public void shuffle() {
		Collections.shuffle(drawDeck);
	}

	/**
	 * 획득 더미를 리필
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
