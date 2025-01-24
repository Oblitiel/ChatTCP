package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import chat.Chat;
import chat.User;

public class ServerHandler implements Runnable{
	private final Socket sc;
	private User user;
	
	private Chat chat = Chat.getInstance();

	public ServerHandler(Socket sc) {
		this.sc = sc;
	}

	@Override
	public void run() {
		try (
				DataInputStream in = new DataInputStream(sc.getInputStream());
				DataOutputStream out = new DataOutputStream(sc.getOutputStream());
			){
			
			// User Creation
			sendMessage("Introduzca su nombre de usuario:");
			String userName = in.readUTF();
			// Comprobar que el usuario no existe
			while (chat.getUserList().containsKey(userName)) {
				if (chat.getUserList().containsKey(userName)) {
					sendMessage("Nombre no disponible");
					userName = in.readUTF();
				}
			}
			// Crear el usuario
			user = chat.createUser(userName, this);
			
			// Leer Input del usuario
			while (true) {
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendMessage(String msg) {
		try {
			DataOutputStream out = new DataOutputStream(sc.getOutputStream());
			
			out.writeUTF(msg);
			if (user != null) {
				out.writeUTF(user.toString());
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
