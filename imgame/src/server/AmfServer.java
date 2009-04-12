package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import util.Constant;
import flex.messaging.io.SerializationContext;
import flex.messaging.io.amf.ASObject;
import flex.messaging.io.amf.Amf3Input;
import flex.messaging.io.amf.Amf3Output;
import game.Img;

public class AmfServer {
	private SerializationContext serializationContext = new SerializationContext();// ���л����������

	private Amf3Output amfout = new Amf3Output(serializationContext);// ��ʽ�������

	private Amf3Input amfin = new Amf3Input(serializationContext);// ��ʽ��������

	// ʵ����һ������������е����ݱ�д��һ�� byte ���顣
	private ByteArrayOutputStream byteoutStream = new ByteArrayOutputStream();// ����������������

	private ByteArrayInputStream byteArrayInputStream = null;// ����������������

	// ��byteoutStream���������������뵽DataOutputStream����
	private DataOutputStream dataoutstream = new DataOutputStream(byteoutStream);// ת����������Ϊ���������

	private DataInputStream dataInputStream = null;// ����������

	private InputStreamReader inputStreamReader;// ����������Ϣ���ַ���ת��Ϊ�ֽ���

	private BufferedReader bufferedReader;// ����������Ϣ���뻺��

	private OutputStreamWriter outputStreamWriter = null;// ʹ��amf3��ʽ��д�����е����ݱ�����ֽ�

	private BufferedWriter bufferedWriter;// ������װOutputStreamWriter�������Ч��

	private Object outputObject;

	private Socket socket;

	private Img iGame;
	
	private ArrayList<Double> hisPriceList = new ArrayList<Double>();

	public AmfServer(Socket socket) {
		this.socket = socket;
		this.Init();
		// this.outputObject = object;
	}

	public AmfServer() {
	}

