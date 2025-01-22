package chat;

import java.util.HashSet;

import server.ServerHandler;

public class Chat {
	private static Chat instance;
	private HashSet<User> userList = new HashSet<User>();
	private HashSet<Channel> channelList = new HashSet<Channel>();
	
	private Chat() {}
	
	public User getUser(String nick) {
		for (User user : userList) if (user.getNick().equals(nick)) return user;
		return null;
	}
	public Channel getChannel(String name) {
		for (Channel channel: channelList) if (channel.getName().equals(name)) return channel;
		return null;
	}
	public static Chat getInstance() {
		if (instance == null) instance = new Chat();
		return instance;
	}
	
	public HashSet<User> getUserList() {return userList;}
	public HashSet<Channel> getChannelList() {return channelList;}
	
	public User createUser(String nick, ServerHandler handler) {
		User newUser = new User(handler, nick);
		if (!userList.contains(newUser)) {
			userList.add(newUser);
			return newUser;
		} else {
			return null;
		}
	}
	
	public void runCommand(String command, User sender) {
		
		String[] segCmd = command.split(" ");
		// TODO segmentar bien los commandos
		
		switch (segCmd[0].toLowerCase()) {
//		TODO: Separar en distintas funciones y manejo de usuarios que no existen y falta de argumentos
		case "#msgbroadcast":
			for (User user : userList) {
				if (user != sender) {
					user.getHandler().sendMessage(String.format("From %s: %s", sender.getNick(), reformMessage(segCmd, 1)));
				}
			}
			break;
		case "#msg":
			getUser(segCmd[1]).getHandler().sendMessage(String.format("From %s: %s", sender.getNick(), reformMessage(segCmd, 2)));
			break;
		
		case "#msgcanal":
			for (User user : getChannel(segCmd[1]).getUsers()) {
				if (user != sender) {
					user.getHandler().sendMessage(String.format("From %s: %s", sender.getNick(), reformMessage(segCmd, 2)));
				}
			}
			break;
			
		case "#crearcanal":
			channelList.add(new Channel(segCmd[1]));
			break;
			
		case "#unir":
			getChannel(segCmd[1]).getUsers().add(sender);
			break;
		
		case "#salir":
			getChannel(segCmd[1]).getUsers().remove(sender);
			break;
		
		case "exit":
			// TODO
			break;

		default:
			break;
		}
	}
	
	private String reformMessage(String[] segCmd, int initialIndx) {
		String message = "";
		for (int i = initialIndx; i < segCmd.length; i++) {
			message += segCmd[i] + " ";
		}
		return message;
	}
}
