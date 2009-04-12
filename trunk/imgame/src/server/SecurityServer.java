package server;

/*
 * 安全认证协议，防止sandbox问题。
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
	private PrintWriter writer;// 引用套接字输出流

	private final String corssDomain = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <cross-domain-policy xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"http://www.adobe.com/xml/schemas/PolicyFileSocket.xsd\"><allow-access-from domain=\"*\" to-ports=\"*\" /><site-control permitted-cross-domain-policies=\"master-only\" /></cross-domain-policy>";
	
	public SecurityServer() {
		// socket.
	}

	public void startServer() // 启动服务器
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
			ServerSocket server = new ServerSocket(843); // 创建服务器套接字
			System.out.println("Port 843 is listening, waiting to return the security policy file." + "\n");
			//String msg;
			while (true) {
				security_Socket = server.accept();// 若客户机提请求，socket连接
				reader = new BufferedReader(new InputStreamReader(security_Socket.getInputStream(), "utf8"));
				writer = new PrintWriter(security_Socket.getOutputStream(), true);

				char[] by = new char[22];
				reader.read(by, 0, 22);
				String head = new String(by);
				// 判断是不是第一求请求连接的安全验证，当客户端连socket时,as3会自动向服务端发送<policy-file-request
				// />这个字符串请求返回策略文件，所以当服务器收到这个串后给client返回就好了。
				// 如果是返回xML信息
				if (head.equals("<policy-file-request/>")) {
					System.out.println("连接服务器");
					writer.print(Constant.CORSS_DOMAIN + "\0");
					writer.flush();
					//reader.close();
					//writer.close();
					System.out.println(corssDomain);

				} else {
					// 自己的正常请求处理逻辑
					System.out.println("自己的正常请求处理逻辑");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
