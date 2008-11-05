package game;

import server.SocketServer;
import util.Constant;

public class MainGame {
	
	public static void main(String[] args) {
		//Img iGame = new Img();
		//iGame.init();
		SocketServer server = new SocketServer();
		server.startServer(Constant.port);
	}

}
