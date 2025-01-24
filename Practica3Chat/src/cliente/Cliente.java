package cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
	final static String HOST = "127.0.0.1";
	final static int PORT = 5000;
	final static int FILE_SIZE = 1024;
	
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
				DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			){
			String message = "";
			while (!message.equals("#exit")) {
				message = sc.nextLine();
				out.writeUTF(message);
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
		try (
				DataInputStream in = new DataInputStream(socket.getInputStream());			
			){
			String message = "";
			while (!message.equals("Cerrando...")) {
				message = in.readUTF();
                System.out.println(message);
                
                // Recivir Archivos
                if (message.equals("Reciviendo Archivo")) {
                	
                	String home = System.getProperty("user.home");
                	String fileName = in.readUTF();
                	
                	byte[] fileBytes = new byte[Cliente.FILE_SIZE];
                	FileOutputStream fileOut = new FileOutputStream(new File(home + "/Downloads/" + fileName));
                	
                	in.read(fileBytes, 0, Cliente.FILE_SIZE);
                	fileOut.write(fileBytes, 0, Cliente.FILE_SIZE);
                	
                	fileOut.close();
                }
			}
		} catch (IOException e) {
			System.out.println("Conexion cerrada");
		}
	}
}