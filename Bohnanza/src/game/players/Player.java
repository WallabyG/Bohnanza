package game.players;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Scanner;

import game.cards.Beans;
import game.cards.Deck;

/**
 * 게임에 참여하는 플레이어 클래스
 * @author ycm
 * @version 1.0
 */
public class Player {
	/**
	 * ID 생성용 클래스 멤버
	 */
	private static int order_generated = 0;
	
	private static Scanner sc = new Scanner(System.in);

	/**
	 * 현재 돈 보유량
	 */
	private int gold;
	
	/**
	 * 플레이어 ID
	 */
	private int id;
	
	/**
	 * 밭의 상태를 나타내는 2~3비트 숫자<br>
	 * 1은 해당하는 위치의 밭이 비어있음을 의미
	 */
	private int fieldStatus;
	
	/**
	 * 플레이어 이름
	 */
	private String name;
	
	/**
	 * 플레이어가 참여하는 게임에 사용하는 덱
	 */
	private Deck deck;
	
	/**
	 * 거래 클래스
	 */
	private Transaction transaction;
	
	/**
	 * 플레이어가 보유한 밭
	 */
	private List<Field> fields;
	
	/**
	 * 플레이어의 공개된 콩
	 */
	private List<Beans> openedBeans;
	
	/**
	 * 플레이어의 패
	 */
	private Queue<Beans> hands;

	/**
	 * 생성자 메서드
	 * @param name 플레이어 이름
	 * @param deck 플레이어가 참여하는 게임에 사용하는 덱
	 * @param number 게임에 참여하는 플레이어 숫자
	 */
	public Player(String name, Deck deck, int number) {
		this.name = name;
		int numberOfField = number > 3 ? 2 : 3;
		this.gold = 0;
		this.id = ++order_generated;
		this.fieldStatus = 0;
		this.deck = deck;
		this.transaction = new Transaction();
		this.fields = new ArrayList<Field>();
		for (int i = 0; i < numberOfField; i++)
			fields.add(new Field(i));
		this.updateFieldStatus();
		this.openedBeans = new LinkedList<Beans>();
		this.hands = new LinkedList<Beans>();
	}

	public String getName() {
		return name;
	}

	public Beans getHandsByOrder(int order) {
		return ((LinkedList<Beans>) hands).get(order);
	}

