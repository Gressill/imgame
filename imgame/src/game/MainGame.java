package game;

import server.DatabaseOperation;
import server.SecurityServer;
import server.SocketServer;
import util.Constant;

public class MainGame {

	public static void main(String[] args) {
		//Img iGame = new Img();
		//iGame.init();
		
		runTwoSocket rSocket = new runTwoSocket();
		Thread myThread = new Thread(rSocket);
		myThread.start();

		//SocketServer server = new SocketServer();
		//server.startServer(Constant.port);
		//server.startServer(843);
		//		String sqlStr = "INSERT INTO mgtest VALUES ('23ee','34','89')";
		//		DatabaseOperation databaseOperation = new DatabaseOperation();
		//		databaseOperation.sqlTest(sqlStr);
	}

}


class runTwoSocket implements Runnable {
	public runTwoSocket() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		SecurityServer server = new SecurityServer();
		server.startServer();
	}

}
