package game;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.FileWriter;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.XMLWriter;

import com.sun.org.apache.xalan.internal.xsltc.compiler.Parser;

import server.SocketServer;
import util.Constant;
import game.Game;
import agents.Agent;
import agents.MGAgent;
import agents.MGHuman;

public class Img
{

	Game game;

	Agent[] agents;

	MGHuman[] mghuman;
	
	//ArrayList<ArrayList<MGHuman>> mgList;// = new ArrayList<ArrayList<MGHuman>>;

	private int[] initCurrentChoise;

	private int currentPrice;

	// double[] price = new double[Constant.timeStepNumber + 1];
	List<Integer> price = new ArrayList<Integer>();
	
	//this is a list to store human agent
	//List<ArrayList<MGHuman>> mgHumanList = new ArrayList<ArrayList<MGHuman>>();
	private GameList gameList = GameList.getInstance();

	public Img()
	{
		// TODO Auto-generated constructor stub
	}

	public Img(int m, int s, int n)
	{

		//Constant.memorySize = m;
		//Constant.strategySize = s;
		//Constant.agentNumber = n;
		gameList.addHumenAgent(new MGHuman(m,s,n));
//
//		if (gameList.size() == 0)
//		{
//			gameList.addHumenAgent(new MGHuman(m,s,n));
//			//mghuman[Constant.differetnHumNum] = new MGHuman(m, s, n);
//		} else
//		{
//			for (int i = 0,j=mghuman.length; i < j; i++)
//			{
//				if (mghuman[i].getMemoryNum() == m && mghuman[i].getStrategyNum() == s && mghuman[i].getAgentNum() == n)
//				{
//					//add this human agent to the same group
//					break;
//				}
//				//different m,n,s,begin a new game. game number +1
//				if(i==j)
//					Constant.differetnHumNum++;
//			}
//		}
	}

	public int getCurrentPrice()
	{
		return this.currentPrice;
	}

	public List getPrice()
	{
		return price;
	}
	
	public void init() {
		
	}

	public void init(int m,int s,int n)
	{

		try
		{
			// 用户没填m,s,n的时候用默认数据3，2，95
			// Constant.memorySize = Integer
			// .parseInt((this.getParameter("memory") == null) ? String
			// .valueOf(Constant.memorySize) : this
			// .getParameter("memory"));
//			if (mgHumanList.exist(msn))) {
//				//add to the exsit game
//			} else {
//				//begin a new game
//			}
		} catch (Exception e)
		{

			e.printStackTrace();
		}

		// TODO change the agentNumber to configured value
		agents = new Agent[Constant.agentNumber + 1]; // number of
		// computer-controlled
		// agents PLUS human

		// 初始化agent
		for (int i = 0; i < agents.length; i++)
		{
			agents[i] = new MGAgent(Constant.memorySize, Constant.strategySize);
		}

		// game plays once to initialise
		// game = new Game(agents, 0, 200 * (1 << 3));
		game = new Game(agents, 0, Constant.agentNumber + 1);// third @param will
																// be the number
																// of agents

		initCurrentChoise = game.getCurrentChoise();

		// price.set(0,0);
		//
		// for (int i = 0; i < price.size() - 1; ++i) {
		// //int tempPrice = price.get(i) +
		// initCurrentChoise[(initCurrentChoise.length - price.size() - 1) + i];
		// //int tempPrice = price.get(i) + game.getCurrentPrice();
		// //price.set(i+1, tempPrice);
		// //System.out.println("price["+i+"]"+price[i]);
		// //System.out.println((initCurrentChoise.length - price.length - 1) +
		// i);
		// }

		// price = null;
		// initCurrentChoise = null;
		//
		// agents[agents.length - 1] = new MGHuman(agents);

	}

	public void playGame()
	{
		price = loadHistory(price);
		game.playGame();
		this.currentPrice = game.getCurrentPrice();
		// price.set(price.size()+1, price.get(price.size())+currentPrice);
	}

	/**
	 * loading history price,while price is empty,load history
	 * 
	 * @param price
	 *            : price list
	 * @return priceList: price list
	 */
	private List loadHistory(List price)
	{

		// check file exist
		// File historyFile = new File(Constant.HISTORY_PRICE_FILE);
		// if (historyFile.canRead())
		List priceList = price;

		if (priceList.isEmpty())
		{
			long lasting = System.currentTimeMillis();
			try
			{
				File historyFile = new File(Constant.HISTORY_PRICE_FILE);
				SAXReader reader = new SAXReader();
				Document doc = reader.read(historyFile);
				Element root = doc.getRootElement();
				// get price
				// List nodes = root.elements("price");
				// System.out.println("size = "+nodes.size());
				if (priceList.size() > 50)
				{
					priceList = root.elements("price").subList(
							(priceList.size() - 50), priceList.size());
				}

				for (Iterator it = priceList.iterator(); it.hasNext();)
				{
					Element priceElm = (Element) it.next();
					System.out.println("priceElm.getData()"
							+ priceElm.getData());
				}

			} catch (Exception e)
			{
				e.printStackTrace();
			}
			// System.out.println("运行时间：" + (System.currentTimeMillis() -
			// lasting) + " 毫秒");
		}
		return priceList;
	}

	public void writeAgentFile(int best, double avg, int worse)
	{
		/**//*
			 * 产生 一个document对象AGENT_INFO_FILE
			 */
		try
		{
			File historyFile = new File(Constant.AGENT_INFO_FILE);
			SAXReader reader = new SAXReader();
			Document doc = reader.read(historyFile);
			Element root = doc.getRootElement();

			root.element("best").setText(String.valueOf(best));
			root.element("avg").setText(String.valueOf(avg));
			root.element("worse").setText(String.valueOf(worse));
			// System.out.println(asaString);
		} catch (Exception e)
		{
			// TODO: handle exception
		}
	}

	public void writePriceFile(int thisTurnPrice)
	{
		/**//*
			 * 产生 一个document对象AGENT_PRICE_FILE
			 */
		try
		{
			File historyFile = new File(Constant.HISTORY_PRICE_FILE);
			SAXReader reader = new SAXReader();
			Document doc = reader.read(historyFile);
			Element root = doc.getRootElement();
			root.addElement("price").setText(String.valueOf(thisTurnPrice));
			XMLWriter writer = new XMLWriter(new FileWriter(new File(
					Constant.HISTORY_PRICE_FILE)));
			writer.write(doc);
			writer.close();
		} catch (Exception e)
		{
			// TODO: handle exception
		}
	}

	public void destroy()
	{
		initCurrentChoise = null;

		agents = null;
		game = null;

	}

	private double caculateVolatility(List price)
	{
		double lastDTurnA = 0;
		double sqrtLastDTurnA = 0;
		double volatility = 0;
		List tempPriceList = null;
		if (price.size() >= Constant.dVolatility)
		{
			tempPriceList = price.subList(price.size() - 5, price.size());
		}
		if (price.size() == Constant.dVolatility)
		{
			for (int j = 0; j < tempPriceList.size(); j++)
			{
				lastDTurnA = lastDTurnA
						+ Integer.parseInt(tempPriceList.get(j).toString());
				sqrtLastDTurnA = sqrtLastDTurnA
						+ Double.parseDouble(tempPriceList.get(j).toString())
						* Double.parseDouble(tempPriceList.get(j).toString());
			}
		}
		// caculateVolatility
		volatility = (sqrtLastDTurnA / Constant.dVolatility)
				- (lastDTurnA / Constant.dVolatility)
				* (lastDTurnA / Constant.dVolatility);
		return volatility;
	}

}
