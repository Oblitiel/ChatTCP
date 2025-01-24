package chat;

import java.util.HashSet;

public class Channel {
	private final String name;
	private HashSet<User> userList = new HashSet<User>();
	
	public String getName() {return name;}
	
	
	Channel(String name) {
		this.name = name;
	}
	
	// Patron Observer
	public void subscribe(User user) {
		userList.add(user);
		user.getChannelList().add(this);
	}
	
	public void unSubscribe(User user) {
		userList.remove(user);
		user.getChannelList().remove(this);
	}
	
	public void mensaje(String msg, User sender) {
		//TODO: Enviar mensaje a todos los usuarios
	}
}
