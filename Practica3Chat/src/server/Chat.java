package server;

import java.util.HashSet;

import channel.Channel;
import channel.User;

public class Chat {
	private static Chat instance;
	private HashSet<User> userList;
	private HashSet<Channel> channels;
	
	private Chat() {}
	
	public static Chat getInstance() {
		if (instance == null) {
			instance = new Chat();
		}
		return instance;
	}
	
	public HashSet<User> getUserList() {return userList;}
	
	public void runCommand(String command) {
		
		String[] segCmd = command.split(" ");
		
		switch (segCmd[0]) {
		case "#msg":
				userList.
			break;

		default:
			break;
		}
	}
}
