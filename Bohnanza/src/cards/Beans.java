package cards;

public class Beans {
	private static int order_generated=0;
	
	private boolean isTraded;
	private int id;
	private int number;
	private int[] beanometer;
	private String imgPath;
	
	public Beans(){
		this.id=order_generated++;
		this.isTraded=false;
		this.number=0;
		this.beanometer=new int[4];
	}
	
	public boolean isTraded() {
		return isTraded;
	}

	public void setTraded(boolean isTraded) {
		this.isTraded = isTraded;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public static Beans selectBeans(int n) {
		Beans bean2return;
		
		switch(n) {
		case 6:
			bean2return=new GardenBeans();
			break;
		case 8:
			bean2return=new RedBeans();
			break;
		case 10:
			bean2return=new BlackEyedBeans();
			break;
		case 12:
			bean2return=new SoyBeans();
			break;
		case 14:
			bean2return=new GreenBeans();
			break;
		case 16:
			bean2return=new StinkBeans();
			break;
		case 18:
			bean2return=new ChiliBeans();
			break;
		case 20:
			bean2return=new BlueBeans();
			break;
		default:
			bean2return=new Beans();
			break;
		}
		
		return bean2return;
	}
	
	public int getId() {
		return id;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int[] getBeanometer() {
		return beanometer;
	}

	public void setBeanometer(int[] beanometer) {
		this.beanometer = beanometer;
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.id==((Beans)obj).id;
	}
	
	@Override
	public String toString() {
		return "Beans("+number+")";
	}
}
