package game;

import java.util.ArrayList;

import util.Constant;
import agents.Agent;
import agents.Strategy;

public class Game implements Strategy
{

	private int price;

	private int turns = 0; // turns

	// ?
	private int ttrans = 0;

	// computer agents number
	private int tmax = 0;

	// history space
	private int P = 8;// 1 << 16;

	private int[] historyChoise;

	private ArrayList<Integer> historyPrice;

	private int[] currentChoise;// 存放每个agent的选择的数组

	private int currentPrice = 0;

	private Thread gameThread;

	private Agent[] agent;

	private int[] lastDturnA;

	//
	public Game(Agent[] agents, int tTrans, int tMax)
	{
		this.agent = agents; // agents[95]
		ttrans = tTrans;
		tmax = tMax; // 59
		currentChoise = new int[agents.length];
		historyChoise = new int[agents.length];
		for (int i = 0; i < agents.length; i++)
		{
			historyChoise[i] = (int) (P * Math.random());// random number
															// between 0 to p:8
		}

	}

	public void playGame()
	{

		// turns = 0;
		// boolean keepPlaying = Constant.keepPlaying;//when keepPlaying ==
		// false ,we end the game.
		// loadHistory();

		for (int i = 0; i < agent.length; i++)
		{
			// System.out.println(historyChoise[i]);
			agent[i].agentAct(historyChoise[i]);// 根据历史来决定买和卖，也就是action的值，为0或者1
			currentChoise[i] = (int) agent[i].getAction();
			// System.out.println("current"+i+"Choise"+currentChoise[i]);
		}
		for (int i = 0; i < agent.length; i++)
		{
			agent[i].feedback(historyChoise[i],
					caculateThisTurnPrice(currentChoise), i);
			updateHistory(historyChoise, currentChoise, i);
		}
		// 得到该轮的价格 feedback to client
		currentPrice = caculatePrice(currentChoise) + Constant.userChoise;
		// System.out.println("currentPrice"+currentPrice);
		// Constant.keepPlaying = false;
		System.out.println("currentPrice" + currentPrice);
		turns++;

	}

	/**
	 * Initialize the agent strategy
	 */
	public Strategy initStrategy()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public int[] getCurrentChoise()
	{
		return currentChoise;
	}

	public int getCurrentPrice()
	{
		return this.currentPrice;
	}

	/**
	 * 计算该轮的价格
	 * 
	 * @param currentChoise
	 *            ：储存该轮的所有agent的决定（买或者卖,1/-1）
	 * @return currentPrice：每一轮的价格
	 */
	private int caculateThisTurnPrice(int[] currentChoise)
	{

		int thisTurnPrice = 0;
		for (int i = 0; i < currentChoise.length; i++)
		{
			thisTurnPrice += currentChoise[i];
		}
		return thisTurnPrice;
	}

	/**
	 * 计算历史的价格
	 * 
	 * @param currentChoise
	 *            ：储存该轮的所有agent的决定（买或者卖,1/-1）
	 * @return currentPrice：每一轮的价格
	 */
	private int caculatePrice(int[] currentChoise)
	{

		// int currentPrice = 0;
		for (int i = 0; i < currentChoise.length; i++)
		{
			currentPrice += currentChoise[i];
		}
		return currentPrice;
	}

	/**
	 * strategiesArray = new int[strategiesNum][historySize];<br>
	 * then this we may base on virtualScores to determin param "strategiesNum",<br>
	 * and we use historyChoise to determing param "historySize".<br>
	 * for example:<br>
	 * in t turns,strategy one's virtualScores is highter,so MGagent action with
	 * strategiesArray[1][historyChoise[t]]
	 * 
	 * @param historyChoise
	 *            : History choice array,we add that <br>
	 *            for example:<br>
	 *            1 0 1 (historial fluctuation)<br>
	 *            -----><br>
	 *            1*2^2 + 0*2^1 + 1*2^0 (strategy's choose index)<br>
	 * 
	 * @param currentChoise
	 *            : this turns agents choice.
	 */
	private void updateHistory(int historyChoise[], int currentChoise[], int i)
	{

		// System.out.println("agent["+i+"]Choise = "+historyChoise[i]);
		int tempCurrentChoise = 1;
		if (currentChoise[i] == -1)
		{
			tempCurrentChoise = 0;
		}
		historyChoise[i] = ((2 * historyChoise[i]) + tempCurrentChoise) % P;
	}

	private void updateHistoryForHold(int historyChoise[], int currentChoise[], int i)
	{

		// System.out.println("agent["+i+"]Choise = "+historyChoise[i]);
		int tempCurrentChoise = 1;
		if (currentChoise[i] == -1)
		{
			tempCurrentChoise = 0;
		} else if (currentChoise[i] == 0)
		{
			tempCurrentChoise = 1;
		} else if (currentChoise[i] == 1)
		{
			tempCurrentChoise = 2;
		}
		historyChoise[i] = ((3 * historyChoise[i]) + tempCurrentChoise) % P;
	}

	private void caculateVolatility()
	{
	}

	private void caciulatePlayerScore()
	{
	}

}