package game;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

import util.Constant;
import agents.Agent;
import agents.Strategy;

public class Game implements Strategy {

	private int price;

	private int turns = 0; // turns

	// history space
	private int P = 8;// 1 << 16;

	private int[] historyChoise;

	private ArrayList<Integer> historyPrice;

	private int[] currentChoise;// 存放每个agent的选择的数组

	private double currentPrice = 0;

	private Thread gameThread;

	private Agent[] agents;

	private int[] lastDturnA;

	private double[] agentScoreInfo = new double[3];

	//
	public Game(Agent[] agents) {
		this.agents = agents; // agents
		currentChoise = new int[agents.length];
		historyChoise = new int[agents.length];
		for (int i = 0; i < (agents.length-1); i++) {
			historyChoise[i] = (int) (P * Math.random());// random number
			// between 0 to p:8
		}

	}

	public void playGame() {

		// turns = 0;
		// boolean keepPlaying = Constant.keepPlaying;//when keepPlaying ==
		// false ,we end the game.
		// loadHistory();
		turns++;
		for (int i = 0; i < (agents.length-1); i++) {
			// System.out.println(historyChoise[i]);
			agents[i].agentAct(historyChoise[i]);// 根据历史来决定买和卖，也就是action的值，为0或者1
			currentChoise[i] = (int) agents[i].getAction();
			// System.out.println("current"+i+"Choise"+currentChoise[i]);
		}
		for (int i = 0; i < (agents.length); i++) {
			//agent[i].feedback(historyChoise[i],caculateThisTurnPrice(currentChoise), i);
			agents[i].feedback(caculateThisTurnPrice(currentChoise));
			updateHistory(historyChoise, currentChoise, i);
		}
		//System.out.println(agents[agents.length-1].getClass());
		// 得到该轮的价格 feedback to client
		currentPrice = caculatePrice(currentChoise) + agents[agents.length-1].getAction();
		this.updateAgentScore(agents);
		// System.out.println("currentPrice"+currentPrice);
		// Constant.keepPlaying = false;
		System.out.println("currentPrice" + currentPrice);

	}

	/**
	 * Initialize the agent strategy
	 */
	public Strategy initStrategy() {
		// TODO Auto-generated method stub
		return null;
	}

	public int[] getCurrentChoise() {
		return currentChoise;
	}

	public double getCurrentPrice() {
		return this.currentPrice;
	}

	/**
	 * 计算该轮的价格
	 * 
	 * @param currentChoise
	 *            ：储存该轮的所有agent的决定（买或者卖,1/-1）
	 * @return currentPrice：每一轮的价格
	 */
	private int caculateThisTurnPrice(int[] currentChoise) {

		int thisTurnPrice = 0;
		for (int i = 0; i < currentChoise.length; i++) {
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
	private double caculatePrice(int[] currentChoise) {

		// int currentPrice = 0;
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
	private void updateHistory(int historyChoise[], int currentChoise[], int i) {

		// System.out.println("agent["+i+"]Choise = "+historyChoise[i]);
		int tempCurrentChoise = 1;
		if (currentChoise[i] == -1) {
			tempCurrentChoise = 0;
		}
		historyChoise[i] = ((2 * historyChoise[i]) + tempCurrentChoise) % P;
	}

	private void updateHistoryForHold(int historyChoise[], int currentChoise[],
			int i) {

		// System.out.println("agent["+i+"]Choise = "+historyChoise[i]);
		int tempCurrentChoise = 1;
		if (currentChoise[i] == -1) {
			tempCurrentChoise = 0;
		} else if (currentChoise[i] == 0) {
			tempCurrentChoise = 1;
		} else if (currentChoise[i] == 1) {
			tempCurrentChoise = 2;
		}
		historyChoise[i] = ((3 * historyChoise[i]) + tempCurrentChoise) % P;
	}

	private void caculateVolatility() {
	}

	/**
	 * this to return best,worse,average score of agents
	 * 
	 * @return an array A ,A[0] = worse score, A[1] = average score, A[2] = best score
	 */
	public double[] getAgentScore() {
  
		DecimalFormat daDecimalFormat = new DecimalFormat("########.00");
		//四舍五入
		agentScoreInfo[0] = Double.parseDouble(daDecimalFormat.format(agentScoreInfo[0]));
		agentScoreInfo[1] = Double.parseDouble(daDecimalFormat.format(agentScoreInfo[1]));
		agentScoreInfo[2] = Double.parseDouble(daDecimalFormat.format(agentScoreInfo[2]));
		return agentScoreInfo;
	}

	public double getHumanScoreInfo() {
		return agents[agents.length].getScore();
	}
	
	/**
	 * update agents score,best,worse,average score of agents
	 * @param agents: all agents
	 */
	public void updateAgentScore(Agent[] agents) {

//		double avgAgentScore = 0;
//		double bestAgentScore = 0;
//		double worseAgentScore = 0;
		//System.out.println("agents is:"+agents.length);
		//avgAgentScore = agents[22].getScore();
		//System.out.println("agents score is:" + agents[500].getScore());
		for (int i = 0, j = (agents.length-1); i < j; i++) {
			if (agents[i].getScore() < this.agentScoreInfo[0]) {

				this.agentScoreInfo[0] = agents[i].getScore();
			}

			if (agents[i].getScore() > this.agentScoreInfo[2]) {

				this.agentScoreInfo[2] = agents[i].getScore();
			}

			this.agentScoreInfo[1] = this.agentScoreInfo[1] + agents[i].getScore();
		}
		this.agentScoreInfo[1] = this.agentScoreInfo[1] / (agents.length-1);
		//Arrays.sort(agents);
//		this.agentScoreInfo[0] = worseAgentScore;
//		this.agentScoreInfo[1] = avgAgentScore;
//		this.agentScoreInfo[2] = bestAgentScore;
//		System.out.println("agent:--best is:"+this.agentScoreInfo[2]+"avg is:"+this.agentScoreInfo[1]+"worse is:"+this.agentScoreInfo[0]);
	}

}