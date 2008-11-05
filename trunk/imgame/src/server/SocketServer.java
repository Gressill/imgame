package server;

import java.net.*;
import java.io.*;
import java.util.*;

import util.Constant;
import game.Img;
import server.AmfServer;

public class SocketServer
{
	private ServerSocket server;
	private int userDefineMemorySize = 0;
	private int userDefinestrategySize = 0;
	private int userDefineAgentNumber = 0;
	private Img imgGame;

	private BManager bMan = new BManager();//��Ϣ�㲥��

	public SocketServer() {
	} //���캯��

	public SocketServer(Img img) {
		this.imgGame = img;
	}

	public void startServer(int port) //����������
	{
		try {
			server = new ServerSocket(port); //�����������׽���
			System.out.println("server socket created...");
			while (true) {
				Socket socket = server.accept();//���ͻ���������socket����
				new GameThread(socket).start();//�����߳�
				bMan.add(socket);//����׽���
				//AmfServer amfServer1 = new AmfServer(socket);
				//amfServer1.sentSerializationMeg();
				//bMan.sendClientInfo();//ʹ���׽��������ǰ��������
				//bMan.sendToAll();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

//	public static void main(String[] args) {
//		Img iGame = new Img();
//		iGame.init();
//		///////////////////////////
////		        	try
////					{
////		        		ServerSocket server1=new ServerSocket(8888);
////		            	while(true)
////		            	{
////		            		System.out.println("socket created....!");
////		            		Socket socket=server1.accept();
////		                    AmfServer amfServer = new AmfServer(socket);
////		                	amfServer.sentSerializationMeg();
////		                	System.out.println("connect....!");
////		            	}
////					} catch (Exception e)
////					{
////						e.printStackTrace();
////						// TODO: handle exception
////					}
//		///////////////////////////
//		SocketServer server = new SocketServer(iGame);
//		server.startServer(Constant.port);
//	}

	class GameThread extends Thread //��ͻ�������ͨ�ŵ��߳���
	{
		Socket socket; //�׽������ñ���
		private BufferedReader reader;//�����׽�����������
		private PrintWriter writer;//�����׽����������
		private AmfServer amfServer;

		GameThread(Socket socket)//���캯��
		{
			this.socket = socket;
			this.amfServer= new AmfServer(socket);
		}

		public void run() {

			try {
				reader = new BufferedReader(new InputStreamReader(socket
						.getInputStream(), "utf8"));
				writer = new PrintWriter(socket.getOutputStream(), true);
				String msg;
				//writer.println(imgGame.getPrice());
				//System.out.println("pice="+imgGame.getPrice());
				//���������ȡ��Ϣ
				amfServer.receiveSerializationMeg();
//				while ((msg = reader.readLine()) != null) {
//					//
//					System.out.println(msg);
//					//imgGame.playGame();
//					if (msg.equals("buy")) {
//						Constant.userChoise = Constant.buyChoise;
//					}
//					if (msg.equals("sell")) {
//						Constant.userChoise = Constant.sellChoise;
//					}
//					if (msg.equals("hold")) {
//						Constant.userChoise = Constant.holdChoise;
//					} else {
//						//
//					}
//					
//					amfServer.sentSerializationMeg();
//					//System.out.println("pice="+imgGame.getCurrentPrice());
//					//�����пͻ���������Ϣ
//					//imgGame.writePriceFile(1212);
//					//bMan.sendToAll(String.valueOf(imgGame.getCurrentPrice()));
//					//bMan.sendToAll(imgGame.getPrice());
//					//bMan.sendToAll();
//				}
			} catch (Exception e) {
				e.printStackTrace();

			} finally {
				try {//����Ϣ�㲥����ɾ��socket
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
					System.out.println("clients disconnect..");
					//�����пͻ������͵�ǰ������
					//bMan.sendClientInfo();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
	}
}

//��Ϣ�㲥���࣬�̳�Vector��
class BManager extends Vector {
	BManager() {
	}//���캯��


	void add(Socket sock) {
		super.add(sock);//����׽���
	}

	void remove(Socket sock) {
		super.remove(sock);//ɾ���׽���
	}

	//�����пͻ���������Ϣ��ͬ����������
	synchronized void sendToAll(String msg) {
		PrintWriter writer = null; //�����
		Socket sock; //�׽���
		for (int i = 0; i < size(); i++) //ִ��ѭ��
		{
			sock = (Socket) elementAt(i);//��ȡ��i���׽���
			try { //��ȡ��i���׽��������
				writer = new PrintWriter(sock.getOutputStream(), true);
			} catch (Exception ie) {
			}
			//ʹ�õ�i���׽���������������Ϣ
			if (writer != null)
				writer.println(msg);
		}
	}

	synchronized void sendToAll() {
		PrintWriter writer = null; //�����
		Socket sock; //�׽���
		for (int i = 0; i < size(); i++) //ִ��ѭ��
		{
			sock = (Socket) elementAt(i);//��ȡ��i���׽���
			try { //��ȡ��i���׽��������
				//writer = new PrintWriter(sock.getOutputStream(), true);
				AmfServer amfServer = new AmfServer(sock);
				amfServer.sentSerializationMeg();
			} catch (Exception ie) {
			}
			//ʹ�õ�i���׽���������������Ϣ
			if (writer != null)
			{
				//writer.println(object);
			}
		}
	}

	//�����пͻ������͵�ǰ��������
	synchronized void sendClientInfo() {
		String info = "The number of clients��" + size();
		System.out.println(info);
		sendToAll(info);
	}
}
