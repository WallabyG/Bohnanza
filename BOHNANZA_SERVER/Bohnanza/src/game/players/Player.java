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

public class Player {
	private static int order_generated=0;
	private static Scanner sc=new Scanner(System.in);
	
	private int gold;
	private int id;
	private int fieldStatus; //2 or 3 bit binary int; 1 means vacancy of its corresponding field
	private Deck deck;
	private Transaction transaction;
	private List<Field> fields;
	private List<Beans> openedBeans;
	private Queue<Beans> hands;
	
	public Player(Deck deck, int number){
		int numberOfField=number>3?2:3;
		this.gold=0;
		this.id=++order_generated;
		this.fieldStatus=0;
		this.deck=deck;
		this.transaction=new Transaction();
		this.fields=new ArrayList<Field>();
		for(int i=0;i<numberOfField;i++)
			fields.add(new Field(i));
		this.updateFieldStatus();
		this.openedBeans=new LinkedList<Beans>();
		this.hands=new LinkedList<Beans>();
	}
	
	public List<Beans> getOpenedBeans() {
		return openedBeans;
	}

	public void updateFieldStatus() {
		fieldStatus=0;
		for(Field f:fields) {
			fieldStatus=fieldStatus*2+(f.isEmpty()?1:0);
		}
	}
	
	public boolean draw(int n) {
		Optional<Beans> b;
		for(int i=0;i<n;i++) {
			b=deck.draw();
			if(b.isPresent()) hands.offer(b.get());
			else return false;
		}
		return true;
	}
	
	public void displayHands() {
		System.out.print("Your Hands | ");
		Iterator<Beans> itr=hands.iterator();
		while(itr.hasNext())
			System.out.print(itr.next()+" ");
		System.out.println();
	}
	
	public void plant(Beans b) {
		System.out.println("\n=======================================================");
		System.out.println("Plant Phase");
		System.out.println();
		System.out.println(this);
		System.out.println("You have to plant "+b);
		while(fieldStatus==0) {
			boolean overlapFlag=fields.stream()
					.map(f->f.getNumber())
					.anyMatch(n->n==b.getNumber());
			if(overlapFlag) break;
			this.harvest();
		}
		Field field;
		try {
			field=fields.stream()
				.filter(f->f.getNumber()==b.getNumber())
				.collect(() -> new ArrayList<Field>(),
						(c,s) -> c.add(s),
						(lst1,lst2) -> lst1.addAll(lst2)).get(0);
		}
		catch(IndexOutOfBoundsException e){
			field=fields.stream()
					.filter(f->f.isEmpty())
					.collect(() -> new ArrayList<Field>(),
							(c,s) -> c.add(s),
							(lst1,lst2) -> lst1.addAll(lst2)).get(0);
		}
		field.plant(b);
		System.out.println();
		System.out.println(this);
		this.updateFieldStatus();
	}
	
	public void plantPhase() {
		String additional;
		if(!hands.isEmpty()) plant(hands.poll());
		System.out.println(hands);
		while(true) {
			System.out.print("Plant another bean : ");
			additional=sc.nextLine();
			if(additional.toLowerCase().equals("y")) {
				if(!hands.isEmpty()) plant(hands.poll());
				else System.out.println("Your hand is Empty!");
				break;
			}
			else if(additional.toLowerCase().contentEquals("n")) break;
		}
	}
	
	public Beans getHandsByOrder(int order) {
		return ((LinkedList<Beans>)hands).get(order);
	}
	
	public Transaction getTransaction() {
		return transaction;
	}
	
