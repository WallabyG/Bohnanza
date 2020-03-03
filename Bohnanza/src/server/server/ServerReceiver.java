package server.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import server.message.Message;
import server.process.Login;
import server.process.MatchSystem;
import server.process.OnlineMatch;

/**
 * 
 * 클라이언트 응답용 스레드
 * 
 * @author YJH
 * @version 1.0
 *
 */
public class ServerReceiver extends Thread {
	/**
	 * 소켓
	 */
	Socket socket;

	/**
	 * 클라이언트 메시지 수신용 스트림
	 */
	ObjectInputStream in;

	/**
	 * 오브젝트 반환용 스트림
	 */
	ObjectOutputStream out;
	
	MatchSystem matchSystem;

	/**
	 * 생성자 메서드
	 * @param matchSystem 서버의 매치시스템
	 * @param socket 소켓
	 */
	public ServerReceiver(MatchSystem matchSystem, Socket socket) {
		super();
		this.matchSystem=matchSystem;
		this.socket = socket;
		try {
			in = new ObjectInputStream(socket.getInputStream());
			out = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
		}
	}
	
	/**
	 * 메시지 처리<br>
	 * <br>
	 * 메시지 타입<br>
	 * <br>
	 * 0 - 서버에 접속<br>
	 * 001 - 클라이언트의 스트림을 서버에 등록<br>
	 * <br>
	 * 1 - 로그인<br>
	 * 101 - 플레이어 이름 중복 체크<br>
	 * 102 - 플레이어 이름 추가<br>
	 * <br>
	 * 2 - 온라인 매치 생성/접속<br>
	 * 201 - 매치 이름 중복 체크<br>
	 * 202 - 온라인 매치 생성<br>
	 * 203 - 온라인 매치 삭제<br>
	 * 211 - 온라인 매치 접속<br>
	 * 212 - 온라인 매치 정보 요청<br>
	 * 213 - 온라인 매치 나가기<br>
	 * 221 - 온라인 매치 상태 업데이트
	 * 
	 * @param message 전송된 메시지
	 * @return 반환할 오브젝트
	 */
	@SuppressWarnings("unchecked")
	private Object processMessage(Message message) {
		OnlineMatch match;
		
		switch (message.getMessageType()) {
		case 1:

		case 101:
			return Login.checkPlayerNameDuplicate((String) message.getContents());

		case 102:
			Login.addPlayerName(message.getPlayerName());
			break;

		case 201:
			return matchSystem.checkMatchNameDuplicate((String) message.getContents());

		case 202:
			matchSystem.createOnlineMatch(message.getPlayerName(), (ArrayList<Object>) message.getContents());
			break;

		case 203:
			matchSystem.deleteOnlineMatch((String) message.getContents());
			break;

		case 211:
			matchSystem.joinOnlineMatch(message.getPlayerName(), (ArrayList<Object>)message.getContents(), socket);
			match=matchSystem.getMatchbyPlayer(message.getPlayerName());
			match.update(211);
			break;

		case 212:
			match=matchSystem.getMatchbyPlayer(message.getPlayerName());
			(new UpdateSender(socket,212,match.getInfo())).start();
			break;

		case 213:
			break;

		case 221:
			// 온라인 매치 상태 업데이트 구현
			break;
		}

		return null;
	}

	@Override
	public void run() {
		Message message;
		try {
			while (in != null) {
				message = (Message) in.readObject();
				System.out.println(ServerTime.getTime() + " player name: " + message.getPlayerName()
						+ " - message type: " + message.getMessageType());
				System.out.println();

				Object returnObj = processMessage(message);

				out.writeObject(new Message(message.getMessageType(), "SERVER", returnObj));

				out.flush();
			}
		} catch (Exception e) {
		} finally {
			System.out.println(ServerTime.getTime() + " Disconnected from [" + socket.getInetAddress() + ":"
					+ socket.getPort() + "]");
		}
	}
}
