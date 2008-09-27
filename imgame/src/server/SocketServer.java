package server;

import java.net.*;
import java.io.*;
import java.util.*;

import util.Constant;

public class SocketServer {
	
	private ServerSocket server;
	Vector sManager=new Vector();//管理套接字的Vector
	Random rnd=new Random();//创建随机数的发生器
	
	public SocketServer(){}
	public void startServer(int port)
	{
		try
		{
			server=new ServerSocket(port);
			System.out.println("服务器套接字已创建成功！");
			while(true)
			{
				Socket socket=server.accept();
				System.out.println("已经与客户机连接");
				new KBBCom_Thread(socket).start();
				sManager.add(socket);
				System.out.println("当前客户机连结数："+sManager.size());
			}
		}catch(Exception e){}
	}
	
	/**
	 * @param args
	 */
	
//	public static void main(String[] args) {
//		// TODO 自动生成方法存根
//		SocketServer server=new SocketServer();
//		server.startServer(Constant.port);
//	}
//	
	class KBBCom_Thread extends Thread
	{
		Socket socket;
		private DataInputStream reader;
		private DataOutputStream writer;
		KBBCom_Thread(Socket socket)
		{
			this.socket=socket;
		}
		public void run()
		{
			try
			{
				reader=new DataInputStream(socket.getInputStream());
				writer=new DataOutputStream(socket.getOutputStream());
				String msg;
				while((msg=reader.readUTF())!=null)
				{
					
						writer.writeUTF(rnd.nextInt(3)+"\n");
						writer.flush();
						System.out.println("来自客户机："+msg);

				}
			}catch(Exception e){}finally
			{
				try
				{
					sManager.remove(socket);
					if(reader!=null)reader.close();
					if(writer!=null)writer.close();
					if(socket!=null)socket.close();
					reader=null;
					writer=null;
					socket=null;
					System.out.println("客户机离开");
					System.out.println("当前客户机的连接数："+sManager.size());
				}catch(Exception e){}
			}
		}
	}

}

