package server.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import server.message.Message;
import server.process.Login;
import server.process.OnlineMatch;

/**
 * 
 * Ŭ���̾�Ʈ ����� ������
 * 
 * @author YJH
 * @version 1.0
 *
 */
public class ServerReceiver extends Thread {
	/**
	 * ����
	 */
	Socket socket;

	/**
	 * Ŭ���̾�Ʈ �޽��� ���ſ� ��Ʈ��
	 */
	ObjectInputStream in;

	/**
	 * ������Ʈ ��ȯ�� ��Ʈ��
	 */
	ObjectOutputStream out;

	/**
	 * @param socket ����
	 */
	public ServerReceiver(Socket socket) {
		super();
		this.socket = socket;
		try {
			in = new ObjectInputStream(socket.getInputStream());
			out = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
		}
	}
	
	/**
	 * �޽��� ó��<br>
	 * <br>
	 * �޽��� Ÿ��<br>
	 * <br>
	 * 0 - ������ ����<br>
	 *   001 - Ŭ���̾�Ʈ�� ��Ʈ���� ������ ���<br>
	 * 1 - �α���<br>
	 *   101 - �÷��̾� �̸� �ߺ� üũ<br>
	 *   102 - �÷��̾� �̸� �߰�<br>
	 * <br>
	 * 2 - �¶��� ��ġ ����/����<br>
	 *   201 - ��ġ �̸� �ߺ� üũ<br>
	 *   202 - �¶��� ��ġ ����<br>
	 *   203 - �¶��� ��ġ ����<br>
	 *   211 - �¶��� ��ġ ����<br>
	 *   221 - �¶��� ��ġ ���� ������Ʈ
	 * 
	 * @param message ���۵� �޽���
	 * @return ��ȯ�� �޽���
	 */
	public static Object processMessage(Message message) {
		
		switch (message.getMessageType()) {
		case 1:
			
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
			
		case 211:
			break;
			
		case 221:
			
		}
		
		return null;
	}

	@Override
	public void run() {
		Message message;
		try {
			while (in != null) {
				message = (Message) in.readObject();
				System.out.println(
						"player name: " + message.getPlayerName() + " - message type: " + message.getMessageType());
				System.out.println();

				Object returnObj = processMessage(message);
				out.writeObject(returnObj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			System.out.println("Disconnected from ["+socket.getInetAddress()+":"+socket.getPort()+"]");
		}
	}
}