	public void setTransaction() {
		int tempOffer;
		int tempDemand;
		
		transaction.getOffer().clear();
		transaction.getDemand().clear();
		
		System.out.println("\n=======================================================");
		System.out.println("Setting Transaction Phase");
		while(true) {
			System.out.println("\n  ---------------------------------------------------");
			System.out.print("Your Offer | ");
			System.out.println(transaction.getOffer());
			displayHands();
			System.out.print("You want to offer : ");
			tempOffer=sc.nextInt();
			if(0<=tempOffer && tempOffer<hands.size()) {
				if(!transaction.addToOffer(getHandsByOrder(tempOffer))) System.out.println("You already added that bean to offer");
			}
			else if(tempOffer<0) break;
			else System.out.println("Invalid Bean Index");
		}
		
		while(true) {
			System.out.println("\n  ---------------------------------------------------");
			System.out.print("Your Offer | ");
			System.out.println(transaction.getOffer());
			System.out.print("Opened Beans | ");
			System.out.println(openedBeans);
			System.out.print("You want to offer : ");
			tempOffer=sc.nextInt();
			if(0<=tempOffer && tempOffer<openedBeans.size()) {
				Beans temp=openedBeans.get(tempOffer);
				if(temp.isTraded()) System.out.println("You already traded that bean");
				else if(!transaction.addToOffer(temp)) System.out.println("You already added that bean to offer");
			}
			else if(tempOffer<0) break;
			else System.out.println("Invalid Bean Index");
		}
		
		while(true) {
			System.out.println("\n  ---------------------------------------------------");
			System.out.print("Your Demand | ");
			System.out.println(transaction.getDemand());
			System.out.print("You demand : ");
			tempDemand=sc.nextInt();
			if(6<=tempDemand && tempDemand<=20 && tempDemand%2==0)
				transaction.getDemand().add(tempDemand);
			else if(tempDemand<0) break;
			else System.out.println("Invalid Bean Number");
		}
		System.out.println("Your Transaction");
		System.out.println(transaction);
	}
	
	public void plantOpenedBeans() {
		int tempPlant;
		Beans b;
		System.out.println("\n=======================================================");
		System.out.println("Planting Traded Beans Phase");
		while(!openedBeans.isEmpty()) {
			System.out.println("\n  ---------------------------------------------------");
			System.out.print("Remained Beans : ");
			System.out.println(openedBeans);
			System.out.print("You want to plant : ");
			tempPlant=sc.nextInt();
			if(0<=tempPlant && tempPlant<openedBeans.size()) {
				b=openedBeans.get(tempPlant);
				this.plant(b);
				openedBeans.remove(b);
			}
			else System.out.println("Invalid Bean Index");
		}
	}
	
	public Queue<Beans> getHands() {
		return hands;
	}

	public void setHands(Queue<Beans> hands) {
		this.hands = hands;
	}

	public void harvest() {
		System.out.println("\n=======================================================");
		System.out.println("Harvest Phase");
		int tempHarvest;
		while(true) {
			System.out.println("\n  ---------------------------------------------------");
			
			List<Integer> candidates=fields.stream()
					.filter(f->f.getSize()>1)
					.mapToInt(f->f.getId())
					.collect(() -> new ArrayList<>(),
							(c,s) -> c.add(s),
							(lst1, lst2) -> lst1.addAll(lst2));
			if(candidates.isEmpty())
				candidates=fields.stream()
				.filter(f->f.getSize()>0)
				.mapToInt(f->f.getId())
				.collect(() -> new ArrayList<>(), 
						(c,s) -> c.add(s), 
						(lst1, lst2) -> lst1.addAll(lst2));
			
			System.out.print("You can harvest | ");
			for(int i:candidates)
				System.out.print(fields.get(i)+" ");
			System.out.println();
			
			tempHarvest=sc.nextInt();
			if(tempHarvest<=0) break;
			if(candidates.contains(tempHarvest))
				gold+=fields.get(tempHarvest).harvest(deck);
			else
				System.err.println("Invalid Field Number");
			this.updateFieldStatus();
		}
	}
	
	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public String toString() {
		String str2return="Player "+id+"("+gold+"G) | ";
		for(Field f:fields)
			str2return+=f.toString()+"\t";
		return str2return;
	}

}
