package game.cards;

public class GardenBeans extends Beans{
	public GardenBeans(){
		this.setNumber(6);
		this.setBeanometer(new int[] {0,2,2,3});
	}
	
	@Override
	public String toString() {
		return "GardenBeans("+this.getNumber()+")";
	}
}
