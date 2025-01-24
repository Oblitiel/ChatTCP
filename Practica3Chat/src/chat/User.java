package chat;

import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;

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
	
	public void sendMessage(String msg) {
		try {
			handler.getOut().writeUTF(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendMessage(String msg, User sender) {
		try {
			if (sender != null) {
				msg = String.format("De %s : %s", sender.getName(), msg);
			}
			handler.getOut().writeUTF(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString() {
		String canales = "";
		
		for (Channel channel : channelList) {
			canales += channel.getName() +  " ";
		}
		return String.format("Nombre: %s - Canales: %s", name, canales);
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
		User other = (User) obj;
		return Objects.equals(name, other.name);
	}
	
}
