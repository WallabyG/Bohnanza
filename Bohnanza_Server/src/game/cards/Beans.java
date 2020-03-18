package game.cards;

/**
 * 게임에 사용하는 콩 카드 클래스
 * @author ycm
 * @version 1.0
 */
public class Beans implements java.io.Serializable{
	
	private static final long serialVersionUID=1L;
	
	/**
	 * ID 생성을 위한 클래스 멤버
	 */
	private static int order_generated=0;
	
	/**
	 * 콩의 거래 여부
	 */
	private boolean isTraded;
	
	/**
	 * 콩 카드의 ID
	 */
	private int id;
	
	/**
	 * 콩 카드의 종류(장수)
	 */
	private int number;
	
	/**
	 * 콩값
	 */
	private int[] beanometer;
	
	/**
	 * 콩 카드 이미지 경로
	 */
	private String imgPath;
	
	/**
	 * 생성자 메서드
	 */
	public Beans(){
		this.id=order_generated++;
		this.isTraded=false;
		this.number=0;
		this.beanometer=new int[4];
	}
	
	/**
	 * 콩의 거래 여부를 반환
	 * @return 콩의 거래 여부
	 */
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

	/**
	 * n에 따라 콩 카드를 선택
	 * @param n 콩 카드의 종류(장수)
	 * @return 해당하는 콩 인스턴스
	 */
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
		return this.id==((Beans)obj).getId();
	}
	
	@Override
	public String toString() {
		return "Beans("+number+")";
	}
}
