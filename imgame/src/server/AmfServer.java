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
	  
	   //���л�amf3����
	   Amf3Output amfout=new Amf3Output(serializationContext);
	  
	   //ʵ����һ������������е����ݱ�д��һ�� byte ���顣
	   ByteArrayOutputStream byteoutStream=new ByteArrayOutputStream();
	  
	   //��byteoutStream���������������뵽DataOutputStream����
	   DataOutputStream dataoutstream=new DataOutputStream(byteoutStream);
	  
	   //����ServerSocket��Socket����
	   //ServerSocket serverSocekt;
	  
	   // �������ı����ʽΪamf3
	   amfout.setOutputStream(dataoutstream);
	  
	   //����Map����Double��������
	   HashMap map=new HashMap();
	   map.put("Event", "PRICE");
	   map.put("Best", 100);
	   map.put("Avg", 50);
	   map.put("Worse", 10);  
	  
	   try {
	    amfout.writeObject(map);//ʵ�����ǽ�map����д�뵽dataoutstream����
	    dataoutstream.flush();//��ջ���
	   } catch (IOException e) {
	    e.printStackTrace();
	   }
	  
	   //��ByteArrayOutputStream����ת�����ֽ�����
	   byte[] messageBytes=byteoutStream.toByteArray();//amf3����
	  
	   OutputStreamWriter osw;//ʹ��amf3��ʽ��д�����е����ݱ�����ֽ�
	   BufferedWriter bwrite;//������װOutputStreamWriter�������Ч��
	  
	   try {
	   
	    System.out.println("������鳤��"+messageBytes.length);   
//	    serverSocekt=new ServerSocket(8888);//��������������   
//	    System.out.println("�������Ѿ�������������������");   
//	    socket=serverSocekt.accept();   
//	    if(socket.isConnected())
//	    {
//	     System.out.println(">>>>>>>>>>�ͻ���������");
//	    }
	    //socket.
	    osw=new OutputStreamWriter(socket.getOutputStream());//���ַ���ת��Ϊ�ֽ���
	    bwrite=new BufferedWriter(osw);//��װosw�������д���Ч��
	   
	    socket.getOutputStream().write(messageBytes);//������д�����������
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
