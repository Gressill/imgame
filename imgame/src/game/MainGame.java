package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

//import com.sun.xml.internal.ws.wsdl.writer.document.Port;

import server.DatabaseOperation;
import server.SecurityServer;
import server.SocketServer;
import util.Constant;

public class MainGame {

	public static void main(String[] args) {

		runTwoSocket rSocket = new runTwoSocket();
		Thread myThread = new Thread(rSocket);
		myThread.start();

		// SocketServer server = new SocketServer();
		// server.startServer(Constant.port);
		// server.startServer(843);
		// String sqlStr = "INSERT INTO mgtest VALUES ('23ee','34','89')";
		// DatabaseOperation databaseOperation = new DatabaseOperation();
		// databaseOperation.sqlTest(sqlStr);
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
