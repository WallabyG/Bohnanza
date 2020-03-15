package game.players;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import game.cards.Beans;
import game.cards.Deck;

/**
 * 플레이어의 밭 클래스
 * 
 * @author ycm
 * @version 1.0
 */
public class Field implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 밭에 심은 콩의 종류
	 */
	private int number;

	/**
	 * 밭에 심은 콩 카드의 수
	 */
	private int size;

	/**
	 * 밭의 ID
	 */
	private String id;

	/**
	 * 밭을 나타내는 리스트
	 */
	private List<Beans> field;

	/**
	 * 생성자 메서드
	 * 
	 * @param id 밭 ID
	 */
	public Field(String name, int id) {
		field = new LinkedList<Beans>();
		this.number = 0;
		this.id = name + id;
		this.size = 0;
	}

	public String getId() {
		return id;
	}

	public List<Beans> getField() {
		return field;
	}

	public int getNumber() {
		return number;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	@Override
	public boolean equals(Object obj) {
		return this.id.equals(((Field) obj).getId());
	}

	@Override
	public String toString() {
		return "|Field " + id + " : " + (isEmpty() ? "Empty" : number + " X " + size) + "|";
	}

	/**
	 * 콩 심기
	 * 
	 * @param b 심을 콩
	 */
	public void plant(Beans b) {
		if (number == 0 || number == b.getNumber()) {
			field.add(b);
			number = b.getNumber();
			size++;
		}
		// raise exception if number does not fit
	}

	/**
	 * 콩 수확
	 * 
	 * @param deck 게임에 사용하는 덱
	 * @return 콩값
	 */
	public int harvest(Deck deck) {
		if (isEmpty())
			return 0;

		int[] beanometer = this.peek().getBeanometer();
		int price = 0;
		for (; price < beanometer.length && size >= beanometer[price]; price++)
			;
		price--;
		Iterator<Beans> itr = field.iterator();
		for (int i = 0; i < price; i++) {
			itr.next();
			itr.remove();
		}
		while (itr.hasNext()) {
			Beans b = itr.next();
			b.setTraded(false);
			deck.discard(b);
			itr.remove();
		}
		number = 0;
		size = 0;
		return price;
	}

	public Beans peek() {
		return ((LinkedList<Beans>) field).peek();
	}

	public boolean isEmpty() {
		return size == 0;
	}

}
