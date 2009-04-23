package game;

import server.DatabaseOperation;
import server.SecurityServer;
import server.SocketServer;
import util.Constant;

public class MainGame {

	public static void main(String[] args) {

		if (Constant.initGameFromXml()) {
			//Test database connection
			if (DatabaseOperation.testConnection()) {
				//Security Policy Thread, provide security certification service. 
				SecurityPolicySocket policySocket = new SecurityPolicySocket();
				Thread policyThread = new Thread(policySocket);
				policyThread.start();
				//Game Thread, provide minority game service.
				SocketServer server = new SocketServer();
				server.startServer(Constant.port);
			} else {
				System.out.println("System Msgs: Database connection failed.");
			}
		} else {
			System.out
					.println("System Msgs: Read config file failed, the game stopped.");
		}
	}

}

class SecurityPolicySocket implements Runnable {
	public SecurityPolicySocket() {
		// TODO Auto-generated constructor stub
	}

	// @Override
	public void run() {
		SecurityServer server = new SecurityServer();
		server.startServer();
	}
}
