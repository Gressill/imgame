package game;

import server.SecurityServer;

public class MainGame {

	public static void main(String[] args) {

		runTwoSocket rSocket = new runTwoSocket();
		Thread myThread = new Thread(rSocket);
		myThread.start();
		

//		SocketServer server = new SocketServer();
//		server.startServer(Constant.port);
//		server.startServer(843);
	}

}

class runTwoSocket implements Runnable {
	public runTwoSocket() {
		// TODO Auto-generated constructor stub
	}

	// @Override
	public void run() {
		SecurityServer server = new SecurityServer();
		server.startServer();

	}
}
