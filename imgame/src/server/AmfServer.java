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
	private SerializationContext serializationContext = new SerializationContext();//序列化输入输出流

	private Amf3Output amfout = new Amf3Output(serializationContext);//格式化输出流

	private Amf3Input amfin = new Amf3Input(serializationContext);//格式化输入流

	//实现了一个输出流，其中的数据被写入一个 byte 数组。
	private ByteArrayOutputStream byteoutStream = new ByteArrayOutputStream();//创建二进制输入流

	private ByteArrayInputStream byteArrayInputStream = null;//创建二进制输入流

	//将byteoutStream产生的数组流导入到DataOutputStream流中
	private DataOutputStream dataoutstream = new DataOutputStream(byteoutStream);//转化二进制流为数据输出流

	private DataInputStream dataInputStream = null;//创建输入流

	private InputStreamReader inputStreamReader;//将输入流信息由字符型转换为字节型

	private BufferedReader bufferedReader;//将输入流信息放入缓存

	private OutputStreamWriter outputStreamWriter = null;//使用amf3格式将写入流中的数据编码成字节

	private BufferedWriter bufferedWriter;//用来封装OutputStreamWriter，以提高效率

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
			bufferedReader = new BufferedReader(inputStreamReader);//将流中的数据放入缓存

			outputStreamWriter = new OutputStreamWriter(socket
					.getOutputStream());//将字符流转化为字节流
			bufferedWriter = new BufferedWriter(outputStreamWriter);//封装osw对象，提高写入的效率
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

							amfout.writeObject(map);// 实际上是将map对象写入到dataoutstream流中
							dataoutstream.flush();// 清空缓存

							map = null;

							byte[] messageBytes = byteoutStream.toByteArray();// amf3数据
							socket.getOutputStream().write(messageBytes);// 向流中写入二进制数据
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

								amfout.writeObject(map);// 实际上是将map对象写入到
														// dataoutstream流中
								dataoutstream.flush();// 清空缓存

								map = null;

								byte[] messageBytes = byteoutStream
										.toByteArray();// amf3数据
								socket.getOutputStream().write(messageBytes);// 向流中写入二进制数据
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
		//	   //序列化amf3对象
		//	   Amf3Output amfout=new Amf3Output(serializationContext);

		//实现了一个输出流，其中的数据被写入一个 byte 数组。
		//ByteArrayOutputStream byteoutStream=new ByteArrayOutputStream();

		//将byteoutStream产生的数组流导入到DataOutputStream流中
		//DataOutputStream dataoutstream=new DataOutputStream(byteoutStream);

		//创建ServerSocket和Socket对象
		//ServerSocket serverSocekt;

		// 设置流的编码格式为amf3
		amfout.setOutputStream(dataoutstream);

		//创建Map对象、Double对象数组
		HashMap map = new HashMap();
		map.put("event", "buyAction");
		map.put("playerName", "zhangliang");
		map.put("best", 100);
		map.put("avg", 50);
		map.put("worse", 10);

		try
		{
			amfout.writeObject(map);//实际上是将map对象写入到dataoutstream流中
			dataoutstream.flush();//清空缓存
			map = null;
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		//将ByteArrayOutputStream流中转化成字节数组
		byte[] messageBytes = byteoutStream.toByteArray();//amf3数据
		//messageBytes = null;

		//OutputStreamWriter outputStreamWriter;//使用amf3格式将写入流中的数据编码成字节
		//BufferedWriter bufferedWriter;//用来封装OutputStreamWriter，以提高效率

		try
		{

			System.out.println("输出数组长度" + messageBytes.length);

			//outputStreamWriter=new OutputStreamWriter(socket.getOutputStream());//将字符流转化为字节流
			//bufferedWriter=new BufferedWriter(outputStreamWriter);//封装outputStreamWriter对象，提高写入的效率

			socket.getOutputStream().write(messageBytes);//向流中写入二进制数据
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
