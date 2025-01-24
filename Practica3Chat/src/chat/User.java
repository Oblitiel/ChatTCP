package chat;

import java.util.ArrayList;

import server.ServerHandler;

public class User {
	//Prueba
	private String nick;
	private ServerHandler handler;
	private ArrayList<Channel> channelsSub = new ArrayList<>();
	
	User(ServerHandler handler, String nick) {
		this.nick = nick;
		this.handler = handler;
	}

	public String getNick() {return nick;}
	public ArrayList<Channel> getChanelsSub() {return channelsSub;}
	public ServerHandler getHandler() {return handler;}

	public void setNick(String nick) {this.nick = nick;}
	public void setChanelsSub(ArrayList<Channel> chanelsSub) {this.channelsSub = chanelsSub;}
	public void setHandler(ServerHandler handler) {this.handler = handler;}

	@Override
	public boolean equals(Object obj) {
		if (obj.getClass() != this.getClass()) {
			return false;
		}
		User user = (User) obj;
		if (user.getNick().toLowerCase() != this.getNick().toLowerCase()) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return String.format("Nick: %s - Canales: %s", nick, channelsSub.toString());
	}
	
	
}
