package server;

import java.net.*;
import java.io.*;
import java.util.*;

import agents.MGHuman;

import util.Constant;
import game.Img;
import server.AmfServer;

public class SocketServer
{
	private ServerSocket server;

//	private int userDefineMemorySize = 0;
//
//	private int userDefinestrategySize = 0;
//
//	private int userDefineAgentNumber = 0;
//
	private Img imgGame;

	private BManager bMan = new BManager();// ��Ϣ�㲥��

	public SocketServer()
	{
	} // ���캯��

	public SocketServer(Img img)
	{
		this.imgGame = img;
	}

	public void startServer(int port) // ����������
	{
		try
		{
			server = new ServerSocket(port); // �����������׽���
			System.out.println("System Msgs: Port "+Constant.port+" is listening, waiting to play game.");
			while (true)
			{
				Socket socket = server.accept();// ���ͻ���������socket����
				//may be we should not start a thread here
				//this place will only receive the M,N,S,param
				new GameThread(socket).start();// �����߳�
				//bMan.add(socket);// ����׽���
				// AmfServer amfServer1 = new AmfServer(socket);
				// amfServer1.sentSerializationMeg();
			}
		} catch (Exception e)
		{
			// System.out.println(e);
			e.printStackTrace();
		}
	}


	class GameThread extends Thread // ��ͻ�������ͨ�ŵ��߳���
	{
		Socket socket; // �׽������ñ���

		private BufferedReader reader;// �����׽�����������

		private PrintWriter writer;// �����׽����������

		private AmfServer amfServer;

		GameThread(Socket socket)// ���캯��
		{
			this.socket = socket;
			this.amfServer = new AmfServer(socket);
		}

		public void run()
		{

			try
			{
				reader = new BufferedReader(new InputStreamReader(socket
						.getInputStream(), "utf8"));
				writer = new PrintWriter(socket.getOutputStream(), true);
				//String msg;
				// writer.println(imgGame.getPrice());
				// System.out.println("pice="+imgGame.getPrice());
				// ���������ȡ��Ϣ
				amfServer.receiveSerializationMeg();
				// while ((msg = reader.readLine()) != null) {
				// //
				// System.out.println(msg);
				// //imgGame.playGame();
				// }
			} catch (Exception e)
			{
				e.printStackTrace();

			} finally
			{
				try
				{// ����Ϣ�㲥����ɾ��socket
					bMan.remove(socket);
					if (reader != null)
						reader.close();
					if (writer != null)
						writer.close();
					if (socket != null)
						socket.close();
					reader = null;
					writer = null;
					socket = null;
					System.out.println("Message: socket disconnected at line 112 in socketserver.java with illegal mode.");
					// �����пͻ������͵�ǰ������
					// bMan.sendClientInfo();
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}

		}
	}
}

// ��Ϣ�㲥���࣬�̳�Vector��
class BManager extends Vector
{
	BManager()
	{
	}// ���캯��

	void add(Socket sock)
	{
		super.add(sock);// ����׽���
	}

	void add(MGHuman mgHuman)
	{
		super.add(mgHuman);// ����׽���
	}

	void remove(Socket sock)
	{
		super.remove(sock);// ɾ���׽���
	}

	void remove(MGHuman mgHuman)
	{
		super.remove(mgHuman);// ɾ���׽���
	}

	// �����пͻ���������Ϣ��ͬ����������
	synchronized void sendToAll(String msg)
	{
		PrintWriter writer = null; // �����
		Socket sock; // �׽���
		MGHuman mgHuman;
		for (int i = 0; i < size(); i++) // ִ��ѭ��
		{
			mgHuman = (MGHuman) elementAt(i);// ��ȡ��i���׽���
			sock = mgHuman.getSocket();
			try
			{ // ��ȡ��i���׽��������
				writer = new PrintWriter(sock.getOutputStream(), true);
			} catch (Exception ie)
			{
			}
			// ʹ�õ�i���׽���������������Ϣ
			if (writer != null)
				writer.println(msg);
		}
	}

	/**
	 * this function used to sent message to the client group by the init @param
	 */
	synchronized void sendToAll()
	{
		PrintWriter writer = null; // �����
		ThreadGroup group;

		Socket sock; // �׽���
		for (int i = 0; i < size(); i++) // ִ��ѭ��
		{
			sock = (Socket) elementAt(i);// ��ȡ��i���׽���
			try
			{ // ��ȡ��i���׽��������
				// writer = new PrintWriter(sock.getOutputStream(), true);
				AmfServer amfServer = new AmfServer(sock);
				amfServer.sentSerializationMeg();
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			// ʹ�õ�i���׽���������������Ϣ
			// if (writer != null)
			// {
			// //writer.println(object);
			// }
		}
	}

	// �����пͻ������͵�ǰ��������
	synchronized void sendClientInfo()
	{
		String info = "The number of clients��" + size();
		System.out.println(info);
		sendToAll(info);
	}
}
