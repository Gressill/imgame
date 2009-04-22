package server;

/*
 * ��ȫ��֤Э�飬��ֹsandbox���⡣
 * 
 * 
 * 
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import util.Constant;

import com.sun.org.apache.xpath.internal.axes.ReverseAxesWalker;

public class SecurityServer {
	private Socket security_Socket;
	private BufferedReader reader;
	private PrintWriter writer;// �����׽��������

	private final String corssDomain = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <cross-domain-policy xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"http://www.adobe.com/xml/schemas/PolicyFileSocket.xsd\"><allow-access-from domain=\"*\" to-ports=\"*\" /><site-control permitted-cross-domain-policies=\"master-only\" /></cross-domain-policy>";
	
	public SecurityServer() {
		// socket.
	}

	public void startServer() // ����������
	{

		String ip = "";
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		
//		String xml = "<cross-domain-policy><site-control permitted-cross-domain-policies=\"all\"/>";
//		xml = xml + "<allow-access-from domain=\"" + ip
//				+ "\" to-ports=\"1234\" />";
//		xml = xml + "</cross-domain-policy>";
		try {
			ServerSocket server = new ServerSocket(843); // �����������׽���
			System.out.println("System Msgs: Port 843 is listening, waiting to return the security policy file." + "\n");
			//String msg;
			while (true) {
				security_Socket = server.accept();// ���ͻ���������socket����
				reader = new BufferedReader(new InputStreamReader(security_Socket.getInputStream(), "utf8"));
				writer = new PrintWriter(security_Socket.getOutputStream(), true);

				char[] by = new char[22];
				reader.read(by, 0, 22);
				String head = new String(by);
				// �ж��ǲ��ǵ�һ���������ӵİ�ȫ��֤�����ͻ�����socketʱ,as3���Զ������˷���<policy-file-request
				// />����ַ������󷵻ز����ļ������Ե��������յ���������client���ؾͺ��ˡ�
				// ����Ƿ���xML��Ϣ
				if (head.equals("<policy-file-request/>")) {
					System.out.println("���ӷ�����");
					writer.print(Constant.CORSS_DOMAIN + "\0");
					writer.flush();
					//reader.close();
					//writer.close();
					System.out.println(corssDomain);

				} else {
					// �Լ��������������߼�
					System.out.println("�Լ��������������߼�");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
