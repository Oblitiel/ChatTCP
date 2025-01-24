package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
	final static int PORT = 5000;
	final static int POOL_SIZE = 30;
	
	private static ServerSocket servidor = null;
	private static final ExecutorService pool = Executors.newFixedThreadPool(POOL_SIZE);
	
	public static void main(String[] args) {
		try {
			servidor = new ServerSocket(PORT);
			System.out.println("Servidor inicializado");
			
			while (true) {
				pool.execute(new ServerHandler(servidor.accept()));
			}
		} catch (IOException e) {
			pool.shutdown();
			e.printStackTrace();
		}
	}
}
