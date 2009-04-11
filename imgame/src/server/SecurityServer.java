package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import server.SocketServer.GameThread;

public class SecurityServer {
	private Socket socket;
	private BufferedReader reader;
	private PrintWriter writer;// �����׽��������

	private final String corssDomain = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <cross-domain-policy xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"xsi:noNamespaceSchemaLocation=\"http://www.adobe.com/xml/schemas/PolicyFileSocket.xsd\"><allow-access-from domain=\"*\" to-ports=\"*\" /><site-control permitted-cross-domain-policies=\"master-only\" /></cross-domain-policy>";
	
	private final String cd = "<?xml version=\"1.0\"?><!DOCTYPE cross-domain-policy SYSTEM \"/xml/dtds/cross-domain-policy.dtd\"><cross-domain-policy><site-control permitted-cross-domain-policies=\"master-only\" /><allow-access-from domain=\"*\" to-ports=\"*\" /></cross-domain-policy>";
	public SecurityServer() {
		// socket.
	}

	public void startServer() // ����������
	{
		try {
			ServerSocket server = new ServerSocket(843); // �����������׽���
			System.out.println("server socket 843 listening..." + "\0"
					+ "dasdasd");
			while (true) {
				Socket socket = server.accept();// ���ͻ���������socket����
				// new GameThread(socket).start();// �����߳�
				reader = new BufferedReader(new InputStreamReader(socket
						.getInputStream(), "utf8"));
				writer = new PrintWriter(socket.getOutputStream(), true);
				String msg;
				while ((msg = reader.readLine()) != null) {
					System.out.println(msg);
					writer.println(cd);
					writer.write(cd);
					System.out.println(cd);
					//writer.flush();
				}
				// ���������ȡ��Ϣ
				//<policy-file-request/>
				if ((msg = reader.readLine()) == "<policy-file-request/>\0") {
					//
					System.out.println(msg);
					writer.println(corssDomain);
					writer.write(corssDomain);
					//writer.flush();
					// imgGame.playGame();
				}

				// System.out.println("pice="+imgGame.getCurrentPrice());
			}
		} catch (Exception e) {
			// System.out.println(e);
			e.printStackTrace();
		}
	}
}
