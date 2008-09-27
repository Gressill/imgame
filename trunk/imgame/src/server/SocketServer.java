package server;

import java.net.*;
import java.io.*;
import java.util.*;

import util.Constant;

public class SocketServer {
	
	private ServerSocket server;
	Vector sManager=new Vector();//�����׽��ֵ�Vector
	Random rnd=new Random();//����������ķ�����
	
	public SocketServer(){}
	public void startServer(int port)
	{
		try
		{
			server=new ServerSocket(port);
			System.out.println("�������׽����Ѵ����ɹ���");
			while(true)
			{
				Socket socket=server.accept();
				System.out.println("�Ѿ���ͻ�������");
				new KBBCom_Thread(socket).start();
				sManager.add(socket);
				System.out.println("��ǰ�ͻ�����������"+sManager.size());
			}
		}catch(Exception e){}
	}
	
	/**
	 * @param args
	 */
	
//	public static void main(String[] args) {
//		// TODO �Զ����ɷ������
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
						System.out.println("���Կͻ�����"+msg);

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
					System.out.println("�ͻ����뿪");
					System.out.println("��ǰ�ͻ�������������"+sManager.size());
				}catch(Exception e){}
			}
		}
	}

}

