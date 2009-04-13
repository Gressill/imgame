package game;

import server.DatabaseOperation;
import server.SecurityServer;
import server.SocketServer;
import util.Constant;

public class MainGame {

	public static void main(String[] args) {

		//Security Policy Thead, provide security certification service. 
		SecurityPolicySocket policySocket = new SecurityPolicySocket();
		Thread policyThread = new Thread(policySocket);
		policyThread.start();
		
		//Game Thead, provide minority game service.
		SocketServer server = new SocketServer();
		server.startServer(Constant.port);
		
		//DatabaseOperation.testSql();
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
