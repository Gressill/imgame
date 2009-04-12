package server;

/*
 * ��ȫ��֤Э�飬��ֹsandbox���⡣
 * 
 * 
 * 
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

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
		try {
			ServerSocket server = new ServerSocket(843); // �����������׽���
			System.out.println("Port 843 is listening, waiting to return the security policy file." + "\n");
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
					writer.print(corssDomain + "\0");
					writer.flush();
					reader.close();
					writer.close();
				} else {
					// �Լ��������������߼�
					System.out.println("�Լ��������������߼�");
				}
				//if((msg = reader.readLine()) == "<policy-file-request/>\n"){
//				System.out.println("Get message: " + reader.readLine());
//				//No matter send what, we just return the security policy file. 
//				writer.print(corssDomain);
//				System.out.println("Send message: "+ corssDomain);
//				writer.write(corssDomain);
//				writer.flush();
				//}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
