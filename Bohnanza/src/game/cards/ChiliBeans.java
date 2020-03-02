package game.cards;

public class ChiliBeans extends Beans{
	private static final long serialVersionUID=1L;
	
	public ChiliBeans(){
		this.setNumber(18);
		this.setBeanometer(new int[] {0,3,6,8,9});
	}
	
	@Override
	public String toString() {
		return "ChiliBeans("+this.getNumber()+")";
	}
}
