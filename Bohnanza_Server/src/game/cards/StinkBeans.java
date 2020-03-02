package game.cards;

public class StinkBeans extends Beans{
	private static final long serialVersionUID=1L;
	
	public StinkBeans(){
		this.setNumber(16);
		this.setBeanometer(new int[] {0,3,5,7,8});
	}
	
	@Override
	public String toString() {
		return "StinkBeans("+this.getNumber()+")";
	}
}
