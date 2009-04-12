package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import com.sun.xml.internal.ws.wsdl.writer.document.Port;

import server.DatabaseOperation;
import server.SecurityServer;
import server.SocketServer;
import util.Constant;

public class MainGame {

	public static void main(String[] args) {
		// Img iGame = new Img();
		// iGame.init();

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
	public void run1() {
		SecurityServer server = new SecurityServer();
		server.startServer();
	}

	public void run() {
		ServerSocket ss;
		String ip = "";
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		String xml = "<cross-domain-policy><site-control permitted-cross-domain-policies=\"all\"/>";
		xml = xml + "<allow-access-from domain=\"" + ip
				+ "\" to-ports=\"1234\" />";
		xml = xml + "</cross-domain-policy>";

		try {
			ss = new ServerSocket(843);
			while (true) {
				Socket s = ss.accept();

				BufferedReader br = new BufferedReader(new InputStreamReader(s
						.getInputStream(), "UTF-8"));
				PrintWriter pw = new PrintWriter(s.getOutputStream());
				char[] by = new char[22];
				br.read(by, 0, 22);
				String head = new String(by);
				// �ж��ǲ��ǵ�һ���������ӵİ�ȫ��֤�����ͻ�����socketʱ,as3���Զ������˷���<policy-file-request
				// />����ַ������󷵻ز����ļ������Ե��������յ���������client���ؾͺ��ˡ�
				// ����Ƿ���xML��Ϣ
				if (head.equals("<policy-file-request/>")) {
					System.out.println("���ӷ�����");
					pw.print(xml + "\0");
					pw.flush();
					br.close();
					pw.close();
				} else {
					// ���Լ��������������߼�
					System.out.println("�Լ��������������߼�");
				}

			}
			//ss.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
