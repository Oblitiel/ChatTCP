package channel;

import java.util.ArrayList;

import server.Chat;
import server.Handler;

public class User {
	private String nick;
	private Handler handler;
	private ArrayList<Channel> channelsSub = new ArrayList<>();
	
	public User(Handler handler, String nick) {
		this.nick = nick;
		this.handler = handler;
		Chat.getInstance().getUserList().add(this);
	}

	public String getNick() {return nick;}
	public ArrayList<Channel> getChanelsSub() {return channelsSub;}
	public Handler getHandler() {return handler;}

	public void setNick(String nick) {this.nick = nick;}
	public void setChanelsSub(ArrayList<Channel> chanelsSub) {this.channelsSub = chanelsSub;}
	public void setHandler(Handler handler) {this.handler = handler;}

	@Override
	public boolean equals(Object obj) {
		if (obj.getClass() != this.getClass()) {
			return false;
		}
		User user = (User) obj;
		if (user.getNick() != this.getNick()) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return String.format("Nick: %s - Canales: %s", nick, channelsSub.toString());
	}
	
	
}
