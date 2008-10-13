package agents;

import java.lang.Math;
import util.Constant;

public class MGAgent extends Agent {

	private int historySize; // Size of history space = (actionChooseNumber^memorySize) where agent looks default as 27
	// at last M time steps

	private int strategiesNum; // Number of strategies default as 2

	private int[][] strategiesArray; // The strategies: an (strategiesNum*historySize) array

	private double[] virtualScores; // The strategies' virtual scores

	private int[] determining; // Array determining active strategy

	public MGAgent(int memorySize, int strategySize) {

		historySize = 1 << memorySize; // Size of history space
		strategiesNum = strategySize;

		strategiesArray = new int[strategiesNum][historySize]; // The strategies: an (strategiesNum*historySize) array

		virtualScores = new double[strategiesNum];
		determining = new int[strategiesNum];

		this.InitStrategy();
	}

	/**
	 * init strategies
	 * 
	 * return strategies array
	 */
	public int[][] InitStrategy() {

		for (int i = 0; i < strategiesNum; i++) {
			// virtualScores[i] = 0;
			for (int j = 0; j < historySize; j++) {
				// this is how the game works,use random num to simulate the
				// people's select & init strategies
				strategiesArray[i][j] = (Math.random() < 0.5) ? -1 : 1;

				//System.out.println("strategiesArray ["+i+"]["+j+"]= "+strategiesArray[i][j]);
			}
		}
		return strategiesArray;
	}

	/**
	 * 根据历史来决定这轮的选择
	 * 
	 * @param historyChoise: historyChoise.
	 * @see agent.Agent#act(int)
	 * @param action: this turn agent's choice should be 0 or 1;
	 */
	public boolean agentAct(int historyChoise) {
		//
		action = strategiesArray[determining[0]][historyChoise];
		System.out.println("strategiesArray[1][" + historyChoise + "]");
		return true;
	}

	/**
	 * update virtualscores and decide determining
	 * 
	 * @see agent.Agent#feedback(double, int)
	 * @param action: action,couble be -1 or 1
	 * @param historyChoise:   History choice array
	 */

	public boolean feedback(double action, int historyChoise, int currentPrice) {
		if ((currentPrice < 0) && (this.action > 0)) {

		}
		int ns = 1;
		for (int i = 0; i < strategiesNum; ++i) {
			virtualScores[i] = virtualScores[i]
					- strategiesArray[i][historyChoise % historySize] * action;

		}

		// 根据历史虚分值选择策略
		for (int i = 0; i < strategiesNum; ++i) {

			if (virtualScores[i] > virtualScores[determining[0]]) {

				determining[0] = i;
				ns = 1;

			} else if ((virtualScores[i] == virtualScores[determining[0]])
					&& (i != determining[0])) {
				determining[ns] = i;
				ns++;
			}
		}

		if (ns > 1) {

			determining[0] = determining[(int) (ns * Math.random())];//第一次在 0 and 1 中随机选择一个策略
		}
		return true;
	}

	public double getAction() {
		return this.action;
	}
}
