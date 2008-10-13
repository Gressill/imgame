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
        
        private BManager bMan=new BManager();//��Ϣ�㲥��
        
        public SocketServer(){}  //���캯��
        public SocketServer(Img img)
        {
        	this.imgGame = img;
        }
        public void startServer(int port)  //����������
        {
                try{
                        server=new ServerSocket(port); //�����������׽���
                        System.out.println("�������׽��ֽ������");
                        while(true)
                        {
                                Socket socket=server.accept();//���ͻ���������socket����
                                new GameThread(socket).start();//�����߳�
                                bMan.add(socket);//����׽���
                                //bMan.sendClientInfo();//ʹ���׽��������ǰ��������
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
        class GameThread extends Thread  //��ͻ�������ͨ�ŵ��߳���
        {
                Socket socket; //�׽������ñ���
                private BufferedReader reader;//�����׽�����������
                private PrintWriter writer;//�����׽����������
                GameThread(Socket socket)//���캯��
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
                                //���������ȡ��Ϣ
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
                                        //�����пͻ���������Ϣ
                                    //bMan.sendToAll(String.valueOf(imgGame.getCurrentPrice()));
                                }
                        }catch(Exception e)
                        {
                               
                        }finally
                        {
                                try {//����Ϣ�㲥����ɾ��socket
                                        bMan.remove(socket);
                                        if(reader !=null) reader.close();
                                        if(writer !=null) writer.close();
                                        if(socket !=null) socket.close();
                                        reader=null;
                                        writer=null;
                                        socket=null;
                                        System.out.println("�ͻ����뿪");
                                        //�����пͻ������͵�ǰ������
                                        bMan.sendClientInfo();
                                } catch (Exception e) {}
                        }
                       
                }
        }
}

//��Ϣ�㲥���࣬�̳�Vector��
class BManager extends Vector
{
        BManager (){}//���캯��
        void add(Socket sock)
        {
                super.add(sock);//����׽���
        }
        void remove(Socket sock)
        {
                super.remove(sock);//ɾ���׽���
        }
   //�����пͻ���������Ϣ��ͬ����������
        synchronized void sendToAll(String msg)
        {
                PrintWriter writer=null; //�����
                Socket sock;  //�׽���
                for(int i=0;i<size();i++)  //ִ��ѭ��
                {
                        sock=(Socket)elementAt(i);//��ȡ��i���׽���
                        try
                        {        //��ȡ��i���׽��������
                                writer=new PrintWriter(sock.getOutputStream(),true);
                        }catch(Exception ie){}
//ʹ�õ�i���׽���������������Ϣ
                        if(writer!=null) writer.println(msg);
                }
        }

        //�����пͻ������͵�ǰ��������
        synchronized void sendClientInfo()
        {
                String info="��ǰ����������"+size();
                System.out.println(info);
                sendToAll(info);
        }
}
