package chat;

import java.util.HashSet;
import java.util.Objects;

public class Channel {
	private final String name;
	private HashSet<User> userList = new HashSet<User>();
	
	public String getName() {return name;}
	public HashSet<User> getUserList() {return userList;}
	
	Channel(String name) {
		this.name = name;
	}
	
	// Patron Observer
	public void subscribe(User user) {
		userList.add(user);
		user.getChannelList().add(this);
		
		sendMessage(String.format("%s Se unio al canal %s :)", user.getName(), this.getName()));
	}
	
	public void unSubscribe(User user) {
		userList.remove(user);
		user.getChannelList().remove(this);
		
		sendMessage(String.format("%s Salio del canal %s :(", user.getName(), this.getName()));
	}
	
	public void sendMessage(String msg) {
		for (User user : userList) {
			user.sendMessage(msg);
		}
	}
	
	public void sendMessage(String msg, User sender) {
		msg = String.format("De %s Desde %s : %s", sender.getName(), this.getName(), msg);

		for (User user : userList) {
			if (!(user.equals(sender))) {
				user.sendMessage(msg);
			}
		}
	}


	@Override
	public String toString() {
		String usuarios = "";
		
		for (User user : userList) {
			usuarios += user.getName() +  " ";
		}
		
		return String.format("Nombre: %s - Usuarios: %s", name, usuarios);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Channel other = (Channel) obj;
		return Objects.equals(name, other.name);
	}
}
