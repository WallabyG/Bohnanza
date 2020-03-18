package game.cards;

public class GardenBeans extends Beans{
	private static final long serialVersionUID=1L;
	
	public GardenBeans(){
		this.setNumber(6);
		this.setBeanometer(new int[] {0,2,2,3});
	}
	
	@Override
	public String toString() {
		return "GardenBeans("+this.getNumber()+")";
	}
}
