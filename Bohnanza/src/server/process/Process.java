package server.process;

import server.message.Message;

/**
 * 
 * 메시지 처리 관련 기능
 * 
 * @author YJH
 * @version 1.0
 *
 */
public class Process {

	/**
	 * 메시지 처리<br>
	 * <br>
	 * 메시지 타입<br>
	 * <br>
	 * 1 - 로그인<br>
	 *   101 - 플레이어 이름 중복 체크<br>
	 *   102 - 플레이어 이름 추가<br>
	 * <br>
	 * 2 - 온라인 매치 생성/접속<br>
	 *   201 - 매치 이름 중복 체크<br>
	 *   202 - 온라인 매치 생성<br>
	 *   203 - 온라인 매치 접속
	 * 
	 * @param message 전송된 메시지
	 * @return 반환할 메시지
	 */
	public static Object processMessage(Message message) {
		
		switch (message.getMessageType()) {
		case 101:
			return Login.checkPlayerNameDuplicate((String) message.getContents());
			
		case 102:
			Login.addPlayerName(message.getPlayerName());
			break;
		
		case 201:
			return OnlineMatch.checkMatchNameDuplicate((String) message.getContents());
		
		case 202:
			break;
			
		case 203:
			break;
		}
		
		return null;
	}
	
}
