package game;

import server.DatabaseOperation;
import server.SecurityServer;
import server.SocketServer;
import util.Constant;

public class MainGame {

	public static void main(String[] args) {

		//cross domain
		runTwoSocket rSocket = new runTwoSocket();
		Thread myThread = new Thread(rSocket);
		myThread.start();
		//begin game
		SocketServer server = new SocketServer();
		server.startServer(Constant.port);
		
		//DatabaseOperation.testSql();
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
