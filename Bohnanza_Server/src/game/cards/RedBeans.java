package game.cards;

public class RedBeans extends Beans{
	private static final long serialVersionUID=1L;
	
	public RedBeans(){
		this.setNumber(8);
		this.setBeanometer(new int[] {0,2,3,4,5});
	}
	
	@Override
	public String toString() {
		return "RedBeans("+this.getNumber()+")";
	}
}
