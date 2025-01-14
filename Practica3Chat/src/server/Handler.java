package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import channel.User;

public class Handler implements Runnable{
	private final Socket sc;
	private final Chat chat = Chat.getInstance();

	public Handler(Socket sc) {
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
			
			User user = new User(this, in.readUTF());
			
			while (true) {
				out.writeUTF("Comando: ");
				chat.runCommand(in.readUTF());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
