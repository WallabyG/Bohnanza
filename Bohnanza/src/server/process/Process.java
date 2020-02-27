package server.process;

import server.message.Message;

/*
 * < Message Type >
 * 
 * 1 - Login
 * 	101 - player name duplication check
 *  102 - add player name
 * 
 * 2 - Create/Join online match
 *   201 - match name duplication check
 *   202 - create online match
 *   203 - join online match
 * 
 */

public class Process {

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
