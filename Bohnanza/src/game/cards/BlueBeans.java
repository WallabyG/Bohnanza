package game.cards;

public class BlueBeans extends Beans{
	private static final long serialVersionUID=1L;
	
	public BlueBeans(){
		this.setNumber(20);
		this.setBeanometer(new int[] {0,4,6,8,10});
	}
	
	@Override
	public String toString() {
		return "BlueBeans("+this.getNumber()+")";
	}
}
