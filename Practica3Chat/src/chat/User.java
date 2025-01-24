package chat;

import java.util.HashSet;

public class User {
	private final String name;
	private HashSet<Channel> channelList = new HashSet<Channel>();
	
	public String getName() {return name;}
	public HashSet<Channel> getChannelList() {return channelList;}
	
	public User(String name) {
		this.name = name;
	}
}
