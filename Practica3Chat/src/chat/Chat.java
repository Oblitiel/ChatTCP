package chat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import server.ServerHandler;

// Patron Singleton
public class Chat {
	private static Chat instance;
	final static int FILE_SIZE = 1024;
	
	public static Chat getInstance() {
		if (instance == null) instance = new Chat();
		return instance;
	}
	
	private HashMap<String, User> userList = new HashMap<String, User>();
	private HashMap<String, Channel> channelList = new HashMap<String, Channel>();
	private String[] cmdList = {
			"#msg [usuario] [mensaje]",
			"#msgbroadcast [mensaje]",
			"#msgcanal [canal] [mensaje]",
			"#crear [canal]",
			"#unir [canal]",
			"#salir [canal]",
			"#sendfile [usuario] [ruta]",
			"#exit [canal]",
			"#help"
			}; 
	
	public HashMap<String, User> getUserList() {return userList;}
	public HashMap<String, Channel> getChannelList() {return channelList;}
	
	public User createUser(String name, ServerHandler handler) {
		User newUser = null;
		if (!userList.containsKey(name)) {
			newUser = new User(name, handler);
			userList.put(name, newUser);
			
			System.out.println("Usuario registrado: " + newUser.toString());
		}
		
		return newUser;
	}
	
	public Channel createChannel(String name, User user) {
		Channel newChnl = null;
		
		if (!channelList.containsKey(name)) {
			newChnl = new Channel(name);
			channelList.put(name, newChnl);
			newChnl.subscribe(user);
			
			System.out.println("Canal creado: " + newChnl.toString());
		}
		return newChnl;
	}
	
	public boolean runCommand(String command, User sender) {
		
		String[] splitCmd = command.split(" ");
		
		switch (splitCmd[0].toLowerCase()) {
		case "#msg":
			if (splitCmd.length < 3) return false;
			if (!userList.containsKey(sender.getName()))return false;
			
			userList.get(splitCmd[1]).sendMessage(reformMessage(splitCmd, 2), sender);
			return true;
		
		case "#msgbroadcast":
			if (splitCmd.length < 2) return false;
			
			for (User user : userList.values()) {
				if (!user.equals(sender)) {
					user.sendMessage(reformMessage(splitCmd, 1), sender);
				}
			}
			return true;
		
		case "#msgcanal":
			if (splitCmd.length < 3) return false;
			if (!channelList.get(splitCmd[1]).getUserList().contains(sender)) return false;
			
			channelList.get(splitCmd[1]).sendMessage(reformMessage(splitCmd, 2), sender);
			return true;
			
		case "#crear":
			if (splitCmd.length < 2) return false;
			if (channelList.containsKey(splitCmd[1])) return false;
			
			createChannel(splitCmd[1], sender);
			return true;
			
		case "#unir":
			if (splitCmd.length < 2) return false;
			if (!channelList.containsKey(splitCmd[1])) return false;
			
			channelList.get(splitCmd[1]).subscribe(sender);
			return true;
			
		case "#salir":
			if (splitCmd.length < 2) return false;
			if (!channelList.get(splitCmd[1]).getUserList().contains(sender)) return false;
			
			channelList.get(splitCmd[1]).unSubscribe(sender);
			return true;
		
		case "#exit":
			sender.getHandler().close();
			return true;
		
		// Help es adicion mia por si es la que se prueba en clase ;)
		case "#help","help":
			String helpText = "Comandos disponibles: \n";
			for (int i = 0; i < cmdList.length; i++) {
				helpText += cmdList[i] + "\n";
			}
			sender.sendMessage(helpText);
		return true;
		
		case "#sendfile":
			if (splitCmd.length < 3) return false;
			if (userList.containsKey(splitCmd[1]));
			
			File file = new File(splitCmd[2]);
			User receiver = userList.get(splitCmd[1]);

			if (file.length() > FILE_SIZE) {
				sender.sendMessage(String.format("El archivo no pude pasar los %d bytes", FILE_SIZE));
				return false;
			}
			
			receiver.sendMessage("Reciviendo Archivo");
			
			byte[] fileBytes = new byte[FILE_SIZE];
			FileInputStream fileIn;
			
			try {
				
				fileIn = new FileInputStream(file);
				fileIn.read(fileBytes, 0, FILE_SIZE);
				
				receiver.sendMessage(file.getName());
				receiver.getHandler().getOut().write(fileBytes, 0, FILE_SIZE);
				
				fileIn.close();
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			return true;
			
		default:
			return false;
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
