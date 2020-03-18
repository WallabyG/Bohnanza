package game.cards;

public class GreenBeans extends Beans{
	private static final long serialVersionUID=1L;
	
	public GreenBeans(){
		this.setNumber(14);
		this.setBeanometer(new int[] {0,3,5,6,7});
	}
	
	@Override
	public String toString() {
		return "GreenBeans("+this.getNumber()+")";
	}
}
