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
import java.util.HashMap;

import util.Constant;
import flex.messaging.io.SerializationContext;
import flex.messaging.io.amf.ASObject;
import flex.messaging.io.amf.Amf3Input;
import flex.messaging.io.amf.Amf3Output;
import game.Img;

public class AmfServer {
	private SerializationContext serializationContext = new SerializationContext();// 序列化输入输出流

	private Amf3Output amfout = new Amf3Output(serializationContext);// 格式化输出流

	private Amf3Input amfin = new Amf3Input(serializationContext);// 格式化输入流

	// 实现了一个输出流，其中的数据被写入一个 byte 数组。
	private ByteArrayOutputStream byteoutStream = new ByteArrayOutputStream();// 创建二进制输入流

	private ByteArrayInputStream byteArrayInputStream = null;// 创建二进制输入流

	// 将byteoutStream产生的数组流导入到DataOutputStream流中
	private DataOutputStream dataoutstream = new DataOutputStream(byteoutStream);// 转化二进制流为数据输出流

	private DataInputStream dataInputStream = null;// 创建输入流

	private InputStreamReader inputStreamReader;// 将输入流信息由字符型转换为字节型

	private BufferedReader bufferedReader;// 将输入流信息放入缓存

	private OutputStreamWriter outputStreamWriter = null;// 使用amf3格式将写入流中的数据编码成字节

	private BufferedWriter bufferedWriter;// 用来封装OutputStreamWriter，以提高效率

	private Object outputObject;

	private Socket socket;

	private Img iGame;

	private Img[] gameListImgs;

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
			bufferedReader = new BufferedReader(inputStreamReader);// 将流中的数据放入缓存

			outputStreamWriter = new OutputStreamWriter(socket
					.getOutputStream());// 将字符流转化为字节流
			bufferedWriter = new BufferedWriter(outputStreamWriter);// 封装osw对象，
			// 提高写入的效率
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
				System.out.println(object);
			}

		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException is "+e.getMessage());
			//e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOException is "+e.toString());
			try {
				socket.close();
				System.out.println("Client disconnect by unexcept close.......");
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
					// System.out.println(socket.isConnected());
					ASObject message = ReceiveMsg();
					if (message != null) {
						String event = (String) message.get("event");
						// System.out.println("message111=" + message);
						// now we need we identify the differentes between
						// players
						// same player put in the same group

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
							}
							if (event.equals("buy")) {
								iGame.playGame();
								// gameListImgs[1].playGame();
								int tempPrice = iGame.getCurrentPrice();
								map.put("event", "buyAction");
								map.put("price", tempPrice);
								map.put("bestAgentScore", 100);
								map.put("avgAgentScore", 50);
								map.put("worseAgentScore", 10);
								map.put("bestHumanScore", 111);
								map.put("avGHumanScore", 222);
								map.put("worseHumanScore", 333);
								sentSerializationMeg(map);

							} else if (event.equals("sell")) {
								iGame.playGame();
								int tempPrice = iGame.getCurrentPrice();
								map.put("event", "sellAction");
								map.put("bestAgentScore", 100);
								map.put("avgAgentScore", 50);
								map.put("worseAgentScore", 10);
								map.put("bestHumanScore", 111);
								map.put("avGHumanScore", 222);
								map.put("worseHumanScore", 333);
								sentSerializationMeg(map);

							} else if (event.equals("hold")) {
								iGame.playGame();
							} else if (event.equals("close")) {
								//close game and write database
								socket.close();
								break;
							} else {
								System.out.println(event);
								// break;
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
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public synchronized void sentSerializationMeg(HashMap<String, Object> map) {
		// 设置流的编码格式为amf3
		amfout.setOutputStream(dataoutstream);

		try {
			amfout.writeObject(map);// 实际上是将map对象写入到dataoutstream流中
			dataoutstream.flush();// 清空缓存
			map = null;
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 将ByteArrayOutputStream流中转化成字节数组
		byte[] messageBytes = byteoutStream.toByteArray();// amf3数据
		// messageBytes = null;

		// OutputStreamWriter outputStreamWriter;//使用amf3格式将写入流中的数据编码成字节
		// BufferedWriter bufferedWriter;//用来封装OutputStreamWriter，以提高效率

		try {

			System.out.println("输出数组长度" + messageBytes.length);

			// outputStreamWriter=new
			// OutputStreamWriter(socket.getOutputStream());//将字符流转化为字节流
			// bufferedWriter=new
			//BufferedWriter(outputStreamWriter);//封装outputStreamWriter对象，提高写入的效率

			socket.getOutputStream().write(messageBytes);// 向流中写入二进制数据
			socket.getOutputStream().flush();
			byteoutStream.reset();
			//System.out.println("数组长度" + byteoutStream.size());
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

		// 设置流的编码格式为amf3
		amfout.setOutputStream(dataoutstream);

		// 创建Map对象、Double对象数组
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("event", "buyAction");
		map.put("playerName", "zhangliang");
		map.put("price", 7777);
		map.put("bestAgentScore", 100);
		map.put("avgAgentScore", 50);
		map.put("worseAgentScore", 10);
		map.put("bestHumanScore", 111);
		map.put("avGHumanScore", 222);
		map.put("worseHumanScore", 333);

		try {
			amfout.writeObject(map);// 实际上是将map对象写入到dataoutstream流中
			dataoutstream.flush();// 清空缓存
			map = null;
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 将ByteArrayOutputStream流中转化成字节数组
		byte[] messageBytes = byteoutStream.toByteArray();// amf3数据
		// messageBytes = null;

		// OutputStreamWriter outputStreamWriter;//使用amf3格式将写入流中的数据编码成字节
		// BufferedWriter bufferedWriter;//用来封装OutputStreamWriter，以提高效率

		try {

			System.out.println("输出数组长度" + messageBytes.length);

			// outputStreamWriter=new
			// OutputStreamWriter(socket.getOutputStream());//将字符流转化为字节流
			// bufferedWriter=new
			//BufferedWriter(outputStreamWriter);//封装outputStreamWriter对象，提高写入的效率

			socket.getOutputStream().write(messageBytes);// 向流中写入二进制数据
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
