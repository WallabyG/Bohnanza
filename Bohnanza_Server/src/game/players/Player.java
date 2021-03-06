package game.players;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

import game.cards.Beans;
import game.cards.Deck;

/**
 * 게임에 참여하는 플레이어 클래스
 * 
 * @author ycm
 * @version 1.0
 */
public class Player implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID 생성용 클래스 멤버
	 */
	private static int order_generated = 0;

	private static Scanner sc = new Scanner(System.in);

	/**
	 * 거래 단계 종료 플래그
	 */
	private boolean endTradeFlag;

	/**
	 * 현재 돈 보유량
	 */
	private int gold;

	/**
	 * 플레이어 ID
	 */
	private int id;

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
	 * 
	 * @param name   플레이어 이름
	 * @param deck   플레이어가 참여하는 게임에 사용하는 덱
	 * @param number 게임에 참여하는 플레이어 숫자
	 */
	public Player(String name, Deck deck, int number) {
		this.name = name;
		int numberOfField = number > 3 ? 2 : 3;
		this.endTradeFlag = false;
		this.gold = 0;
		this.id = ++order_generated;
		this.deck = deck;
		this.transaction = new Transaction();
		this.fields = new ArrayList<Field>();
		for (int i = 0; i < numberOfField; i++)
			fields.add(new Field(name, i));
		this.openedBeans = new LinkedList<Beans>();
		this.hands = new LinkedList<Beans>();
	}

	public String getName() {
		return name;
	}

	public Beans getHandsByOrder(int order) {
		return ((LinkedList<Beans>) hands).get(order);
	}

	public boolean getEndTradeFlag() {
		return endTradeFlag;
	}

	public void setEndTradeFlag(boolean flag) {
		endTradeFlag = flag;
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

	public List<Field> getFields() {
		return fields;
	}

	@Override
	public String toString() {
		String str2return = name + "(" + gold + "G) | ";
		for (Field f : fields)
			str2return += f + "\t";
		return str2return;
	}

	/**
	 * 거래 내용을 반환
	 * 
	 * @return 설정한 거래 클래스
	 */
	public Transaction getTransaction() {
		return transaction;
	}

	public int addOffer(int id) {
		Beans bean = openedBeans.stream().filter(b -> b.getId() == id).reduce(null, (b1, b2) -> b1 != null ? b1 : b2);
		if (bean != null) {
			if (!bean.isTraded()) {
				transaction.addToOffer(bean);
				return 431;
			}
		} else {
			bean = hands.stream().filter(b -> b.getId() == id).reduce(null, (b1, b2) -> b1 != null ? b1 : b2);
			if (bean != null) {
				transaction.addToOffer(bean);
				return 431;
			}
		}
		return 0;
	}

	public int removeOffer(int id) {
		Beans bean = transaction.getOffer().stream().filter(b -> b.getId() == id).reduce(null,
				(b1, b2) -> b1 != null ? b1 : b2);
		if (bean != null) {
			transaction.getOffer().remove(bean);
			return 432;
		}
		return 0;
	}

	public void addDemand(int number) {
		transaction.getDemand().add(number);
	}

	public void removeDemand(int number) {
		transaction.getDemand().remove(number);
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
	 * 
	 * @return 공개된 콩 카드
	 */
	public List<Beans> getOpenedBeans() {
		return openedBeans;
	}

	/**
	 * 카드 n장을 뽑아 패에 추가함
	 * 
	 * @param n 뽑을 카드 수
	 * @return 뽑을 카드가 있는 경우 true, 없을 경우 false
	 */
	public boolean draw(int n) {
		Beans b;
		for (int i = 0; i < n; i++) {
			b = deck.draw();
			if (b != null)
				hands.offer(b);
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
	 * 
	 * @param b 밭에 심을 콩
	 * @return true(성공) false(실패 : 수확 필요)
	 */
	public boolean plant(Beans b) {
		List<Field> tempFields;
		tempFields = fields.stream().filter(f -> f.getNumber() == b.getNumber()).collect(() -> new ArrayList<Field>(),
				(c, s) -> c.add(s), (lst1, lst2) -> lst1.addAll(lst2));
		if (tempFields.isEmpty()) {
			tempFields = fields.stream().filter(f -> f.isEmpty()).collect(() -> new ArrayList<Field>(),
					(c, s) -> c.add(s), (lst1, lst2) -> lst1.addAll(lst2));
		}
		if (tempFields.isEmpty())
			return false;
		tempFields.get(0).plant(b);
		return true;
	}

	/**
	 * id에 해당하는 콩을 심음
	 * 
	 * @param id 심을 콩의 id
	 * @return 403(성공) 413(실패 : 수확 필요)
	 */
	public int plantOpenedBeans(int id) {
		Beans bean = openedBeans.stream().filter(b -> b.getId() == id).reduce(null, (b1, b2) -> b1 != null ? b1 : b2);
		if (bean != null) {
			if (plant(bean))
				return 403;
		}
		return 413;
	}

	/**
	 * 첫번째 콩 심기(필수)
	 * 
	 * @return 401(성공) 411(실패 : 수확 필요)
	 */
	public int plantFirstBean() {
		if (!hands.isEmpty())
			if (!plant(hands.poll()))
				return 411;
		return 402;
	}

	/**
	 * 추가 콩 심기
	 * 
	 * @return 402(성공) 412(실패 : 수확 필요)
	 */
	public int plantAdditionalBean() {
		if (!hands.isEmpty())
			if (!plant(hands.poll()))
				return 412;
		return 409;
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
		while (true) {
			System.out.println("\n  ---------------------------------------------------");

			List<Field> candidates = fields.stream().filter(f -> f.getSize() > 1).collect(() -> new ArrayList<>(),
					(c, s) -> c.add(s), (lst1, lst2) -> lst1.addAll(lst2));
			if (candidates.isEmpty())
				candidates = fields.stream().filter(f -> f.getSize() > 0).collect(() -> new ArrayList<>(),
						(c, s) -> c.add(s), (lst1, lst2) -> lst1.addAll(lst2));

			System.out.print("You can harvest | ");
			for (Field f : candidates)
				System.out.print(f + " ");
			System.out.println();

//			tempHarvest = sc.nextInt();
//			if (tempHarvest < 0)
//				break;
//			if (candidates.contains(name + tempHarvest))
//				gold += fields.get(tempHarvest).harvest(deck);
//			else
//				System.err.println("Invalid Field Number");
		}
	}

	/**
	 * id에 해당되는 밭을 수확
	 * 
	 * @param id 수확할 밭의 id
	 * @return true(성공) false(실패 : 재요청)
	 */
	public boolean harvest(int index) {
		Field field = fields.get(index);
		if (field != null) {
			gold += field.harvest(deck);
			return true;
		}
		return false;
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
