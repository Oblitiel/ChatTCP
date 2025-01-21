package cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
	final static String HOST = "127.0.0.1";
	final static int PORT = 5000;
	
	private String nick;
	
	public static void main(String[] args) {
		try {
			Socket socket = new Socket(HOST, PORT);
			
			new Thread(new Sender(socket)).start();;
			new Thread(new Receiver(socket)).start();
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}

class Sender implements Runnable{
	Socket socket;
	
	public Sender(Socket socket) {
		super();
		this.socket = socket;
	}

	@Override
	public void run() {
		try (
				Scanner sc = new Scanner(System.in);
			){
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			while (true) {
				out.writeUTF(sc.nextLine());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}

class Receiver implements Runnable{
Socket socket;
	
	public Receiver(Socket socket) {
		super();
		this.socket = socket;
	}
	
	@Override
	public void run() {
		try {
			DataInputStream in = new DataInputStream(socket.getInputStream());
			while (true) {
				String message = in.readUTF();
                System.out.println(message);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}