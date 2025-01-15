package cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClienteReciver {
	final static String HOST = "127.0.0.1";
	final static int PORT = 5000;
	
	public static void main(String[] args) {
		try (
				Socket socket = new Socket(HOST, PORT);
				Scanner sc = new Scanner(System.in);
				DataInputStream in = new DataInputStream(socket.getInputStream());
				DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			){
			
			out.writeUTF(sc.nextLine());
			
			while (true) {
				System.out.println(in.readUTF());
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
