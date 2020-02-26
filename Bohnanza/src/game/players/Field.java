package game.players;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import game.cards.Beans;
import game.cards.Deck;

public class Field {
	private List<Beans> field;
	private int number;
	private int size;
	private int id;
	
	public Field(int id){
		field=new LinkedList<Beans>();
		this.number=0;
		this.id=id;
		this.size=0;
	}
	
	public void plant(Beans b) {
		if(number==0 || number==b.getNumber()) {
			field.add(b);
			number=b.getNumber();
			size++;
		}
		//raise exception if number does not fit
	}
	
	public int harvest(Deck deck) {
		if(isEmpty()) return 0;
		
		int[] beanometer=this.peek().getBeanometer();
		int price=0;
		for(;price<beanometer.length && size>=beanometer[price];price++);
		price--;
		Iterator<Beans> itr=field.iterator();
		for(int i=0;i<price;i++) {
			itr.next();
			itr.remove();
		}
		while(itr.hasNext()) {
			Beans b=itr.next();
			b.setTraded(false);
			deck.discard(b);
			itr.remove();
		}
		number=0;
		size=0;
		return price;
	}
	
	public Beans peek() {
		return ((LinkedList<Beans>)field).peek();
	}
	
	public boolean isEmpty() {
		return size==0;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id=id;
	}

	public List<Beans> getField() {
		return field;
	}

	public void setField(List<Beans> field) {
		this.field = field;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	public String toString() {
		return "|Field "+id+" : "+ (isEmpty()?"Empty":number+" X "+size)+"|";
	}
	
}
