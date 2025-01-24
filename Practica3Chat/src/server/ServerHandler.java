package server;

import java.net.Socket;

public class ServerHandler implements Runnable{
	private final Socket sc;

	public ServerHandler(Socket sc) {
		super();
		this.sc = sc;
	}

	@Override
	public void run() {

	}
}
