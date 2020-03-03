package server.process;

import game.game.Updatable;

/**
 * OnlineMatch 객체의 필수적인 정보만 담은 클래스
 * @author ycm
 * @version 1.0
 */
public class MatchInfo implements Updatable, java.io.Serializable{
	private static final long serialVersionUID=1L;
	
	/**
	 * 현재 접속 인원
	 */
	private int currentPlayers;
	
	/**
	 * 방의 정원
	 */
	private int capacity;
	
	/**
	 * 생성자 메서드
	 * @param currentPlayers 현재 접속 인원
	 * @param capacity 방의 정원
	 */
	MatchInfo(int currentPlayers, int capacity){
		this.currentPlayers=currentPlayers;
		this.capacity=capacity;
	}
	
	public int getCurrentPlayers() {
		return currentPlayers;
	}
	
	public int getCapacity() {
		return capacity;
	}
}
