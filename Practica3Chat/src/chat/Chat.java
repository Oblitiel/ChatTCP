package chat;

import java.util.ArrayList;
import java.util.HashMap;

import server.ServerHandler;

// Patron Singleton
public class Chat {
	private static Chat instance;
	
	public static Chat getInstance() {
		if (instance == null) instance = new Chat();
		return instance;
	}
	
	private HashMap<String, User> userList = new HashMap<String, User>();
	private HashMap<String, Channel> channelList = new HashMap<String, Channel>();
	private String[] cmdList = {
			"msg",
			"msgbroadcast",
			"msgcanal",
			"crearcanal",
			"unirse",
			"salir",
			"exit"
			}; 
	
	public HashMap<String, User> getUserList() {return userList;}
	public HashMap<String, Channel> getChannelList() {return channelList;}
	
	public User createUser(String name, ServerHandler handler) {
		User newUser = new User(name, handler);
		userList.put(name, newUser);
		return newUser;
	}
	
	public Channel createChannel(String name, User user) {
		Channel newChnl = new Channel(name);
		channelList.put(name, newChnl);
		newChnl.subscribe(user);
		return newChnl;
	}
	
	public boolean runCommand(String command, User sender) {
	}
}
