package channel;

import java.util.ArrayList;

import server.Chat;

public class Channel {
	private String name;
	private ArrayList<User> users;
	
	
	public Channel(String name, ArrayList<User> users) {
		super();
		this.name = name;
		this.users = users;
		Chat.getInstance().getChannelList().put(name, this);
	}
	
	public String getName() {return name;}
	public ArrayList<User> getUsers() {return users;}
	
	public void setName(String name) {this.name = name;}
	public void setUsers(ArrayList<User> users) {this.users = users;}
	
	@Override
	public boolean equals(Object obj) {
		if (obj.getClass() != this.getClass()) {
			return false;
		}
		Channel channel = (Channel) obj;
		if (channel.getName() != this.getName()) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
}
