package server;

import java.util.HashMap;

import channel.Channel;
import channel.User;

public class Chat {
	private static Chat instance;
	private HashMap<String, User> userList = new HashMap<String, User>();
	private HashMap<String, Channel> channelList = new HashMap<String, Channel>();
	
	private Chat() {}
	
	public static Chat getInstance() {
		if (instance == null) {
			instance = new Chat();
		}
		return instance;
	}
	
	public HashMap<String, User> getUserList() {return userList;}
	public HashMap<String, Channel> getChannelList() {return channelList;}
	
	public void runCommand(String command) {
		
		String[] segCmd = command.split(" ");
		
		switch (segCmd[0]) {
		case "#msg":
			// segCmd[1] = Usuario
			// segCmd[2] = Mensaje
			User user = userList.get(segCmd[1]);
			user.getHandler();
			break;

		default:
			break;
		}
	}
}
