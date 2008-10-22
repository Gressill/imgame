package agents;

import java.lang.Math;
import util.Constant;

public class MGAgent extends Agent {

	private int historySize; // Size of history space =
								// (actionChooseNumber^memorySize) where agent
								// looks default as 27
	// at last M time steps

	private int strategiesNum; // Number of strategies default as 2

	private int[][] strategiesArray; // The strategies: an
										// (strategiesNum*historySize) array

	private double[] virtualScores; // The strategies' virtual scores

	private int[] determining; // Array determining active strategy

	public MGAgent(int memorySize, int strategySize) {

		historySize = 1 << memorySize; // Size of history space
		strategiesNum = strategySize;

		strategiesArray = new int[strategiesNum][historySize]; // The
																// strategies:
																// an
																// (strategiesNum*historySize)
																// array
		virtualScores = new double[strategiesNum];
		determining = new int[strategiesNum];
		determining[0] = (Math.random() < 0.5) ? 0 : 1;

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
			}
		}
		return strategiesArray;
	}

	/**
	 * 根据历史来决定这轮的选择
	 * 
	 * @param historyChoise:
	 *            historyChoise.
	 * @see agent.Agent#act(int)
	 * @param action:
	 *            this turn agent's choice should be 0 or 1;
	 */
	public boolean agentAct(int historyChoise) {
		//
		action = strategiesArray[determining[0]][historyChoise];
		// System.out.println("strategiesArray[1][" + historyChoise + "]");

		return true;
	}

	/**
	 * update virtualscores and decide determining
	 * 
	 * @see agent.Agent#feedback(double, int)
	 * @param historyChoise:
	 *            History choice array
	 * @param thisTurnPrice: this turn price
	 * @param num:just for test,meanness
	 */


	public boolean feedback(int historyChoise, int thisTurnPrice,int num) {
		//update virtuakScore
		if (thisTurnPrice * action < 0) {
			virtualScores[determining[0]]++;
		} else if (thisTurnPrice * action > 0) {
			virtualScores[determining[0]]--;

		}
		//// 根据历史虚分值选择策略
		for (int i = 0; i < strategiesNum; ++i) {
			if (virtualScores[i] > virtualScores[determining[0]]) {

				determining[0] = i;

			}

			//System.out.println("agent["+num+"]v[" + i + "]=" + virtualScores[i]);


		}
		return true;
	}

	public double getAction() {
		return this.action;
	}
}
