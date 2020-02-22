package process;

import message.Message;

/*
 * < Message Type >
 * 
 * 1 - Login
 * 	10 - player name duplicate check
 * 
 */

public class Process {

	public static Object processMessage(Message message) {
		
		switch (message.getMessageType()) {
		case 10:
			return Login.checkPlayerNameDuplicate((String)message.getContents());
		}
		
		return null;
	}
	
}
