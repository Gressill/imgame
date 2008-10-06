package game;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;
import java.util.*;

import org.dom4j.*;
import org.dom4j.io.*;

import agents.Agent;
import util.Constant;
import util.FileOperate;
import agents.Strategy;
import server.SocketServer;

public class Game implements Strategy,Runnable {

	private int price;
	
	private int turns; //turns

	//?
	private int ttrans = 0;

	//computer agents number
	private int tmax = 0;

	//history space
	private int P = 8;//1 << 16;
	
	private int[] historyChoise;
	
	private ArrayList<Integer> historyPrice;

	private int[] currentChoise;

	private Thread gameThread;

	private Agent[] agent;
	//
	public Game(Agent[] agents, int tTrans, int tMax) {
		this.agent = agents; //agents[95]
		ttrans = tTrans;
		tmax = tMax;		//59
		currentChoise = new int[tmax];
		historyChoise = new int[tmax];
		historyChoise[0] = (int) (P * Math.random());
	}
	
public void playGame() {
		
		//create sockets
		//SocketServer mgSocket=new SocketServer();
		//mgSocket.startServer(Constant.port);
		
		turns = 0;
		boolean keepPlaying = false;//when keepPlaying == false ,we end the game.
		loadHistory();
		while (keepPlaying) {
			currentChoise[turns] = 0;
			
			for (int i = 0; i < agent.length; i++) {
				agent[i].agentAct(historyChoise[turns]);//根据历史来决定买和卖，也就是action的值，为0或者1
				//currentChoise[i] = (int)agent[i].getAction();
				//Arrays.fill(currentChoise, historyPrice.length);
			}
			//historyPrice.add(currentChoise[turns]);
		}
		
	}

	/**
	 * 
	 * loading history price
	 */
	public void loadHistory() {
		
		
		// File historyFile = new File(Constant.HISTORY_PRICE_FILE);
		// if (historyFile.canRead())
		
		long lasting = System.currentTimeMillis();
		try {
				File historyFile = new File(Constant.HISTORY_PRICE_FILE);
				SAXReader reader = new SAXReader();
				Document doc = reader.read(historyFile);
				Element root = doc.getRootElement();
				Element foo;
				//System.out.println(" test:" + root.elementText("value"));
				for (Iterator i = root.elementIterator(); i.hasNext();) {
					foo = (Element) i.next();
					//System.out.println(foo.getData());
				}
				//get price
				List nodes = root.elements("price");
				//System.out.println("size = "+nodes.size());

				for (Iterator it = nodes.iterator(); it.hasNext();) {
				   Element elm = (Element) it.next();
				   System.out.println(elm.getData());
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				}
			System.out.println("运行时间：" + (System.currentTimeMillis() - lasting) + " 毫秒");
		}

	/**
	 * Initialize the agent strategy
	 */
	public Strategy initStrategy() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 计算该轮的价格
	 * 
	 * @param currentChoise：储存该轮的所有agent的决定（买或者卖,1/0）
	 * @return currentPrice：每一轮的价格
	 */
	private double caculatePrice(int[] currentChoise) {
		
		double currentPrice = 0;
		for (int i = 0; i < currentChoise.length; i++) {
			currentPrice += currentChoise[i];
		}
		return currentPrice;
	}
	/*
	 * lock the history
	 */
	private void lockHistory() {

	}

	/**
	 * strategiesArray = new int[strategiesNum][historySize];<br>
     * then this we may base on virtualScores to determin param "strategiesNum",<br>
     * and we use historyChoise to determing param "historySize".<br>
     * for example:<br>
     * in t turns,strategy one's virtualScores is highter,so MGagent action with strategiesArray[1][historyChoise[t]]
     *  
	 * @param historyChoise: History choice array,we add that <br>
	 *  for example:<br>
     *     1     0        1 (historial fluctuation)<br>
     *   -----><br>
     *  1*2^2 + 0*2^1 + 1*2^0 (strategy's choose index)<br>
     *  
	 * @param currentChoise: this turns agents choice.
	 */
	private void updateHistory(int historyChoise[],int currentChoise[]) {
		
		int historySize = 1;
		historyChoise[turns] = ((2 * historyChoise[turns - 1]) + ((currentChoise[turns - 1] > 0) ? 1 : 0)) % P;
		//System.out.println("historyChoise"+historyChoise[turns]);
	}

	private void releaseHistory() {

	}

	public void gainAction() {

		lockHistory();
		updateHistory(historyChoise,currentChoise);
		releaseHistory();
	}
	
	public void run() {
		Thread me = Thread.currentThread();
		// game continues to play
		while (me == gameThread && Constant.playingFlagOfHumanPlayer) {
			//play();
		}

		me = null;

	}

	public void start() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	public void stop() {
		gameThread = null;
	}
		
	public int[] getCurrentChoise() {
		return currentChoise;
	}

}