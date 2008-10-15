package game;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import server.SocketServer;
import util.Constant;
import game.Game;
import agents.Agent;
import agents.MGAgent;
import agents.MGHuman;

public class Img {

	Game game;

	Agent[] agents;

	private int[] initCurrentChoise;
	
	private int currentPrice;
	
	//double[] price = new double[Constant.timeStepNumber + 1];
	List<Integer> price = new ArrayList<Integer>();
	
	public Img()
	{
		// TODO Auto-generated constructor stub
	}
	
	public Img(int m,int s,int a)
	{

		Constant.memorySize = m;
		Constant.strategySize = s;
		Constant.agentNumber = a;
	}
	
	public int getCurrentPrice()
	{
		return this.currentPrice;
	}
	
	public List getPrice() {
		return price;
	}

	public void init() {

		try {

			//create sockets
		    //SocketServer mgServer=new SocketServer();
		    //mgServer.startServer(Constant.port);
		    
			//�û�û��m,s,n��ʱ����Ĭ������3��2��95
//			Constant.memorySize = Integer
//					.parseInt((this.getParameter("memory") == null) ? String
//							.valueOf(Constant.memorySize) : this
//							.getParameter("memory"));			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		// TODO change the agentNumber to configured value
		agents = new Agent[Constant.agentNumber + 1]; // number of
		// computer-controlled
		// agents PLUS human

		//��ʼ��agent
		for (int i = 0; i < agents.length; i++) {
			agents[i] = new MGAgent(Constant.memorySize, Constant.strategySize);
		}
		
		// game plays once to initialize
		//game = new Game(agents, 0, 200 * (1 << 3));
		game = new Game(agents, 0, Constant.agentNumber+1);//third pram will be the number of agents
		
		initCurrentChoise = game.getCurrentChoise();

		//price.set(0,0);
		//
		for (int i = 0; i < price.size() - 1; ++i) {
			int tempPrice = price.get(i) + initCurrentChoise[(initCurrentChoise.length - price.size() - 1) + i];
			price.set(i+1, tempPrice);
			//System.out.println("price["+i+"]"+price[i]);
			//System.out.println((initCurrentChoise.length - price.length - 1) + i);
		}
		
//		price = null;
//		initCurrentChoise = null;
//
//		agents[agents.length - 1] = new MGHuman(agents);
//
//		for (int i = 0; i < agents.length; ++i) {
//			//agents[i].setGain(0);
//		}
//
//		game = new Game(agents, 0, 100);

	}
	
	public void playGame()
	{
		price = loadHistory(price);
		game.playGame();
		this.currentPrice = game.getCurrentPrice();		
	}
	
	/**
	 * loading history price,while price is empty,load history
	 * @param price: price list 
	 * @return priceList: price list
	 */
	private List loadHistory(List price) {
		
		//check file exist
		// File historyFile = new File(Constant.HISTORY_PRICE_FILE);
		// if (historyFile.canRead())
		List priceList = price;
		
		if (priceList.isEmpty())
		{
			long lasting = System.currentTimeMillis();
			try {
					File historyFile = new File(Constant.HISTORY_PRICE_FILE);
					SAXReader reader = new SAXReader();
					Document doc = reader.read(historyFile);
					Element root = doc.getRootElement();
					//get price
					//List nodes = root.elements("price");
					//System.out.println("size = "+nodes.size());
					if(priceList.size()>50)
					{
						priceList = root.elements("price").subList((priceList.size()-50), priceList.size());
					}

					for (Iterator it = priceList.iterator(); it.hasNext();) {
					   Element priceElm = (Element) it.next();
					   System.out.println("priceElm.getData()"+priceElm.getData());
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					}
				//System.out.println("����ʱ�䣺" + (System.currentTimeMillis() - lasting) + " ����");
		}
		return priceList;
	}

	public void destroy() {
		initCurrentChoise = null;

		agents = null;
		game = null;

	}

	public String getAppletInfo() {
		return "Title: Interactive Minority Game \nAuthor: <f>";
	}

	


//	public static void main(String[] args) {
//
//		Img ImgTest = new Img();
//	
//		ImgTest.init();
//	}
}