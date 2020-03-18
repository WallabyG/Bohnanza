package game.cards;

public class SoyBeans extends Beans{
	private static final long serialVersionUID=1L;
	
	public SoyBeans(){
		this.setNumber(12);
		this.setBeanometer(new int[] {0,2,4,6,7});
	}
	
	@Override
	public String toString() {
		return "SoyBeans("+this.getNumber()+")";
	}
}
