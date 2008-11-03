package server;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import util.Constant;

import flex.messaging.io.SerializationContext;
import flex.messaging.io.amf.*;

public class AmfServer
{
	private SerializationContext serializationContext = new SerializationContext();//���л����������

	private Amf3Output amfout = new Amf3Output(serializationContext);//��ʽ�������

	private Amf3Input amfin = new Amf3Input(serializationContext);//��ʽ��������

	//ʵ����һ������������е����ݱ�д��һ�� byte ���顣
	private ByteArrayOutputStream byteoutStream = new ByteArrayOutputStream();//����������������

	private ByteArrayInputStream byteArrayInputStream = null;//����������������

	//��byteoutStream���������������뵽DataOutputStream����
	private DataOutputStream dataoutstream = new DataOutputStream(byteoutStream);//ת����������Ϊ���������

	private DataInputStream dataInputStream = null;//����������

	private InputStreamReader inputStreamReader;//����������Ϣ���ַ���ת��Ϊ�ֽ���

	private BufferedReader bufferedReader;//����������Ϣ���뻺��

	private OutputStreamWriter outputStreamWriter = null;//ʹ��amf3��ʽ��д�����е����ݱ�����ֽ�

	private BufferedWriter bufferedWriter;//������װOutputStreamWriter�������Ч��

	private Object outputObject;

	private Socket socket;

	public AmfServer(Socket socket)
	{
		this.socket = socket;
		this.Init();
		//this.outputObject = object;
	}

	public AmfServer()
	{
	}

	public void Init()
	{
		try
		{
			amfin.setInputStream(socket.getInputStream());
			amfout.setOutputStream(dataoutstream);

			inputStreamReader = new InputStreamReader(socket.getInputStream());
			bufferedReader = new BufferedReader(inputStreamReader);//�����е����ݷ��뻺��

			outputStreamWriter = new OutputStreamWriter(socket
					.getOutputStream());//���ַ���ת��Ϊ�ֽ���
			bufferedWriter = new BufferedWriter(outputStreamWriter);//��װosw�������д���Ч��
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public ASObject ReceiveMsg()
	{
		ASObject object = null;
			try
			{
				object = (ASObject) amfin.readObject();
				System.out.println();
				System.out.println("<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>");
				System.out.println(object);
				System.out.println("<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>");
				System.out.println();

			} catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			return object;
	}

	public void receiveSerializationMeg() {
		HashMap map;
		try {
			// Init();
			while (true) {
				ASObject message = ReceiveMsg();
				//System.out.println("message=" + message);

				if (message != null) {
					String event = (String) message.get("event");
					//System.out.println("message111=" + message);

					if (event != null) {
						if (event.equals("gameInit")) {
							Constant.memorySize = Integer.parseInt((String)message.get("m"));
							Constant.strategySize = Integer.parseInt((String)message.get("s"));
							Constant.agentNumber = Integer.parseInt((String)message.get("n"));
						}
						if (event.equals("buy")) {
							map = new HashMap();
							map.put("event", "buyAction");
							map.put("playerName", "zhangliang");
							map.put("best", 100);
							map.put("avg", 50);
							map.put("worse", 10);
							map.put("price", 2);

							amfout.writeObject(map);// ʵ�����ǽ�map����д�뵽dataoutstream����
							dataoutstream.flush();// ��ջ���

							map = null;

							byte[] messageBytes = byteoutStream.toByteArray();// amf3����
							socket.getOutputStream().write(messageBytes);// ������д�����������
							socket.getOutputStream().flush();
						} else if (event.equals("requestRoleInit"))
						{
							// if (message.get("requestMsg").equals("roleInit"))
							if (message.get("userAction").equals("sell")) {
								map = new HashMap();
								map.put("event", "buyAction");
								map.put("playerName", "zhangliang");
								map.put("best", 100);
								map.put("avg", 50);
								map.put("worse", 10);
								map.put("price", 2);

								amfout.writeObject(map);// ʵ�����ǽ�map����д�뵽
														// dataoutstream����
								dataoutstream.flush();// ��ջ���

								map = null;

								byte[] messageBytes = byteoutStream
										.toByteArray();// amf3����
								socket.getOutputStream().write(messageBytes);// ������д�����������
								socket.getOutputStream().flush();
							}
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {

				socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void sentSerializationMeg()
	{
		//	   SerializationContext serializationContext=new SerializationContext();
		//	  
		//	   //���л�amf3����
		//	   Amf3Output amfout=new Amf3Output(serializationContext);

		//ʵ����һ������������е����ݱ�д��һ�� byte ���顣
		//ByteArrayOutputStream byteoutStream=new ByteArrayOutputStream();

		//��byteoutStream���������������뵽DataOutputStream����
		//DataOutputStream dataoutstream=new DataOutputStream(byteoutStream);

		//����ServerSocket��Socket����
		//ServerSocket serverSocekt;

		// �������ı����ʽΪamf3
		amfout.setOutputStream(dataoutstream);

		//����Map����Double��������
		HashMap map = new HashMap();
		map.put("event", "buyAction");
		map.put("playerName", "zhangliang");
		map.put("best", 100);
		map.put("avg", 50);
		map.put("worse", 10);

		try
		{
			amfout.writeObject(map);//ʵ�����ǽ�map����д�뵽dataoutstream����
			dataoutstream.flush();//��ջ���
			map = null;
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		//��ByteArrayOutputStream����ת�����ֽ�����
		byte[] messageBytes = byteoutStream.toByteArray();//amf3����
		//messageBytes = null;

		//OutputStreamWriter outputStreamWriter;//ʹ��amf3��ʽ��д�����е����ݱ�����ֽ�
		//BufferedWriter bufferedWriter;//������װOutputStreamWriter�������Ч��

		try
		{

			System.out.println("������鳤��" + messageBytes.length);

			//outputStreamWriter=new OutputStreamWriter(socket.getOutputStream());//���ַ���ת��Ϊ�ֽ���
			//bufferedWriter=new BufferedWriter(outputStreamWriter);//��װoutputStreamWriter�������д���Ч��

			socket.getOutputStream().write(messageBytes);//������д�����������
			socket.getOutputStream().flush();
			//socket.getOutputStream().close();

		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		//	   finally
		//	   {
		//		   try {
		//			socket.close();
		//		} catch (IOException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}
		//	   }
	}
}
