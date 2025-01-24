package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import chat.Chat;
import chat.User;

public class ServerHandler implements Runnable{
	private final Socket sc;
	private DataInputStream in;
	private DataOutputStream out;
	private User user;
	
	private Chat chat = Chat.getInstance();

	public ServerHandler(Socket sc) {
		this.sc = sc;
		try {
			in = new DataInputStream(sc.getInputStream());
			out = new DataOutputStream(sc.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public DataInputStream getIn() {return in;}
	public DataOutputStream getOut() {return out;}
	
	public void close() {
		try {
			in.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		user = null;
	}

	@Override
	public void run() {
		System.out.println("Cliente conectado");
		try {
			
			// User Creation
			out.writeUTF("Introduzca su nombre de usuario:");
			String userName = in.readUTF();
			// Comprobar que el usuario no existe
			while (chat.getUserList().containsKey(userName)) {
				if (chat.getUserList().containsKey(userName)) {
					out.writeUTF("Nombre no disponible");
					userName = in.readUTF();
				}
			}
			// Crear el usuario
			user = chat.createUser(userName, this);
			
			// Leer Input del usuario
			String msg;
			while (user != null) {
				user.sendMessage(user.toString());
				msg = in.readUTF();
				boolean exito = chat.runCommand(msg, user);
				if (!exito) {
					user.sendMessage("/!\\ Comando erroneo o desconocido /!\\");
					user.sendMessage("Para ver los comandos disponibles introcuzca #help o help");
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
