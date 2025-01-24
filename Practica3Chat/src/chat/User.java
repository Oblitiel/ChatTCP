package chat;

import java.util.HashSet;

import server.ServerHandler;

public class User {
	private final String name;
	private ServerHandler handler;
	private HashSet<Channel> channelList = new HashSet<Channel>();
	
	public String getName() {return name;}
	public HashSet<Channel> getChannelList() {return channelList;}
	public ServerHandler getHandler() {return handler;}
	
	User(String name, ServerHandler handler) {
		this.name = name;
		this.handler = handler;
	}
}
