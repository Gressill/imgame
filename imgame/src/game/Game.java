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
	
	private int turns = 0; //turns

	//?
	private int ttrans = 0;

	//computer agents number
	private int tmax = 0;

	//history space
	private int P = 8;//1 << 16;
	
	private int[] historyChoise;
	
	private ArrayList<Integer> historyPrice;

	private int[] currentChoise;
	
	private int currentPrice;

	private Thread gameThread;

	private Agent[] agent;
	//
	public Game(Agent[] agents, int tTrans, int tMax) {
		this.agent = agents; //agents[95]
		ttrans = tTrans;
		tmax = tMax;		//59
		currentChoise = new int[agents.length];
		historyChoise = new int[agents.length];
		for (int i = 0; i < agents.length; i++)
		{
			historyChoise[i] = (int) (P * Math.random());//random number between 0 to p:8
		}
		
	}
	
	public void playGame() {
		
		//turns = 0;
		//boolean keepPlaying = Constant.keepPlaying;//when keepPlaying == false ,we end the game.
		//loadHistory();
		//while (Constant.keepPlaying) {
			//currentChoise[turns] = 0;
			
			for (int i = 0; i < agent.length; i++) {
				agent[i].agentAct(historyChoise[i]);//根据历史来决定买和卖，也就是action的值，为0或者1
				currentChoise[i] = (int)agent[i].getAction();
				updateHistory(historyChoise,currentChoise,i);
				//System.out.println("current"+i+"Choise"+currentChoise[i]);
				
			}
			//得到该轮的价格 feedback to client
			currentPrice = caculatePrice(currentChoise)+Constant.userChoise;
			//System.out.println("currentPrice"+currentPrice);
			//Constant.keepPlaying = false;
//			if (Constant.keepPlaying == false) {
//				break;
//			} else {
//			}
			
			// historyPrice.add(currentChoise[turns]);
		//}
		System.out.println("currentPrice"+currentPrice);
		turns ++;
		
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
	private int caculatePrice(int[] currentChoise) {
		
		int currentPrice = 0;
		for (int i = 0; i < currentChoise.length; i++) {
			currentPrice += currentChoise[i];
		}
		return currentPrice;
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
	private void updateHistory(int historyChoise[],int currentChoise[],int i) {
		
		System.out.println("history["+i+"]Choise"+historyChoise[i]);
		historyChoise[i] = ((2 * historyChoise[i]) + currentChoise[i]) % P;
	}

	public void gainAction() {

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
	
	public int getCurrentPrice()
	{
		return this.currentPrice;
	}

}