package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import chat.Chat;
import chat.User;

public class ServerHandler implements Runnable{
	private final Socket sc;
	private final Chat chat = Chat.getInstance();
	User user = null;

	public ServerHandler(Socket sc) {
		super();
		this.sc = sc;
	}

	@Override
	public void run() {
		try (
				DataInputStream in = new DataInputStream(sc.getInputStream());
				DataOutputStream out = new DataOutputStream(sc.getOutputStream());
			){
			
			System.out.println("Cliente conectado");
			// Registro de usuario
			String userName = "";
			do {
				out.writeUTF("Introduzca un nombre de usuario: ");
				userName = in.readUTF();
				if (chat.getUser(userName) != null) {
					out.writeUTF("Nombre de usuario no disponible");
				}
				
			} while (chat.getUser(userName) != null);
			
			out.writeUTF("Usuario Registrado con exito");
			user = chat.createUser(userName, this);
			System.out.println(chat.getUserList());
			
			while (true) {
				String line = in.readUTF();
				System.out.println(user.getNick() + ": " + line);
				chat.runCommand(line, user);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendMessage(String message) {
		try {
			DataOutputStream out = new DataOutputStream(sc.getOutputStream());
			
			out.writeUTF(message);
			out.writeUTF(user.toString());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
