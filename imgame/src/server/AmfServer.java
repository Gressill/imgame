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

import flex.messaging.io.SerializationContext;
import flex.messaging.io.amf.Amf3Input;
import flex.messaging.io.amf.Amf3Output;
import flex.messaging.io.amf.*;

public class AmfServer
{

	Object outputObject;
	private Socket socket;
	public AmfServer(Socket socket)
	{
		this.socket = socket;
		//this.outputObject = object;
	}
	public AmfServer(){}
	public void sentSerializationMeg()
	{
	   SerializationContext serializationContext=new SerializationContext();
	  
	   //序列化amf3对象
	   Amf3Output amfout=new Amf3Output(serializationContext);
	  
	   //实现了一个输出流，其中的数据被写入一个 byte 数组。
	   ByteArrayOutputStream byteoutStream=new ByteArrayOutputStream();
	  
	   //将byteoutStream产生的数组流导入到DataOutputStream流中
	   DataOutputStream dataoutstream=new DataOutputStream(byteoutStream);
	  
	   //创建ServerSocket和Socket对象
	   //ServerSocket serverSocekt;
	  
	   // 设置流的编码格式为amf3
	   amfout.setOutputStream(dataoutstream);
	  
	   //创建Map对象、Double对象数组
	   HashMap map=new HashMap();
	   map.put("Event", "PRICE");
	   map.put("Best", 100);
	   map.put("Avg", 50);
	   map.put("Worse", 10);  
	  
	   try {
	    amfout.writeObject(map);//实际上是将map对象写入到dataoutstream流中
	    dataoutstream.flush();//清空缓存
	   } catch (IOException e) {
	    e.printStackTrace();
	   }
	  
	   //将ByteArrayOutputStream流中转化成字节数组
	   byte[] messageBytes=byteoutStream.toByteArray();//amf3数据
	  
	   OutputStreamWriter osw;//使用amf3格式将写入流中的数据编码成字节
	   BufferedWriter bwrite;//用来封装OutputStreamWriter，以提高效率
	  
	   try {
	   
	    System.out.println("输出数组长度"+messageBytes.length);   
//	    serverSocekt=new ServerSocket(8888);//开启服务器进程   
//	    System.out.println("服务器已经启动。。。。。。。");   
//	    socket=serverSocekt.accept();   
//	    if(socket.isConnected())
//	    {
//	     System.out.println(">>>>>>>>>>客户端已连接");
//	    }
	    //socket.
	    osw=new OutputStreamWriter(socket.getOutputStream());//将字符流转化为字节流
	    bwrite=new BufferedWriter(osw);//封装osw对象，提高写入的效率
	   
	    socket.getOutputStream().write(messageBytes);//向流中写入二进制数据
	    socket.getOutputStream().flush();
	    //socket.getOutputStream().close();
	   
	   } catch (FileNotFoundException e) {
	    e.printStackTrace();
	   }
	   catch (IOException e) {
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
