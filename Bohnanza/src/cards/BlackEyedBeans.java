package cards;

public class BlackEyedBeans extends Beans{
	public BlackEyedBeans(){
		this.setNumber(10);
		this.setBeanometer(new int[] {0,2,4,5,6});
	}
	
	@Override
	public String toString() {
		return "BlackEyedBeans("+this.getNumber()+")";
	}
}
