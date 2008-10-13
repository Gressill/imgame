package server;

import java.net.*;
import java.io.*;
import java.util.*;

import util.Constant;
import game.Img;

public class SocketServer {

        private ServerSocket server;
        private int userDefineMemorySize = 0;
        private int userDefinestrategySize = 0;
        private int userDefineAgentNumber = 0;
        private Img imgGame;
        
        private BManager bMan=new BManager();//消息广播者
        
        public SocketServer(){}  //构造函数
        public SocketServer(Img img)
        {
        	this.imgGame = img;
        }
        public void startServer(int port)  //启动服务器
        {
                try{
                        server=new ServerSocket(port); //创建服务器套接字
                        System.out.println("服务器套接字建立完毕");
                        while(true)
                        {
                                Socket socket=server.accept();//若客户机提请求，socket连接
                                new GameThread(socket).start();//启动线程
                                bMan.add(socket);//添加套接字
                                //bMan.sendClientInfo();//使用套接字输出当前连接人数
                        }
                }catch(Exception e){
                        System.out.println(e);
                }
        }
        public static void main(String[] args) {
        	Img ImgTest = new Img();
        	ImgTest.init();
        	int i=1;
        	while (i<10)
			{
        		ImgTest.playGame();
				i++;
			}
            //SocketServer server=new SocketServer(ImgTest);
            //server.startServer(Constant.port);
        }
        class GameThread extends Thread  //与客户机进行通信的线程累
        {
                Socket socket; //套接字引用变量
                private BufferedReader reader;//引用套接字输入流；
                private PrintWriter writer;//引用套接字输出流；
                GameThread(Socket socket)//构造函数
                {
                        this.socket=socket;
                }
                public void run()
                {
                       
                        try
                        {
                                reader=new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf8"));
                                writer=new PrintWriter(socket.getOutputStream(),true);
                                String msg;
                                //writer.println(imgGame.getPrice());
                                //System.out.println("pice="+imgGame.getPrice());
                                //从输出流获取信息
                                while((msg=reader.readLine())!=null)
                                {
                                	//
                                	imgGame.playGame();
                                	if (msg.equals("buy"))
									{
										//
                                		Constant.userChoise = Constant.buyChoise;
									}
                                	if (msg.equals("sell"))
									{
										//
                                		Constant.userChoise = Constant.sellChoise;
									}
                                	if (msg.equals("hold"))
									{
										//
                                		Constant.userChoise = Constant.holdChoise;
									}
                                	else 
                                	{
                                		//
									}
                                	//Constant.keepPlaying = true; 
                                	System.out.println(msg);
                                	//System.out.println("pice="+imgGame.getPrice());
                                        //向所有客户机传送信息
                                    //bMan.sendToAll(String.valueOf(imgGame.getCurrentPrice()));
                                }
                        }catch(Exception e)
                        {
                               
                        }finally
                        {
                                try {//从消息广播者中删除socket
                                        bMan.remove(socket);
                                        if(reader !=null) reader.close();
                                        if(writer !=null) writer.close();
                                        if(socket !=null) socket.close();
                                        reader=null;
                                        writer=null;
                                        socket=null;
                                        System.out.println("客户机离开");
                                        //向所有客户机传送当前连接数
                                        bMan.sendClientInfo();
                                } catch (Exception e) {}
                        }
                       
                }
        }
}

//消息广播者类，继承Vector类
class BManager extends Vector
{
        BManager (){}//构造函数
        void add(Socket sock)
        {
                super.add(sock);//添加套接字
        }
        void remove(Socket sock)
        {
                super.remove(sock);//删除套接字
        }
   //向所有客户机传送消息，同步化方法。
        synchronized void sendToAll(String msg)
        {
                PrintWriter writer=null; //输出流
                Socket sock;  //套接字
                for(int i=0;i<size();i++)  //执行循环
                {
                        sock=(Socket)elementAt(i);//获取第i个套接字
                        try
                        {        //获取第i个套接字输出流
                                writer=new PrintWriter(sock.getOutputStream(),true);
                        }catch(Exception ie){}
//使用第i各套接字输出流，输出消息
                        if(writer!=null) writer.println(msg);
                }
        }

        //向所有客户机发送当前连接人数
        synchronized void sendClientInfo()
        {
                String info="当前连接人数："+size();
                System.out.println(info);
                sendToAll(info);
        }
}