	public int getId() {
		return id;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public Queue<Beans> getHands() {
		return hands;
	}

	public void setHands(Queue<Beans> hands) {
		this.hands = hands;
	}

	public String toString() {
		String str2return = name + "(" + gold + "G) | ";
		for (Field f : fields)
			str2return += f + "\t";
		return str2return;
	}

	/**
	 * 거래 내용을 반환
	 * @return 설정한 거래 클래스
	 */
	public Transaction getTransaction() {
		return transaction;
	}

	/**
	 * 거래 내용을 설정
	 */
	public void setTransaction() {
		int tempOffer;
		int tempDemand;

		transaction.getOffer().clear();
		transaction.getDemand().clear();

		System.out.println("\n=======================================================");
		System.out.println("Setting Transaction Phase : " + this);
		while (true) {
			System.out.println("\n  ---------------------------------------------------");
			System.out.print("Your Offer | ");
			System.out.println(transaction.getOffer());
			displayHands();
			System.out.print("You want to offer : ");
			tempOffer = sc.nextInt();
			if (0 <= tempOffer && tempOffer < hands.size()) {
				if (!transaction.addToOffer(getHandsByOrder(tempOffer)))
					System.out.println("You already added that bean to offer");
			} else if (tempOffer < 0)
				break;
			else
				System.out.println("Invalid Bean Index");
		}

		while (!openedBeans.isEmpty()) {
			System.out.println("\n  ---------------------------------------------------");
			System.out.print("Your Offer | ");
			System.out.println(transaction.getOffer());
			System.out.print("Opened Beans | ");
			System.out.println(openedBeans);
			System.out.print("You want to offer : ");
			tempOffer = sc.nextInt();
			if (0 <= tempOffer && tempOffer < openedBeans.size()) {
				Beans temp = openedBeans.get(tempOffer);
				if (temp.isTraded())
					System.out.println("You already traded that bean");
				else if (!transaction.addToOffer(temp))
					System.out.println("You already added that bean to offer");
			} else if (tempOffer < 0)
				break;
			else
				System.out.println("Invalid Bean Index");
		}

		while (true) {
			System.out.println("\n  ---------------------------------------------------");
			System.out.print("Your Demand | ");
			System.out.println(transaction.getDemand());
			System.out.print("You demand : ");
			tempDemand = sc.nextInt();
			if (6 <= tempDemand && tempDemand <= 20 && tempDemand % 2 == 0)
				transaction.getDemand().add(tempDemand);
			else if (tempDemand < 0)
				break;
			else
				System.out.println("Invalid Bean Number");
		}
		System.out.println("Your Transaction");
		System.out.println(transaction);
	}

	/**
	 * 플레이어의 공개된 콩을 반환
	 * @return 공개된 콩 카드
	 */
	public List<Beans> getOpenedBeans() {
		return openedBeans;
	}

	/**
	 * 밭의 상태를 업데이트<br>
	 * 콩을 심거나 수확한 뒤 반드시 실행
	 */
	public void updateFieldStatus() {
		fieldStatus = 0;
		for (Field f : fields) {
			fieldStatus = fieldStatus * 2 + (f.isEmpty() ? 1 : 0);
		}
	}

	/**
	 * 카드 n장을 뽑아 패에 추가함
	 * @param n 뽑을 카드 수
	 * @return 뽑을 카드가 있는 경우 true, 없을 경우 false 
	 */
	public boolean draw(int n) {
		Optional<Beans> b;
		for (int i = 0; i < n; i++) {
			b = deck.draw();
			if (b.isPresent())
				hands.offer(b.get());
			else
				return false;
		}
		return true;
	}

	public void displayHands() {
		System.out.print("Your Hands | ");
		Iterator<Beans> itr = hands.iterator();
		while (itr.hasNext())
			System.out.print(itr.next() + " ");
		System.out.println();
	}

	/**
	 * 콩을 심음
	 * @param b 밭에 심을 콩
	 */
	public void plant(Beans b) {
		System.out.println("\n=======================================================");
		System.out.println("Plant Phase");
		System.out.println();
		System.out.println(this);
		System.out.println("You have to plant " + b);
		while (fieldStatus == 0) {
			boolean overlapFlag = fields.stream().map(f -> f.getNumber()).anyMatch(n -> n == b.getNumber());
			if (overlapFlag)
				break;
			this.harvest();
		}
		Field field;
		try {
			field = fields.stream().filter(f -> f.getNumber() == b.getNumber())
					.collect(() -> new ArrayList<Field>(), (c, s) -> c.add(s), (lst1, lst2) -> lst1.addAll(lst2))
					.get(0);
		} catch (IndexOutOfBoundsException e) {
			field = fields.stream().filter(f -> f.isEmpty())
					.collect(() -> new ArrayList<Field>(), (c, s) -> c.add(s), (lst1, lst2) -> lst1.addAll(lst2))
					.get(0);
		}
		field.plant(b);
		System.out.println();
		System.out.println(this);
		this.updateFieldStatus();
	}

	/**
	 * 콩 심기 단계
	 */
	public void plantPhase() {
		String additional;
		if (!hands.isEmpty())
			plant(hands.poll());
		System.out.println(hands);
		while (true) {
			System.out.print("Plant another bean : ");
			additional = sc.nextLine();
			if (additional.toLowerCase().equals("y")) {
				if (!hands.isEmpty())
					plant(hands.poll());
				else
					System.out.println("Your hand is Empty!");
				break;
			} else if (additional.toLowerCase().contentEquals("n"))
				break;
		}
	}

	/**
	 * 공개된 콩 심기 단계
	 */
	public void plantOpenedBeans() {
		int tempPlant;
		Beans b;
		System.out.println("\n=======================================================");
		System.out.println("Planting Opened Beans Phase : " + this);
		while (!openedBeans.isEmpty()) {
			System.out.println("\n  ---------------------------------------------------");
			System.out.print("Remained Beans : ");
			System.out.println(openedBeans);
			System.out.print("You want to plant : ");
			tempPlant = sc.nextInt();
			if (0 <= tempPlant && tempPlant < openedBeans.size()) {
				b = openedBeans.get(tempPlant);
				this.plant(b);
				openedBeans.remove(b);
			} else
				System.out.println("Invalid Bean Index");
		}
	}

	/**
	 * 밭을 선택하여 수확
	 */
	public void harvest() {
		System.out.println("\n=======================================================");
		System.out.println("Harvest Phase : " + this);
		int tempHarvest;
		while (true) {
			System.out.println("\n  ---------------------------------------------------");

			List<Integer> candidates = fields.stream().filter(f -> f.getSize() > 1).mapToInt(f -> f.getId())
					.collect(() -> new ArrayList<>(), (c, s) -> c.add(s), (lst1, lst2) -> lst1.addAll(lst2));
			if (candidates.isEmpty())
				candidates = fields.stream().filter(f -> f.getSize() > 0).mapToInt(f -> f.getId())
						.collect(() -> new ArrayList<>(), (c, s) -> c.add(s), (lst1, lst2) -> lst1.addAll(lst2));

			System.out.print("You can harvest | ");
			for (int i : candidates)
				System.out.print(fields.get(i) + " ");
			System.out.println();

			tempHarvest = sc.nextInt();
			if (tempHarvest < 0)
				break;
			if (candidates.contains(tempHarvest))
				gold += fields.get(tempHarvest).harvest(deck);
			else
				System.err.println("Invalid Field Number");
			this.updateFieldStatus();
		}
	}

	/**
	 * 게임 종료시 호출하는 메서드<br>
	 * 모든 밭을 수확
	 */
	public void gameSet() {
		for (Field f : fields)
			gold += f.harvest(deck);
	}

}
