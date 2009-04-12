package server;

/*
 * 安全认证协议，防止sandbox问题。
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
	private PrintWriter writer;// 引用套接字输出流

	private final String corssDomain = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <cross-domain-policy xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"http://www.adobe.com/xml/schemas/PolicyFileSocket.xsd\"><allow-access-from domain=\"*\" to-ports=\"*\" /><site-control permitted-cross-domain-policies=\"master-only\" /></cross-domain-policy>";
	
	public SecurityServer() {
		// socket.
	}

	public void startServer() // 启动服务器
	{
		try {
			ServerSocket server = new ServerSocket(843); // 创建服务器套接字
			System.out.println("Port 843 is listening, waiting to return the security policy file." + "\n");
			//String msg;
			while (true) {
				security_Socket = server.accept();// 若客户机提请求，socket连接
				reader = new BufferedReader(new InputStreamReader(security_Socket.getInputStream(), "utf8"));
				writer = new PrintWriter(security_Socket.getOutputStream(), true);
				//if((msg = reader.readLine()) == "<policy-file-request/>\n"){
				System.out.println("Get message: " + reader.readLine());
				//No matter send what, we just return the security policy file. 
				writer.print(corssDomain);
				System.out.println("Send message: "+ corssDomain);
				writer.write(corssDomain);
				writer.flush();
				//}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