	public void Init() {
		try {
			amfin.setInputStream(socket.getInputStream());
			amfout.setOutputStream(dataoutstream);

			inputStreamReader = new InputStreamReader(socket.getInputStream());
			bufferedReader = new BufferedReader(inputStreamReader);// �����е����ݷ��뻺��

			outputStreamWriter = new OutputStreamWriter(socket
					.getOutputStream());// ���ַ���ת��Ϊ�ֽ���
			bufferedWriter = new BufferedWriter(outputStreamWriter);// ��װosw����
			// ���д���Ч��
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ASObject ReceiveMsg() {
		ASObject object = null;
		try {
			// this line throw a exception
			if (!socket.isClosed()) {
				object = (ASObject) amfin.readObject();
				System.out.println("System Message: Get event call from client: " + object);
			}

		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException is "+e.getMessage());
			//e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOException is "+e.toString());
			try {
				socket.close();
				System.out.println("Message : Client disconnected in unexcept mode at line 99 file amfserver.java.");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			//e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Exception is "+e.getMessage());
			//e.printStackTrace();
		} 
		return object;
	}

	public synchronized void receiveSerializationMeg() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			// Init();
			// ASObject message = ReceiveMsg();
			if (true) {
				while (socket.isConnected()) {
					
					ASObject message = ReceiveMsg();
					if (message != null) {
						String event = (String) message.get("event");

						if (event != null) {
							if (event.equals("gameInit")) {
								//								 Constant.memorySize = Integer
								//								 .parseInt((String) message.get("m"));
								Constant.memorySize = (Integer) message
										.get("m");
								Constant.strategySize = (Integer) message
										.get("s");
								Constant.agentNumber = (Integer) message
										.get("n");
								iGame = new Img(Constant.memorySize,
										Constant.strategySize,
										Constant.agentNumber);
								// iGame.init();
								iGame.init(Constant.memorySize,
										Constant.strategySize,
										Constant.agentNumber);
								hisPriceList = iGame.getHistoryPrice(60);
								map.put("event", "startAction");
								map.put("historyPrice", hisPriceList);
								sentSerializationMeg(map);
							}
							if (event.equals("buy")) {
								iGame.playGame();
								map.put("event", "buyAction");
								map.put("price", iGame.getCurrentPrice());
								map.put("bestAgentScore", 100);
								map.put("avgAgentScore", 50);
								map.put("worseAgentScore", 10);
								map.put("bestHumanScore", 111);
								map.put("avGHumanScore", 222);
								map.put("worseHumanScore", 333);
								map.put("isEnd", "true");
								sentSerializationMeg(map);

							} else if (event.equals("sell")) {
								iGame.playGame();
								map.put("event", "sellAction");
								map.put("price", iGame.getCurrentPrice());
								map.put("bestAgentScore", 100);
								map.put("avgAgentScore", 50);
								map.put("worseAgentScore", 10);
								map.put("bestHumanScore", 111);
								map.put("avGHumanScore", 222);
								map.put("worseHumanScore", 333);
								map.put("isEnd", "true");
								sentSerializationMeg(map);

							} else if (event.equals("hold")) {
								iGame.playGame();
							} else if (event.equals("close")) {
								//close game and write database
								if(socket.isConnected())
								{
									socket.close();
									System.out.print("Message: Client disconnected in line 187 file amfserver.java place.");
								}
								break;
							} else {
								System.out.println(event);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
				System.out.print("client is closeed in line 193 of amfserver.java.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public synchronized void sentSerializationMeg(HashMap<String, Object> map) {
		// �������ı����ʽΪamf3
		amfout.setOutputStream(dataoutstream);

		try {
			amfout.writeObject(map);// ʵ�����ǽ�map����д�뵽dataoutstream����
			dataoutstream.flush();// ��ջ���
			map = null;
		} catch (IOException e) {
			e.printStackTrace();
		}

		// ��ByteArrayOutputStream����ת�����ֽ�����
		byte[] messageBytes = byteoutStream.toByteArray();// amf3����
		// messageBytes = null;

		// OutputStreamWriter outputStreamWriter;//ʹ��amf3��ʽ��д�����е����ݱ�����ֽ�
		// BufferedWriter bufferedWriter;//������װOutputStreamWriter�������Ч��

		try {

			System.out.println("The message length is: " + messageBytes.length);
			//System.out.println("The message is: " + messageBytes);

			// outputStreamWriter=new
			// OutputStreamWriter(socket.getOutputStream());//���ַ���ת��Ϊ�ֽ���
			// bufferedWriter=new
			//BufferedWriter(outputStreamWriter);//��װoutputStreamWriter�������д���Ч��

			socket.getOutputStream().write(messageBytes);// ������д�����������
			socket.getOutputStream().flush();
			byteoutStream.reset();
			//System.out.println("���鳤��" + byteoutStream.size());
			// socket.getOutputStream().close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// finally
		// {
		// try {
		// socket.close();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
	}

	public synchronized void sentSerializationMeg() {

		// �������ı����ʽΪamf3
		amfout.setOutputStream(dataoutstream);

		// ����Map����Double��������
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("event", "buyAction");
		map.put("playerName", "zhangliang");
		map.put("price", 77);
		map.put("bestAgentScore", 100);
		map.put("avgAgentScore", 50);
		map.put("worseAgentScore", 10);
		map.put("bestHumanScore", 111);
		map.put("avGHumanScore", 222);
		map.put("worseHumanScore", 333);
		map.put("isEnd", "true");

		try {
			amfout.writeObject(map);// ʵ�����ǽ�map����д�뵽dataoutstream����
			dataoutstream.flush();// ��ջ���
			map = null;
		} catch (IOException e) {
			e.printStackTrace();
		}

		// ��ByteArrayOutputStream����ת�����ֽ�����
		byte[] messageBytes = byteoutStream.toByteArray();// amf3����
		// messageBytes = null;

		// OutputStreamWriter outputStreamWriter;//ʹ��amf3��ʽ��д�����е����ݱ�����ֽ�
		// BufferedWriter bufferedWriter;//������װOutputStreamWriter�������Ч��

		try {

			System.out.println("������鳤��" + messageBytes.length);

			// outputStreamWriter=new
			// OutputStreamWriter(socket.getOutputStream());//���ַ���ת��Ϊ�ֽ���
			// bufferedWriter=new
			//BufferedWriter(outputStreamWriter);//��װoutputStreamWriter�������д���Ч��

			socket.getOutputStream().write(messageBytes);// ������д�����������
			socket.getOutputStream().flush();
			// socket.getOutputStream().close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// finally
		// {
		// try {
		// socket.close();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
	}
}